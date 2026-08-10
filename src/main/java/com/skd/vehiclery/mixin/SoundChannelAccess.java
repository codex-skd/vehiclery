package com.skd.vehiclery.mixin;

import com.mojang.blaze3d.audio.Channel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(Channel.class)
public interface SoundChannelAccess {
    @Accessor("source")
    int vehiclery$getSource();

    @Accessor("initialized")
    AtomicBoolean vehiclery$getInitialized();
}
