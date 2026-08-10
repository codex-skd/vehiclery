package com.skd.vehiclery.particle;

import com.skd.vehiclery.Vehiclery;
import com.skd.vehiclery.platform.Platform;
import com.skd.vehiclery.util.Eventual;
import com.skd.vehiclery.util.RegistryQueue;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class VehicleryParticles {
    public static final Eventual<SimpleParticleType> DRIFT_SMOKE = RegistryQueue.register(BuiltInRegistries.PARTICLE_TYPE, Vehiclery.rl("drift_smoke"), () -> Platform.get().simpleParticleType(true));

    public static void init() {
    }
}
