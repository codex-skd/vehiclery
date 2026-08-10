package com.skd.vehiclery.neoforge.block.render;

import com.skd.vehiclery.block.model.SlopeBakedModel;
import com.skd.vehiclery.block.model.SlopeUnbakedModel;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.dispatch.ModelState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.SimpleModelWrapper;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.DynamicBlockStateModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

// TODO(port): BakedModel/IDynamicBakedModel + NeoForge's old ModelData-based per-position dynamic
// quads no longer exist. The replacement is DynamicBlockStateModel#collectParts, which is called
// fresh per block position/state and can build the QuadCollection directly -- no separate
// ModelData/ModelProperty round trip needed anymore, since we can just recompute the position-
// dependent sprite/color/border data right here.
public class NeoForgeSlopeBakedModel extends SlopeBakedModel implements DynamicBlockStateModel {
    public NeoForgeSlopeBakedModel(TextureAtlasSprite frame, Map<BlockState, TextureAtlasSprite> frameTexOverrides, @Nullable TextureAtlasSprite plateInner,
                                   @Nullable TextureAtlasSprite plateOuter, ModelState settings, SlopeUnbakedModel.Type type) {
        super(frame, frameTexOverrides, plateInner, plateOuter, settings, type);
    }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockStateModelPart> parts) {
        var frameSprite = this.getFrameSprite(level, pos);
        var frameColor = this.getFrameColor(level, pos);

        boolean borderedLeft = false;
        boolean borderedRight = false;
        if (state.getBlock() instanceof HorizontalDirectionalBlock) {
            var dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            borderedLeft = level.getBlockState(pos.relative(dir.getCounterClockWise(Direction.Axis.Y))) == state;
            borderedRight = level.getBlockState(pos.relative(dir.getClockWise(Direction.Axis.Y))) == state;
        }

        var builder = new QuadCollection.Builder();
        var geo = new NeoForgeGeometryBuilder(this.settings.getRotation().getMatrix(), builder);
        this.buildSlopeGeometry(frameSprite, geo, frameColor, borderedLeft, borderedRight);

        parts.add(new SimpleModelWrapper(builder.build(), true, new Material.Baked(frameSprite, false)));
    }

    @Override
    public Material.Baked particleMaterial() {
        return new Material.Baked(this.getFrameSprite(null, null), false);
    }

    @Override
    public @BakedQuad.MaterialFlags int materialFlags() {
        return 0;
    }
}
