package com.skd.vehiclery.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.skd.vehiclery.sound.AdvancedSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {
    @Inject(method = "play",
            at = @At(value = "INVOKE", shift = At.Shift.AFTER, ordinal = 0,
                    target = "Lnet/minecraft/client/sounds/ChannelAccess$ChannelHandle;execute(Ljava/util/function/Consumer;)V"))
    private void vehiclery$exposeALForPlay(SoundInstance sound, CallbackInfoReturnable<SoundEngine.PlayResult> cir,
                                              @Local(ordinal = 0) ChannelAccess.ChannelHandle channelHandle) {
        if (sound instanceof AdvancedSoundInstance adv) {
            var action = adv.setupALState();
            if (action != null) channelHandle.execute(ch -> {
                var channel = ((SoundChannelAccess) ch);
                if (channel.vehiclery$getInitialized().get()) action.accept(channel.vehiclery$getSource());
            });
        }
    }

    @Inject(method = "tickInGameSound",
            at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0,
                     target = "Lnet/minecraft/client/sounds/ChannelAccess$ChannelHandle;execute(Ljava/util/function/Consumer;)V"))
    private void vehiclery$exposeALForTick(CallbackInfo ci,
                                                    @Local(ordinal = 0) TickableSoundInstance sound,
                                                    @Local(ordinal = 0) ChannelAccess.ChannelHandle channelHandle) {
        if (sound instanceof AdvancedSoundInstance adv) {
            var action = adv.updateALState();
            if (action != null) channelHandle.execute(ch -> {
                var channel = ((SoundChannelAccess) ch);
                if (channel.vehiclery$getInitialized().get()) action.accept(channel.vehiclery$getSource());
            });
        }
    }
}
