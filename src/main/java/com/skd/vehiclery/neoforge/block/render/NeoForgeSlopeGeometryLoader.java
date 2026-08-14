package com.skd.vehiclery.neoforge.block.render;

import com.skd.vehiclery.Vehiclery;
import net.minecraft.resources.Identifier;

// TODO(port): this used to be an IGeometryLoader implementation (net.neoforged.neoforge.client.model.geometry,
// removed entirely in this Minecraft version) that parsed the "represents" field out of the model JSON itself.
// That JSON-reading logic now lives directly in SlopeUnbakedModel.CODEC, registered via the
// RegisterBlockStateModels event (see VehicleryClientNeoForge#registerBakedModels) instead of
// ModelEvent.RegisterLoaders. This class now just holds the shared registration id.
public final class NeoForgeSlopeGeometryLoader {
    public static final Identifier ID = Vehiclery.rl("slope");

    private NeoForgeSlopeGeometryLoader() {
    }
}
