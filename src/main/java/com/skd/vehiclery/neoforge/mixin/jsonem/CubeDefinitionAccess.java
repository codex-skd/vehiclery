package com.skd.vehiclery.neoforge.mixin.jsonem;

import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(CubeDefinition.class)
public interface CubeDefinitionAccess {
    @Accessor("comment")
    String vehiclery$name();

    @Accessor("origin")
    Vector3f vehiclery$offset();

    @Accessor("dimensions")
    Vector3f vehiclery$dimensions();

    @Accessor("grow")
    CubeDeformation vehiclery$dilation();

    @Accessor("mirror")
    boolean vehiclery$mirror();

    @Accessor("texCoord")
    UVPair vehiclery$uv();

    @Accessor("texScale")
    UVPair vehiclery$uvScale();

    @Invoker("<init>")
    static CubeDefinition vehiclery$create(@Nullable String name, float textureX, float textureY, float offsetX, float offsetY, float offsetZ, float sizeX, float sizeY, float sizeZ, CubeDeformation extra, boolean mirror, float textureScaleX, float textureScaleY, Set<Direction> p_273201_) {
        throw new AssertionError();
    }
}
