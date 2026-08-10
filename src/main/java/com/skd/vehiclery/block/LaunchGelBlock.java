package com.skd.vehiclery.block;

import org.jetbrains.annotations.Nullable;
import com.skd.vehiclery.entity.AutomobileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LaunchGelBlock extends Block {
    public static final VoxelShape SHAPE = box(0, 0, 0, 16, 1, 16);

    public LaunchGelBlock(Properties settings) {
        super(settings);
    }

    public boolean canExistAt(LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.below()).isFaceSturdy(world, pos, Direction.UP);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity, net.minecraft.world.entity.InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        super.entityInside(state, world, pos, entity, effectApplier, isPrecise);

        if (entity instanceof AutomobileEntity automobile && automobile.automobileOnGround()) {
            automobile.boost(0.14f, 7);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canExistAt(world, pos);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, @Nullable net.minecraft.world.level.redstone.Orientation orientation, boolean notify) {
        super.neighborChanged(state, world, pos, block, orientation, notify);

        if (!canExistAt(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
