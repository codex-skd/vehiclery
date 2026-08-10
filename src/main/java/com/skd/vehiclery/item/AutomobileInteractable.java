package com.skd.vehiclery.item;

import com.skd.vehiclery.entity.AutomobileEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface AutomobileInteractable {
    InteractionResult interactAutomobile(ItemStack stack, Player player, InteractionHand hand, AutomobileEntity automobile);
}
