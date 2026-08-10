package com.skd.vehiclery.block;

import com.mojang.serialization.MapCodec;
import com.skd.vehiclery.entity.AutomobileEntity;
import com.skd.vehiclery.entity.HitboxEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class AutomobilePressurePlateBlock extends PressurePlateBlock {
    public static final MapCodec<AutomobilePressurePlateBlock> CODEC = simpleCodec(AutomobilePressurePlateBlock::new);

    public AutomobilePressurePlateBlock(Properties properties) {
        super(BlockSetType.IRON, properties);
    }

    @Override
    protected int getSignalStrength(Level level, BlockPos pos) {
        var box = TOUCH_AABB.move(pos);
        return getEntityCount(level, box, AutomobileEntity.class) +
                getEntityCount(level, box, HitboxEntity.class)
                > 0 ? 15 : 0;
    }
}
