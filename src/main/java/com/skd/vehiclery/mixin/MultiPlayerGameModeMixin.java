package com.skd.vehiclery.mixin;

import com.skd.vehiclery.entity.EntityWithInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "isServerControlledInventory", at = @At("HEAD"), cancellable = true)
    private void vehiclery$allowCustomRidingInventories(CallbackInfoReturnable<Boolean> cir) {
        if (this.minecraft.player != null && this.minecraft.player.getVehicle() instanceof EntityWithInventory invEntity && invEntity.hasInventory(this.minecraft.player)) {
            cir.setReturnValue(true);
        }
    }
}
