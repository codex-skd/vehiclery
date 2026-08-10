package com.skd.vehiclery.item;

import com.skd.vehiclery.automobile.attachment.FrontAttachmentType;
import com.skd.vehiclery.entity.AutomobileEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FrontAttachmentItem extends AutomobileComponentItem.Builtin<FrontAttachmentType<?>> implements AutomobileInteractable {
    public FrontAttachmentItem(Properties settings) {
        super(settings, "attachment.front", FrontAttachmentType.REGISTRY);
    }

    @Override
    public InteractionResult interactAutomobile(ItemStack stack, Player player, InteractionHand hand, AutomobileEntity automobile) {
        if (automobile.getFrontAttachment().type.isEmpty()) {
            if (player.level().isClientSide()) {
                return InteractionResult.SUCCESS;
            }

            automobile.setFrontAttachment(getComponent(stack, player.level().registryAccess()));
            automobile.playHitSound(automobile.getHeadPos());
            if (!player.isCreative()) {
                stack.shrink(1);
            }
        }

        return InteractionResult.PASS;
    }
}
