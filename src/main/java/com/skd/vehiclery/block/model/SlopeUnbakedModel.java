package com.skd.vehiclery.block.model;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.skd.vehiclery.Vehiclery;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.MaterialBaker;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

// TODO(port): custom block-state models used to be registered as a JSON "loader" string via
// IGeometryLoader; that whole mechanism (net.neoforged.neoforge.client.model.geometry) is gone.
// The replacement is CustomUnbakedBlockStateModel, registered via the RegisterBlockStateModels
// event with a MapCodec (see NeoForgeSlopeGeometryLoader for the registration + JSON reading side,
// now folded into this class's CODEC). Rotation ("x"/"y" in the blockstate variant JSON) is no
// longer supplied separately by the baking pipeline for custom models, so it's parsed here directly
// via the same Variant.SimpleModelState the vanilla variant system uses.
public class SlopeUnbakedModel implements CustomUnbakedBlockStateModel {
    public static final Identifier MODEL_SLOPE_BOTTOM = Vehiclery.rl("block/slope_bottom");
    public static final Identifier MODEL_SLOPE_TOP = Vehiclery.rl("block/slope_top");
    public static final Identifier MODEL_STEEP_SLOPE = Vehiclery.rl("block/steep_slope");
    public static final Identifier MODEL_SLOPE_BOTTOM_DASH_PANEL = Vehiclery.rl("block/slope_bottom_dash_panel");
    public static final Identifier MODEL_SLOPE_TOP_DASH_PANEL = Vehiclery.rl("block/slope_top_dash_panel");
    public static final Identifier MODEL_STEEP_SLOPE_DASH_PANEL = Vehiclery.rl("block/steep_slope_dash_panel");
    public static final Identifier MODEL_SLOPE_BOTTOM_DASH_PANEL_OFF = Vehiclery.rl("block/slope_bottom_dash_panel_off");
    public static final Identifier MODEL_SLOPE_TOP_DASH_PANEL_OFF = Vehiclery.rl("block/slope_top_dash_panel_off");
    public static final Identifier MODEL_STEEP_SLOPE_DASH_PANEL_OFF = Vehiclery.rl("block/steep_slope_dash_panel_off");

    public static final Identifier TEX_FRAME = Vehiclery.rl("block/slope_frame");
    public static final Identifier TEX_DASH_PANEL = Vehiclery.rl("block/dash_panel");
    public static final Identifier TEX_DASH_PANEL_OFF = Vehiclery.rl("block/dash_panel_off");
    public static final Identifier TEX_DASH_PANEL_FRAME = Vehiclery.rl("block/dash_panel_frame");

    public static final Map<Identifier, Supplier<SlopeUnbakedModel>> DEFAULT_MODELS = ImmutableMap.of(
            MODEL_SLOPE_TOP, () -> new SlopeUnbakedModel(MODEL_SLOPE_TOP, Type.TOP, TEX_FRAME, null, null),
            MODEL_SLOPE_BOTTOM, () -> new SlopeUnbakedModel(MODEL_SLOPE_BOTTOM, Type.BOTTOM, TEX_FRAME, null, null),
            MODEL_STEEP_SLOPE, () -> new SlopeUnbakedModel(MODEL_STEEP_SLOPE, Type.STEEP, TEX_FRAME, null, null),
            MODEL_SLOPE_TOP_DASH_PANEL, () -> new SlopeUnbakedModel(MODEL_SLOPE_TOP_DASH_PANEL, Type.TOP, TEX_FRAME, TEX_DASH_PANEL, TEX_DASH_PANEL_FRAME),
            MODEL_SLOPE_BOTTOM_DASH_PANEL, () -> new SlopeUnbakedModel(MODEL_SLOPE_BOTTOM_DASH_PANEL, Type.BOTTOM, TEX_FRAME, TEX_DASH_PANEL, TEX_DASH_PANEL_FRAME),
            MODEL_STEEP_SLOPE_DASH_PANEL, () -> new SlopeUnbakedModel(MODEL_STEEP_SLOPE_DASH_PANEL, Type.STEEP, TEX_FRAME, TEX_DASH_PANEL, TEX_DASH_PANEL_FRAME),
            MODEL_SLOPE_TOP_DASH_PANEL_OFF, () -> new SlopeUnbakedModel(MODEL_SLOPE_TOP_DASH_PANEL_OFF, Type.TOP, TEX_FRAME, TEX_DASH_PANEL_OFF, TEX_DASH_PANEL_FRAME),
            MODEL_SLOPE_BOTTOM_DASH_PANEL_OFF, () -> new SlopeUnbakedModel(MODEL_SLOPE_BOTTOM_DASH_PANEL_OFF, Type.BOTTOM, TEX_FRAME, TEX_DASH_PANEL_OFF, TEX_DASH_PANEL_FRAME),
            MODEL_STEEP_SLOPE_DASH_PANEL_OFF, () -> new SlopeUnbakedModel(MODEL_STEEP_SLOPE_DASH_PANEL_OFF, Type.STEEP, TEX_FRAME, TEX_DASH_PANEL_OFF, TEX_DASH_PANEL_FRAME)
    );

