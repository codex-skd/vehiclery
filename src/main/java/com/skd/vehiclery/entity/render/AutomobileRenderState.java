package com.skd.vehiclery.entity.render;

import com.skd.vehiclery.entity.AutomobileEntity;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

// TODO(port): EntityRenderer now separates state extraction (main thread, reads live entity data)
// from submission (render thread, reads only the extracted state) via EntityRenderState subclasses.
// For a fast port we don't extract each individual field used by AutomobileRenderer -- we just keep
// a reference to the live AutomobileEntity and tickDelta, and still read from it directly during
// submit(). This defeats the thread-safety purpose of the split (submit() may run off the main
// thread and touch live entity/world state), which is a known risk, not a correctness guarantee.
public class AutomobileRenderState extends EntityRenderState {
    public AutomobileEntity automobile;
    public float tickDelta;
}
