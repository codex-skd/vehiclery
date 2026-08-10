package com.skd.vehiclery.neoforge.mixin.jsonem;

import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MaterialDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LayerDefinition.class)
public interface LayerDefinitionAccess {
    @Accessor("mesh")
    MeshDefinition vehiclery$root();

    @Accessor("material")
    MaterialDefinition vehiclery$texture();

    @Invoker("<init>")
    static LayerDefinition vehiclery$create(MeshDefinition data, MaterialDefinition dimensions) {
        throw new AssertionError();
    }
}
