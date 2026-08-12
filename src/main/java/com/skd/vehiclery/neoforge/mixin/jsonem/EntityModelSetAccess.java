package com.skd.vehiclery.neoforge.mixin.jsonem;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(EntityModelSet.class)
public interface EntityModelSetAccess {
    @Accessor("roots")
    Map<ModelLayerLocation, LayerDefinition> jsonem$getRoots();
}
