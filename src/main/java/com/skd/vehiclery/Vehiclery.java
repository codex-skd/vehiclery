package com.skd.vehiclery;

import com.mojang.serialization.Codec;
import com.skd.vehiclery.automobile.AutomobileEngine;
import com.skd.vehiclery.automobile.AutomobileFrame;
import com.skd.vehiclery.automobile.AutomobileWheel;
import com.skd.vehiclery.block.VehicleryBlocks;
import com.skd.vehiclery.entity.VehicleryEntities;
import com.skd.vehiclery.item.VehicleryItems;
import com.skd.vehiclery.item.CreativeTabQueue;
import com.skd.vehiclery.particle.VehicleryParticles;
import com.skd.vehiclery.platform.Platform;
import com.skd.vehiclery.recipe.AutoMechanicTableRecipe;
import com.skd.vehiclery.recipe.AutoMechanicTableRecipeSerializer;
import com.skd.vehiclery.screen.AutoMechanicTableScreenHandler;
import com.skd.vehiclery.screen.SingleSlotScreenHandler;
import com.skd.vehiclery.sound.VehiclerySounds;
import com.skd.vehiclery.util.AUtils;
import com.skd.vehiclery.util.VehicleryClientResourceDumper;
import com.skd.vehiclery.util.DefaultRegistrar;
import com.skd.vehiclery.util.Eventual;
import com.skd.vehiclery.util.InitlessConstants;
import com.skd.vehiclery.util.RegistryQueue;
import com.skd.vehiclery.util.network.CommonPackets;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Vehiclery {
    public static final String MOD_ID = InitlessConstants.VEHICLERY;
    public static final Logger LOG = LogManager.getLogger("Vehiclery");

    public static CreativeTabQueue TAB = new CreativeTabQueue(rl("vehiclery"));
    public static CreativeTabQueue PREFAB_TAB = new CreativeTabQueue(rl("vehiclery_prefabs"));

    public static final TagKey<Block> SLOPES = TagKey.create(Registries.BLOCK, rl("slopes"));
    public static final TagKey<Block> STEEP_SLOPES = TagKey.create(Registries.BLOCK, rl("steep_slopes"));
    public static final TagKey<Block> NON_STEEP_SLOPES = TagKey.create(Registries.BLOCK, rl("non_steep_slopes"));
    public static final TagKey<Block> STICKY_SLOPES = TagKey.create(Registries.BLOCK, rl("sticky_slopes"));

    public static final Eventual<MenuType<AutoMechanicTableScreenHandler>> AUTO_MECHANIC_SCREEN =
            RegistryQueue.register(BuiltInRegistries.MENU, Vehiclery.rl("auto_mechanic_table"), () -> Platform.get().menuType(AutoMechanicTableScreenHandler::new));
    public static final Eventual<MenuType<SingleSlotScreenHandler>> SINGLE_SLOT_SCREEN =
            RegistryQueue.register(BuiltInRegistries.MENU, Vehiclery.rl("single_slot"), () -> Platform.get().menuType(SingleSlotScreenHandler::new));

    public static void init() {
        VehiclerySounds.init();
        VehicleryBlocks.init();
        VehicleryItems.init();
        VehicleryEntities.init();
        VehicleryParticles.init();
        initOther();

        CommonPackets.init();
    }

    public static void initOther() {
        RegistryQueue.register(BuiltInRegistries.RECIPE_TYPE, AutoMechanicTableRecipe.ID, () -> AutoMechanicTableRecipe.TYPE);
        RegistryQueue.register(BuiltInRegistries.RECIPE_SERIALIZER, AutoMechanicTableRecipe.ID, () -> AutoMechanicTableRecipeSerializer.INSTANCE);
        RegistryQueue.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB.location, () -> Platform.get().creativeTab(TAB.location, AUtils::createGroupIcon, TAB));
        RegistryQueue.register(BuiltInRegistries.CREATIVE_MODE_TAB, PREFAB_TAB.location, () -> Platform.get().creativeTab(PREFAB_TAB.location, AUtils::createPrefabsIcon, PREFAB_TAB));

        Platform.get().registerDataSerializer(AutomobileFrame.ID, AutomobileFrame.SERIALIZER);
        Platform.get().registerDataSerializer(AutomobileWheel.ID, AutomobileWheel.SERIALIZER);
        Platform.get().registerDataSerializer(AutomobileEngine.ID, AutomobileEngine.SERIALIZER);
    }

    public static void initDynamicRegistries(DynamicRegistryRegistrar handler) {
        handler.accept(AutomobileFrame.REGISTRY, AutomobileFrame.DIRECT_CODEC, AutomobileFrame.BOOTSTRAP);
        handler.accept(AutomobileWheel.REGISTRY, AutomobileWheel.DIRECT_CODEC, AutomobileWheel.BOOTSTRAP);
        handler.accept(AutomobileEngine.REGISTRY, AutomobileEngine.DIRECT_CODEC, AutomobileEngine.BOOTSTRAP);
    }

    public static Identifier rl(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void dumpDynamicRegistries(HolderLookup.Provider registries) throws IOException {
        VehicleryClientResourceDumper.dumpDynamicRegistry(registries, AutomobileFrame.REGISTRY, AutomobileFrame.DIRECT_CODEC);
        VehicleryClientResourceDumper.dumpDynamicRegistry(registries, AutomobileWheel.REGISTRY, AutomobileWheel.DIRECT_CODEC);
        VehicleryClientResourceDumper.dumpDynamicRegistry(registries, AutomobileEngine.REGISTRY, AutomobileEngine.DIRECT_CODEC);
    }

    public interface DynamicRegistryRegistrar {
        <T> void accept(ResourceKey<Registry<T>> key, Codec<T> codec, DefaultRegistrar<T> defaults);
    }
}
