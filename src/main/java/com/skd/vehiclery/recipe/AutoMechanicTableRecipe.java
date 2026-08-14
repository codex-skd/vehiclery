package com.skd.vehiclery.recipe;

import com.skd.vehiclery.Vehiclery;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AutoMechanicTableRecipe implements Recipe<ContainerRecipeInput>, Comparable<AutoMechanicTableRecipe> {
    public static final Identifier ID = Vehiclery.rl("auto_mechanic_table");
    public static final RecipeType<AutoMechanicTableRecipe> TYPE = new RecipeType<>() {};

    protected final Identifier category;
    protected final List<Ingredient> ingredients;
    protected final AutoMechanicTableRecipeSerializer.ResultSpec resultSpec;
    protected final int sortNum;

    // Built lazily: constructing the ItemStack requires the result item's DataComponentMap to be
    // bound, which is only guaranteed once the current datapack reload has fully completed (see
    // AutoMechanicTableRecipeSerializer.AUTO_COMPONENT_STACK for details).
    private @Nullable ItemStack cachedResult;

    public @Nullable Identifier sortId;

    public AutoMechanicTableRecipe(Identifier category, List<Ingredient> ingredients, AutoMechanicTableRecipeSerializer.ResultSpec resultSpec, int sortNum) {
        this.category = category;
        this.ingredients = ingredients;
        this.resultSpec = resultSpec;
        this.sortNum = sortNum;
    }

    public AutoMechanicTableRecipe(Identifier category, List<Ingredient> ingredients, ItemStack resolvedResult, int sortNum) {
        this.category = category;
        this.ingredients = ingredients;
        this.resultSpec = AutoMechanicTableRecipeSerializer.toResultSpec(resolvedResult);
        this.cachedResult = resolvedResult;
        this.sortNum = sortNum;
    }

    public AutoMechanicTableRecipeSerializer.ResultSpec getResultSpec() {
        return this.resultSpec;
    }

    public Identifier getCategory() {
        return this.category;
    }

    @Override
    public boolean matches(ContainerRecipeInput inv, Level lvl) {
        boolean[] result = {true};
        this.forMissingIngredients(inv, ing -> result[0] = false);

        return result[0];
    }

    @Override
    public ItemStack assemble(ContainerRecipeInput inv) {
        for (var ing : this.ingredients) {
            for (int i = 0; i < inv.size(); i++) {
                var stack = inv.getItem(i);
                if (ing.test(stack)) {
                    stack.shrink(1);
                    break;
                }
            }
        }

        return this.getResultItem().copy();
    }

    public ItemStack getResultItem() {
        if (this.cachedResult == null) {
            this.cachedResult = AutoMechanicTableRecipeSerializer.buildResultStack(this.resultSpec);
        }
        return this.cachedResult;
    }

    @Override
    public RecipeSerializer<? extends Recipe<ContainerRecipeInput>> getSerializer() {
        return AutoMechanicTableRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<ContainerRecipeInput>> getType() {
        return TYPE;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public net.minecraft.world.item.crafting.PlacementInfo placementInfo() {
        // Not a grid-slot-based recipe (custom station UI), so there is no meaningful vanilla
        // slot-placement hint to give.
        return net.minecraft.world.item.crafting.PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public net.minecraft.world.item.crafting.RecipeBookCategory recipeBookCategory() {
        // Never shown in the vanilla recipe book (custom Auto Mechanic Table UI instead).
        return net.minecraft.world.item.crafting.RecipeBookCategories.CRAFTING_MISC;
    }

    public void forMissingIngredients(ContainerRecipeInput inv, Consumer<Ingredient> action) {
        var invCopy = new ArrayList<ItemStack>();
        for (int i = 0; i < inv.size(); i++) {
            invCopy.add(inv.getItem(i));
        }

        for (var ing : this.ingredients) {
            if (invCopy.stream().noneMatch(ing)) {
                action.accept(ing);
            } else {
                invCopy.remove(invCopy.stream().filter(ing).collect(Collectors.toList()).get(0));
            }
        }
    }

    @Override
    public int compareTo(@NotNull AutoMechanicTableRecipe o) {
        int diff = this.getCategory().compareTo(o.getCategory());
        if (diff != 0) return diff;

        diff = Integer.compare(this.sortNum, o.sortNum);
        if (diff != 0) return diff;

        if (this.sortId != null && o.sortId != null) {
            return this.sortId.compareTo(o.sortId);
        }

        return net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(this.getResultItem().getItem())
                .compareTo(net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(o.getResultItem().getItem()));
    }
}
