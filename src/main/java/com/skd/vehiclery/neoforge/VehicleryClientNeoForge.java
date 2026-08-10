package com.skd.vehiclery.neoforge;

import com.skd.vehiclery.VehicleryClient;
import com.skd.vehiclery.automobile.render.AutomobileModels;
import com.skd.vehiclery.automobile.render.obj.ObjLoader;
import com.skd.vehiclery.block.VehicleryBlocks;
import com.skd.vehiclery.block.model.SlopeBakedModel;
import com.skd.vehiclery.entity.AutomobileEntity;
import com.skd.vehiclery.neoforge.block.render.NeoForgeSlopeBakedModel;
import com.skd.vehiclery.neoforge.block.render.NeoForgeSlopeGeometryLoader;
import com.skd.vehiclery.neoforge.block.render.SlopeModelsProvider;
import com.skd.vehiclery.particle.VehicleryParticles;
import com.skd.vehiclery.particle.DriftSmokeParticle;
import com.skd.vehiclery.screen.AutomobileHud;
import com.skd.vehiclery.screen.MenuScreenRegistrar;
import com.skd.vehiclery.util.Eventual;
import com.skd.vehiclery.util.InitlessConstants;
import com.skd.vehiclery.util.TriFunc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

@EventBusSubscriber(value = Dist.CLIENT, modid = InitlessConstants.VEHICLERY)
public class VehicleryClientNeoForge {
    public static final AutomobileModels MODEL_DEF_LOADER = new AutomobileModels();

    @SubscribeEvent
    public static void initClient(FMLClientSetupEvent setup) {
        NeoForgePlatform.init();

        VehicleryClient.init();

        SlopeBakedModel.impl = NeoForgeSlopeBakedModel::new;

        NeoForge.EVENT_BUS.<RenderGuiEvent.Pre>addListener(evt -> {
            var player = Minecraft.getInstance().player;
            if (player.getVehicle() instanceof AutomobileEntity auto) {
                AutomobileHud.render(evt.getGuiGraphics(), player, auto, evt.getPartialTick().getGameTimeDeltaTicks());
            }
        });

        NeoForge.EVENT_BUS.<ViewportEvent.ComputeFov>addListener(evt ->
                evt.setFOV(VehicleryClient.modifyBoostFov(Minecraft.getInstance(), evt.getFOV(), evt.getPartialTick())));
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent evt) {
        evt.registerSpriteSet(VehicleryParticles.DRIFT_SMOKE.require(), DriftSmokeParticle.Factory::new);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.BlockTintSources evt) {
        evt.register(List.of(VehicleryClient.GRASS_COLOR), VehicleryBlocks.GRASS_OFF_ROAD.require());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.ItemTintSources evt) {
        // The grass item tint is now provided by the vanilla grass ItemTintSource codec, registered
        // automatically via item model JSON; no runtime item-color registration is required in 26.2.
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent evt) {
        VehicleryClient.initMenuScreens(new MenuScreenRegistrar() {
            @Override
            public <T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> void accept(
                    Eventual<MenuType<T>> type, TriFunc<T, Inventory, Component, U> factory) {
                evt.register(type.require(), factory::apply);
            }
        });
    }

    @SubscribeEvent
    public static void registerResourceLoaders(AddClientReloadListenersEvent evt) {
        evt.addListener(Vehiclery.rl("automobile_models"), MODEL_DEF_LOADER);
        evt.addListener(Vehiclery.rl("obj_loader"), ObjLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void registerBakedModels(ModelEvent.RegisterLoaders evt) {
        evt.register(NeoForgeSlopeGeometryLoader.ID, NeoForgeSlopeGeometryLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void generateResources(GatherDataEvent evt) {
        var generator = evt.getGenerator();
        var output = generator.getPackOutput();

        generator.addProvider(evt.includeClient(),
                new SlopeModelsProvider(output));
    }
}