package com.skd.vehiclery.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import com.skd.vehiclery.entity.AutomobileEntity;
import com.skd.vehiclery.platform.Platform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Shadow
    public ClientInput input;

    @Shadow @Final protected Minecraft minecraft;

    @Inject(method = "rideTick", at = @At("TAIL"))
    public void vehiclery$setAutomobileInputs(CallbackInfo ci) {
        LocalPlayer self = (LocalPlayer)(Object)this;
        if (self.getVehicle() instanceof AutomobileEntity vehicle && vehicle.isDriving(self)) {
            if (Platform.get().controller().inControllerMode() && minecraft.gui.screen() == null) {
                vehicle.provideClientInput(
                        Platform.get().controller().accelerating(),
                        Platform.get().controller().braking(),
                        input.keyPresses.left(),
                        input.keyPresses.right(),
                        Platform.get().controller().drifting(),
                        vehiclery$isSprinting()
                );
            } else {
                vehicle.provideClientInput(
                        input.keyPresses.forward(),
                        input.keyPresses.backward(),
                        input.keyPresses.left(),
                        input.keyPresses.right(),
                        input.keyPresses.jump(),
                        vehiclery$isSprinting()
                );
            }
        }
    }

    @Unique
    private boolean vehiclery$isSprinting() {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), ((KeyMappingAccess) minecraft.options.keySprint).vehiclery$getKey().getValue());
    }
}
