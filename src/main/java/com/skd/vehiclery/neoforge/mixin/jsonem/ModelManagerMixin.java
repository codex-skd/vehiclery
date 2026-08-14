package com.skd.vehiclery.neoforge.mixin.jsonem;

import com.llamalad7.mixinextras.sugar.Local;
import com.skd.vehiclery.neoforge.vendored.jsonem.util.JsonEntityModelUtil;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Mixin(ModelManager.class)
public class ModelManagerMixin {
    @Redirect(method = "reload", at = @At(value = "INVOKE", ordinal = 0,
            target = "Ljava/util/concurrent/CompletableFuture;supplyAsync(Ljava/util/function/Supplier;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;"))
    private CompletableFuture<EntityModelSet> jsonem$loadJsonEntityModels(Supplier<EntityModelSet> supplier, Executor executor,
                                                                           @Local ResourceManager manager) {
        return CompletableFuture.supplyAsync(() -> {
            EntityModelSet vanilla = supplier.get();
            Map<ModelLayerLocation, LayerDefinition> roots = new HashMap<>(((EntityModelSetAccess) (Object) vanilla).jsonem$getRoots());
            JsonEntityModelUtil.loadModels(manager, roots);
            return new EntityModelSet(roots);
        }, executor);
    }
}
