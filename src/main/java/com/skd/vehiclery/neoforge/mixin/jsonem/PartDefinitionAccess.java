package com.skd.vehiclery.neoforge.mixin.jsonem;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Map;

@Mixin(PartDefinition.class)
public interface PartDefinitionAccess {
    @Accessor("cubes")
    List<CubeDefinition> vehiclery$cuboids();

    @Accessor("partPose")
    PartPose vehiclery$transform();

    @Accessor("children")
    Map<String, PartDefinition> vehiclery$children();

    @Invoker("<init>")
    static PartDefinition vehiclery$create(List<CubeDefinition> cuboids, PartPose rotation) {
        throw new AssertionError();
    }
}
