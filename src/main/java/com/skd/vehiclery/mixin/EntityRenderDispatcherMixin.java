package com.skd.vehiclery.mixin;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;

// TODO(port): this mixin used to inject into EntityRenderDispatcher#render(Entity, ..., PoseStack,
// MultiBufferSource, int) to tilt passengers riding on an AutomobileEntity along with the vehicle's
// suspension/rotation. That render() method no longer exists on EntityRenderDispatcher at all -- entity
// rendering was split into per-entity extractRenderState()/submit() calls (see AutomobileEntityRenderer),
// and the dispatch loop that used to call EntityRenderer#render() moved elsewhere (likely LevelRenderer
// or a dedicated render-state pipeline) with a different signature. Finding the correct new injection
// point needs more research than fits in this pass. Passengers still render in the correct position;
// they just won't visually tilt with the vehicle until this is revisited. Kept as a no-op mixin (valid
// target, no injectors) rather than guessing at an injection point that could silently fail or crash.
@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
}
