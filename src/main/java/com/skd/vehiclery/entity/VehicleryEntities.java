package com.skd.vehiclery.entity;

import com.skd.vehiclery.Vehiclery;
import com.skd.vehiclery.platform.Platform;
import com.skd.vehiclery.util.Eventual;
import com.skd.vehiclery.util.RegistryQueue;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;

import java.util.Optional;

public enum VehicleryEntities {;
    public static final Eventual<EntityType<AutomobileEntity>> AUTOMOBILE = RegistryQueue.register(BuiltInRegistries.ENTITY_TYPE,
            Vehiclery.rl("automobile"),
            () -> Platform.get().entityType(MobCategory.MISC, AutomobileEntity::new, EntityDimensions.scalable(1f, 0.66f), 3, 10, false, "automobile")
    );
    public static final Eventual<EntityType<HitboxEntity>> HITBOX = RegistryQueue.register(BuiltInRegistries.ENTITY_TYPE,
            Vehiclery.rl("hitbox"),
            () -> Platform.get().entityType(MobCategory.MISC, HitboxEntity::new, EntityDimensions.scalable(1.1f, 0.7f), 3, 10, true, "automobile_hitbox")
    );

    public static final TagKey<EntityType<?>> DASH_PANEL_BOOSTABLES = TagKey.create(Registries.ENTITY_TYPE, Vehiclery.rl("dash_panel_boostables"));

    public static final ResourceKey<DamageType> AUTOMOBILE_DAMAGE_SOURCE = ResourceKey.create(Registries.DAMAGE_TYPE, Vehiclery.rl("automobile"));

    public static Optional<DamageSource> automobileDamageSource(Level level) {
        return level.registryAccess()
                .lookupOrThrow(Registries.DAMAGE_TYPE)
                .get(AUTOMOBILE_DAMAGE_SOURCE)
                .map(DamageSource::new);
    }

    public static void init() {
    }
}
