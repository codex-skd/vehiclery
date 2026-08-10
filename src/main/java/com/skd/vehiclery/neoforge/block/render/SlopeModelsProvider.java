package com.skd.vehiclery.neoforge.block.render;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

// TODO(port): BlockModelProvider (net.neoforged.neoforge.client.model.generators) no longer exists
// in this form -- the custom block-state model registration moved from JSON "loader" strings to
// codec-based CustomUnbakedBlockStateModel (see SlopeUnbakedModel.CODEC and
// VehicleryClientNeoForge#registerBakedModels). Datagen for these JSON files needs to be rebuilt
// against the new blockstate-model-definition datagen API; this is a no-op stub for now so the
// project compiles -- it does NOT affect runtime block rendering, only the `runData` datagen task.
public class SlopeModelsProvider implements DataProvider {
    public SlopeModelsProvider(PackOutput output) {
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public String getName() {
        return "Vehiclery Slope Models (stub, see TODO)";
    }
}
