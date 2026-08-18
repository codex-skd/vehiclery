package com.skd.vehiclery;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.skd.vehiclery.automobile.AutomobileComponent;
import com.skd.vehiclery.automobile.AutomobileEngine;
import com.skd.vehiclery.automobile.AutomobileFrame;
import com.skd.vehiclery.automobile.AutomobileWheel;
import com.skd.vehiclery.automobile.render.AutomobileModels;
import com.skd.vehiclery.automobile.render.AutomobileRenderer;
import com.skd.vehiclery.automobile.render.BaseModel;
import com.skd.vehiclery.automobile.render.item.SimpleRenderableAutomobile;
import com.skd.vehiclery.block.VehicleryBlocks;
import com.skd.vehiclery.block.entity.render.AutomobileAssemblerBlockEntityRenderer;
import com.skd.vehiclery.entity.AutomobileEntity;
import com.skd.vehiclery.entity.VehicleryEntities;
import com.skd.vehiclery.entity.render.AutomobileEntityRenderer;
import com.skd.vehiclery.item.AutomobileComponentItem;
import com.skd.vehiclery.item.VehicleryItems;
import com.skd.vehiclery.platform.Platform;
import com.skd.vehiclery.screen.AutoMechanicTableScreen;
import com.skd.vehiclery.screen.MenuScreenRegistrar;
import com.skd.vehiclery.screen.SingleSlotScreen;
import com.skd.vehiclery.sound.AutomobileSoundInstance;
import com.skd.vehiclery.sound.SlicedLoopingAutomobileSoundInstance;
import com.skd.vehiclery.util.FloatFunc;
import com.skd.vehiclery.util.network.ClientPackets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.GrassColor;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class VehicleryClient {
    // TODO(debug): temporary one-shot diagnostic logging for the oversized/black automobile item
    // render bug reported on a real client -- remove once the root cause is confirmed and fixed.
    private static final Set<net.minecraft.resources.Identifier> DEBUG_LOGGED_AUTOMOBILE_ITEM = ConcurrentHashMap.newKeySet();
    private static final Set<String> DEBUG_LOGGED_COMPONENT_TEXTURES = ConcurrentHashMap.newKeySet();

    public static final BlockTintSource GRASS_COLOR = new BlockTintSource() {
        @Override
        public int color(net.minecraft.world.level.block.state.BlockState state) {
            return GrassColor.get(0.5D, 1.0D);
        }

        @Override
        public int colorInWorld(net.minecraft.world.level.block.state.BlockState state, net.minecraft.client.renderer.block.BlockAndTintGetter level, net.minecraft.core.BlockPos pos) {
            return level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.get(0.5D, 1.0D);
        }
    };
    public static final ItemTintSource GRASS_ITEM_COLOR = new GrassColorSource(0.5F, 1.0F);

    public static void init() {
        AutomobileModels.init();

        initBlocks();
        initItems();
        initEntities();
        ClientPackets.initClient();

        Platform.get().controller().initCompat();

        Platform.get().registerClientCommand((dispatcher, registries) ->
                dispatcher.register(LiteralArgumentBuilder.<SharedSuggestionProvider>literal("vehicleryc")
                        .then(LiteralArgumentBuilder.<SharedSuggestionProvider>literal("dump")
                                .executes(ctx -> {
                                    if (tryDumpBuiltinResources(registries)) {
                                        sendClientMessage("Dumped all resources to .minecraft/vehiclery_dump/");
                                        return 0;
                                    } else {
                                        sendClientMessage("Error dumping resources! See game log for details.");
                                        return 1;
                                    }
                                })
                        )
                )
        );
    }

    public static void initBlocks() {
        Platform.get().blockEntityRenderer(VehicleryBlocks.AUTOMOBILE_ASSEMBLER_ENTITY.require(), AutomobileAssemblerBlockEntityRenderer::new);
    }

    public static void initItems() {
        Platform.get().builtinItemRenderer(VehicleryItems.AUTOMOBILE.require(), (stack, type, pose, buffers, light, overlay) -> {
            var data = stack.get(VehicleryItems.COMPONENT_AUTOMOBILE_DATA.require());
            if (data == null) return;

            var lvl = Minecraft.getInstance().level;
            if (lvl == null) return;

            var frameOpt = lvl.registryAccess().lookupOrThrow(AutomobileFrame.REGISTRY).get(data.frame());
            var wheelOpt = lvl.registryAccess().lookupOrThrow(AutomobileWheel.REGISTRY).get(data.wheel());
            var engineOpt = lvl.registryAccess().lookupOrThrow(AutomobileEngine.REGISTRY).get(data.engine());

            if (frameOpt.isEmpty() || wheelOpt.isEmpty() || engineOpt.isEmpty()) {
                return;
            }
            var frame = frameOpt.get().value();
            var wheel = wheelOpt.get().value();
            var engine = engineOpt.get().value();

            float lengthPx = Math.max(frame.model().lengthPx(), 8.0f);
            float scale = 0.77f * (16f / lengthPx);
            if (DEBUG_LOGGED_AUTOMOBILE_ITEM.add(data.frame().identifier())) {
                Vehiclery.LOG.info("[DEBUG render] automobile item frame={} lengthPx={} scale={} poseScale=(pre-submit)", data.frame().identifier(), lengthPx, scale);
            }
            pose.scale(scale, scale, scale);
            AutomobileRenderer.render(pose, buffers, light, overlay, 0f, new SimpleRenderableAutomobile(frame, engine, wheel));
        });
        componentItemRenderer(VehicleryItems.AUTOMOBILE_FRAME.require(),
                t -> AutomobileModels.getModel(t.model().modelId()),
                t -> t.model().texture(), t -> 0.77f * (16f / t.model().lengthPx())
        );
        componentItemRenderer(VehicleryItems.AUTOMOBILE_WHEEL.require(),
                t -> AutomobileModels.getModel(t.model().modelId()),
                t -> t.model().texture(), t -> 6 / t.model().radius()
        );
        componentItemRenderer(VehicleryItems.AUTOMOBILE_ENGINE.require(),
                t -> AutomobileModels.getModel(t.model().modelId()),
                t -> t.model().texture(), t -> 1
        );
        componentItemRenderer(VehicleryItems.REAR_ATTACHMENT.require(),
                t -> AutomobileModels.getModel(t.model().modelId()),
                t -> t.model().texture(), t -> 1
        );
        componentItemRenderer(VehicleryItems.FRONT_ATTACHMENT.require(),
                t -> AutomobileModels.getModel(t.model().modelId()),
                t -> t.model().texture(), t -> t.model().scale()
        );
        // Note: "stop" item model predicate for the autopilot sign was previously registered here.
        // In 26.2 the ItemProperties.register API was removed; the predicate must now be declared
        // in the item's model JSON via an IsUsingItem conditional property (out of scope for compile fix).
    }

    public static void initMenuScreens(MenuScreenRegistrar screens) {
        screens.accept(Vehiclery.AUTO_MECHANIC_SCREEN, AutoMechanicTableScreen::new);
        screens.accept(Vehiclery.SINGLE_SLOT_SCREEN, SingleSlotScreen::new);
    }

    public static <T extends AutomobileComponent<T>, V> void componentItemRenderer(AutomobileComponentItem<T, V> item, Function<T, com.skd.vehiclery.automobile.render.RenderableModel> modelProvider, Function<T, Identifier> textureProvider, FloatFunc<T> scaleProvider) {
        Platform.get().builtinItemRenderer(item, (stack, mode, matrices, buffers, light, overlay) -> {
            var lvl = Minecraft.getInstance().level;
            if (lvl == null) return;

            var component = item.getComponent(stack, lvl.registryAccess());
            if (item.isVisible(component)) {
                var model = modelProvider.apply(component);
                if (model == null) return;
                if (model instanceof BaseModel base) {
                    base.setDefaultState(0);
                }

                float scale = scaleProvider.apply(component);
                matrices.translate(0.5, 0, 0.5);
                matrices.scale(scale, -scale, -scale);
                var componentTexture = textureProvider.apply(component);
                var renderType = model.renderType(componentTexture);
                if (DEBUG_LOGGED_COMPONENT_TEXTURES.add(item.getClass().getSimpleName() + "/" + componentTexture)) {
                    boolean exists = false;
                    try {
                        exists = Minecraft.getInstance().getResourceManager().getResource(componentTexture).isPresent();
                    } catch (Exception ignored) {}
                    Vehiclery.LOG.info("[DEBUG texture] componentItemRenderer item={} texture={} resourceExists={} modelClass={} scale={} light={} overlay={} mode={}",
                            item.getClass().getSimpleName(), componentTexture, exists, model.getClass().getSimpleName(), scale, light, overlay, mode);
                }
                buffers.submitCustomGeometry(matrices, renderType, (pose, vc) ->
                        model.renderModel(matrices, vc, light, overlay, 0xFFFFFFFF));

                if (model instanceof BaseModel base) {
                    base.doOtherLayerRender(matrices, buffers, light, overlay);
                }
            }
        });
    }

    public static void initEntities() {
        var libs = Platform.get();

        libs.entityRenderer(VehicleryEntities.AUTOMOBILE.require(), AutomobileEntityRenderer::new);
        libs.entityRenderer(VehicleryEntities.HITBOX.require(), NoopRenderer::new);

        AutomobileEntity.engineSound = auto -> {
            if (auto.getEngine().isEmpty()) {
                return;
            }

            var client = Minecraft.getInstance();
            client.getSoundManager().play(new AutomobileSoundInstance.EngineSound(client, auto));
        };
        AutomobileEntity.skidSound = auto -> {
            var client = Minecraft.getInstance();
            client.getSoundManager().play(new AutomobileSoundInstance.SkiddingSound(client, auto));
        };
        AutomobileEntity.hornSound = auto -> {
            var client = Minecraft.getInstance();
            var horn = auto.getFrame().horn();
            for (float pitch : horn.pitches()) {
                client.getSoundManager().play(new SlicedLoopingAutomobileSoundInstance.HornSound(client, auto, horn, pitch));
            }
        };
    }

    public static double modifyBoostFov(Minecraft client, double old, float tickDelta) {
        var player = client.player;

        if (player.getVehicle() instanceof AutomobileEntity auto) {
            return old + ((Math.sqrt(auto.getBoostSpeed(tickDelta)) * 18) * client.options.fovEffectScale().get());
        }

        return old;
    }

    public static boolean tryDumpBuiltinResources(HolderLookup.Provider registries) {
        try {
            AutomobileModels.dump();
            Vehiclery.dumpDynamicRegistries(registries);

            return true;
        } catch (IOException ex) {
            Vehiclery.LOG.error("Error dumping Vehiclery resources: ", ex);
        }
        return false;
    }

    public static void sendClientMessage(String message) {
        var mc = Minecraft.getInstance();
        var txt = Component.literal(message);
        mc.gui.hud.getChat().addClientSystemMessage(txt);
        mc.getNarrator().saySystemNow(txt);
    }
}