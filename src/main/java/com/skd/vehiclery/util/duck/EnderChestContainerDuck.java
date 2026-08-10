package com.skd.vehiclery.util.duck;

import com.skd.vehiclery.automobile.attachment.rear.BaseChestRearAttachment;
import net.minecraft.world.inventory.PlayerEnderChestContainer;

public interface EnderChestContainerDuck {
    void vehiclery$setActiveAttachment(BaseChestRearAttachment attachment);

    static EnderChestContainerDuck of(PlayerEnderChestContainer inv) {
        return (EnderChestContainerDuck) inv;
    }
}
