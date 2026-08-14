package com.skd.vehiclery.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.skd.vehiclery.Vehiclery;
import com.skd.vehiclery.item.AutomobileComponentItem;
import com.skd.vehiclery.item.VehicleryItems;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.ArrayList;

public class AutoMechanicTableRecipeSerializer {
    /**
     * Recipe JSON is parsed as part of the datapack reload's resource-listener phase, which runs
     * BEFORE {@code ReloadableServerResources.updateComponentsAndStaticRegistryTags()} binds item
     * DataComponentMaps for that reload (see MinecraftServer#reloadResources / WorldLoader). Building
     * an ItemStack eagerly during decode (which requires bound components) throws "Item ... does not
     * have components yet" for any item whose Holder hasn't been bound yet in that cycle. So this codec
     * decodes into a lightweight spec (unbound Item holder + count + component id) and the actual
     * ItemStack is only built lazily in AutoMechanicTableRecipe#getResultItem(), by which point the
     * reload has completed and components are bound.
     */
    public record ResultSpec(net.minecraft.core.Holder<net.minecraft.world.item.Item> item, int count, Identifier component) {}

    public static final Codec<ResultSpec> AUTO_COMPONENT_STACK = RecordCodecBuilder.create(inst -> inst.group(
            net.minecraft.world.item.Item.CODEC.fieldOf("item").forGetter(ResultSpec::item),
            Codec.INT.optionalFieldOf("count", 1).forGetter(ResultSpec::count),
            Identifier.CODEC.fieldOf("component").forGetter(ResultSpec::component)
    ).apply(inst, ResultSpec::new));

    public static ItemStack buildResultStack(ResultSpec spec) {
        var stack = new ItemStack(spec.item(), spec.count());
        var item = stack.getItem();
        if (item instanceof AutomobileComponentItem.Dynamic<?> cItem) {
            cItem.setComponent(stack, (ResourceKey) ResourceKey.create(cItem.registryKey, spec.component()));
        } else {
            stack.set(VehicleryItems.COMPONENT_GENERIC_AUTO_PART.require(), spec.component());
        }
        return stack;
    }

    public static ResultSpec toResultSpec(ItemStack stack) {
        var componentId = Vehiclery.rl("empty");
        var item = stack.getItem();
        if (item instanceof AutomobileComponentItem.Dynamic<?> cItem) {
            componentId = cItem.getComponentId(stack, null);
        } else if (item instanceof AutomobileComponentItem.Builtin<?> cItem) {
            componentId = cItem.getComponent(stack, null).getId();
        }
        return new ResultSpec(stack.typeHolder(), stack.getCount(), componentId);
    }

    public static final MapCodec<AutoMechanicTableRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Identifier.CODEC.fieldOf("category").forGetter(AutoMechanicTableRecipe::getCategory),
            Codec.list(Ingredient.CODEC).fieldOf("ingredients").forGetter(r -> r.ingredients),
            AUTO_COMPONENT_STACK.fieldOf("result").forGetter(AutoMechanicTableRecipe::getResultSpec),
            Codec.INT.fieldOf("sortnum").forGetter(r -> r.sortNum)
    ).apply(inst, AutoMechanicTableRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AutoMechanicTableRecipe> STREAM_CODEC =
            StreamCodec.of(AutoMechanicTableRecipeSerializer::toNetwork, AutoMechanicTableRecipeSerializer::fromNetwork);

    public static AutoMechanicTableRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
        var category = Identifier.tryParse(buf.readUtf());

        int size = buf.readByte();
        var ingredients = new ArrayList<Ingredient>();
        for (int i = 0; i < size; i++) {
            ingredients.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
        }

        var result = ItemStack.STREAM_CODEC.decode(buf);
        int sortNum = buf.readInt();

        return new AutoMechanicTableRecipe(category, ingredients, result, sortNum);
    }

    public static void toNetwork(RegistryFriendlyByteBuf buf, AutoMechanicTableRecipe recipe) {
        buf.writeUtf(recipe.category.toString());
        buf.writeByte(recipe.ingredients.size());
        recipe.ingredients.forEach(ing -> Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing));
        ItemStack.STREAM_CODEC.encode(buf, recipe.getResultItem());
        buf.writeInt(recipe.sortNum);
    }

    public static final RecipeSerializer<AutoMechanicTableRecipe> INSTANCE = new RecipeSerializer<>(CODEC, STREAM_CODEC);
}
