package com.skd.vehiclery.block;

import org.jetbrains.annotations.Nullable;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class SteepSlopeWithDashPanelBlock extends SteepSlopeBlock {
    public static final MapCodec<SteepSlopeWithDashPanelBlock> CODEC = Block.simpleCodec(SteepSlopeWithDashPanelBlock::new);

    public SteepSlopeWithDashPanelBlock(Properties settings) {
        super(settings);

        registerDefaultState(defaultBlockState().setValue(DashPanelBlock.POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DashPanelBlock.POWERED);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable net.minecraft.world.level.redstone.Orientation orientation, boolean notify) {
        super.neighborChanged(state, level, pos, block, orientation, notify);

        boolean levelPwr = level.hasNeighborSignal(pos);
        boolean selfPwr = state.getValue(DashPanelBlock.POWERED);

        if (levelPwr != selfPwr) {
            level.setBlockAndUpdate(pos, state.setValue(DashPanelBlock.POWERED, levelPwr));
        }
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(VehicleryBlocks.DASH_PANEL.require());
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity, net.minecraft.world.entity.InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        super.entityInside(state, world, pos, entity, effectApplier, isPrecise);
        DashPanelBlock.onCollideWithDashPanel(state, entity);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}
