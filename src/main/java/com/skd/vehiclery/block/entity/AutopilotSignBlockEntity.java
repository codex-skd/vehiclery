package com.skd.vehiclery.block.entity;

import com.skd.vehiclery.automobile.attachment.front.AutopilotFrontAttachment;
import com.skd.vehiclery.block.VehicleryBlocks;
import com.skd.vehiclery.block.AutopilotSignBlock;
import com.skd.vehiclery.entity.AutomobileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class AutopilotSignBlockEntity extends BlockEntity {
    public static final int BROADCAST_INTERVAL = 4;
    private int broadcastDelay = BROADCAST_INTERVAL;

    public AutopilotSignBlockEntity(BlockPos pos, BlockState blockState) {
        super(VehicleryBlocks.AUTOPILOT_SIGN_ENTITY.require(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AutopilotSignBlockEntity sign) {
        sign.broadcastDelay--;

        if (sign.broadcastDelay <= 0) {
            sign.broadcastDelay = BROADCAST_INTERVAL;

            if (state.getBlock() instanceof AutopilotSignBlock block) {
                var box = new AABB(-16, -2, -16, 16, 2, 16);
                var heading = block.getHeading(state, pos);

                var boxOrigin = heading.planeOrigin().add(heading.limitPlane().scale(block.getDetectBoxOffset(state)));
                box = box.move(boxOrigin);

                for (var auto : level.getEntitiesOfClass(AutomobileEntity.class, box)) {
                    var fAtt = auto.getFrontAttachment();
                    if (fAtt instanceof AutopilotFrontAttachment autopilot) {
                        autopilot.notifyHeadingCommand(heading);
                    }
                }
            }
        }
    }
}
