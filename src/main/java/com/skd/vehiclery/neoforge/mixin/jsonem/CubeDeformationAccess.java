package com.skd.vehiclery.neoforge.mixin.jsonem;

import net.minecraft.client.model.geom.builders.CubeDeformation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CubeDeformation.class)
public interface CubeDeformationAccess {
    @Accessor("growX")
    float vehiclery$radiusX();

    @Accessor("growY")
    float vehiclery$radiusY();

    @Accessor("growZ")
    float vehiclery$radiusZ();
}
