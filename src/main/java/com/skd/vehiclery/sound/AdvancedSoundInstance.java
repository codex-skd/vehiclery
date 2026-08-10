package com.skd.vehiclery.sound;

import java.util.function.IntConsumer;

public interface AdvancedSoundInstance {
    default IntConsumer setupALState() {
        return null;
    }

    default IntConsumer updateALState() {
        return null;
    }
}
