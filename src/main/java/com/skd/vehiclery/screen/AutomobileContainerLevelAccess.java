package com.skd.vehiclery.screen;

import com.skd.vehiclery.automobile.attachment.rear.BlockRearAttachment;
import com.skd.vehiclery.entity.AutomobileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.BiFunction;

public class AutomobileContainerLevelAccess implements ContainerLevelAccess {
    private final Level world;
    private final AutomobileEntity automobile;

    public AutomobileContainerLevelAccess(AutomobileEntity automobile) {
        this.world = automobile.level();
        this.automobile = automobile;
    }

    @Override
    public <T> Optional<T> evaluate(BiFunction<Level, BlockPos, T> getter) {
        return Optional.of(getter.apply(world, automobile.blockPosition()));
    }

    public BlockState getAttachmentBlockState() {
        return automobile.getRearAttachment() instanceof BlockRearAttachment att ? att.block : Blocks.AIR.defaultBlockState();
    }
}