    public static final MapCodec<SlopeUnbakedModel> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Identifier.CODEC.fieldOf("represents").forGetter(SlopeUnbakedModel::represents),
            Variant.SimpleModelState.MAP_CODEC.forGetter(SlopeUnbakedModel::modelState)
    ).apply(inst, (represents, modelState) -> DEFAULT_MODELS.get(represents).get().withState(modelState)));

    private final Identifier represents;
    private final Type type;
    private final Identifier frameTexId;
    private final @Nullable Identifier plateInnerTexId;
    private final @Nullable Identifier plateOuterTexId;
    private final Variant.SimpleModelState modelState;

    public SlopeUnbakedModel(Identifier represents, Type type, Identifier frameTex, @Nullable Identifier plateInnerTex,
                             @Nullable Identifier plateOuterTex) {
        this(represents, type, frameTex, plateInnerTex, plateOuterTex, Variant.SimpleModelState.DEFAULT);
    }

    private SlopeUnbakedModel(Identifier represents, Type type, Identifier frameTex, @Nullable Identifier plateInnerTex,
                              @Nullable Identifier plateOuterTex, Variant.SimpleModelState modelState) {
        this.represents = represents;
        this.type = type;
        this.frameTexId = frameTex;
        this.plateInnerTexId = plateInnerTex;
        this.plateOuterTexId = plateOuterTex;
        this.modelState = modelState;
    }

    public Identifier represents() {
        return this.represents;
    }

    public Variant.SimpleModelState modelState() {
        return this.modelState;
    }

    public SlopeUnbakedModel withState(Variant.SimpleModelState modelState) {
        return new SlopeUnbakedModel(this.represents, this.type, this.frameTexId, this.plateInnerTexId, this.plateOuterTexId, modelState);
    }

    @Override
    public MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
        return CODEC;
    }

    @Override
    public void resolveDependencies(ResolvableModel.Resolver resolver) {
        // No nested model references -- geometry is generated procedurally.
    }

    // TODO: Something better than this that supports other mods and resource packs
    private static Map<BlockState, TextureAtlasSprite> createFrameTexOverrides(MaterialBaker materials, ModelDebugName debugName) {
        return ImmutableMap.of(
                Blocks.GRASS_BLOCK.defaultBlockState(), vanillaSprite(materials, debugName, "block/grass_block_top"),
                Blocks.PODZOL.defaultBlockState(), vanillaSprite(materials, debugName, "block/podzol_top"),
                Blocks.MYCELIUM.defaultBlockState(), vanillaSprite(materials, debugName, "block/mycelium_top"),
                Blocks.CRIMSON_NYLIUM.defaultBlockState(), vanillaSprite(materials, debugName, "block/crimson_nylium"),
                Blocks.WARPED_NYLIUM.defaultBlockState(), vanillaSprite(materials, debugName, "block/warped_nylium")
        );
    }

    @Override
    public BlockStateModel bake(ModelBaker baker) {
        ModelDebugName debugName = () -> this.represents.toString();
        var materials = baker.materials();

        var frameSprite = materials.get(new Material(this.frameTexId), debugName).sprite();
        var plateInnerSprite = this.plateInnerTexId != null ? materials.get(new Material(this.plateInnerTexId), debugName).sprite() : null;
        var plateOuterSprite = this.plateOuterTexId != null ? materials.get(new Material(this.plateOuterTexId), debugName).sprite() : null;

        return SlopeBakedModel.impl.create(frameSprite, createFrameTexOverrides(materials, debugName),
                plateInnerSprite, plateOuterSprite, this.modelState.asModelState(), this.type);
    }

    private static TextureAtlasSprite vanillaSprite(MaterialBaker materials, ModelDebugName debugName, String name) {
        return materials.get(new Material(Identifier.fromNamespaceAndPath("minecraft", name)), debugName).sprite();
    }

    public enum Type {
        BOTTOM, TOP, STEEP
    }
}
