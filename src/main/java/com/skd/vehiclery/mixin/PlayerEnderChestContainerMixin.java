package com.skd.vehiclery.mixin;

import com.skd.vehiclery.automobile.attachment.rear.BaseChestRearAttachment;
import com.skd.vehiclery.util.duck.EnderChestContainerDuck;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEnderChestContainer.class)
public class PlayerEnderChestContainerMixin implements EnderChestContainerDuck {
    private @Nullable BaseChestRearAttachment vehiclery$activeAttachment = null;

    @Override
    public void vehiclery$setActiveAttachment(BaseChestRearAttachment attachment) {
        this.vehiclery$activeAttachment = attachment;
    }

    @Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
    private void vehiclery$allowPlayerUseWithAttachment(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (this.vehiclery$activeAttachment != null) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "startOpen", at = @At("TAIL"))
    private void vehiclery$openActiveAttachment(ContainerUser containerUser, CallbackInfo ci) {
        if (this.vehiclery$activeAttachment != null && containerUser instanceof Player player) {
            this.vehiclery$activeAttachment.open(player);
        }
    }

    @Inject(method = "stopOpen", at = @At("TAIL"))
    private void vehiclery$closeActiveAttachment(ContainerUser containerUser, CallbackInfo ci) {
        if (this.vehiclery$activeAttachment != null && containerUser instanceof Player player) {
            this.vehiclery$activeAttachment.close(player);
        }
        this.vehiclery$activeAttachment = null;
    }
}
