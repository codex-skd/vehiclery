package com.skd.vehiclery.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.vehiclery.automobile.render.AutomobileRenderer;
import com.skd.vehiclery.entity.AutomobileEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.joml.Quaternionf;

// TODO(port): EntityRenderer<T> now requires a second generic param (a custom EntityRenderState)
// and splits rendering into createRenderState()/extractRenderState()/submit() instead of a single
// render() method with direct entity access. See AutomobileRenderState for details on the shortcut
// taken here (kept a live entity reference instead of extracting individual fields).
public class AutomobileEntityRenderer extends EntityRenderer<AutomobileEntity, AutomobileRenderState> {
    // TODO(debug): temporary one-shot diagnostic logging for the "nothing appears when placing"
    // bug reported on a real client -- remove once the root cause is confirmed and fixed.
    private static boolean debugLogged = false;

    public AutomobileEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public AutomobileRenderState createRenderState() {
        return new AutomobileRenderState();
    }

    @Override
    public void extractRenderState(AutomobileEntity entity, AutomobileRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.automobile = entity;
        state.tickDelta = partialTicks;
        state.lightCoords = this.getPackedLightCoords(entity, partialTicks);
    }

    @Override
    public void submit(AutomobileRenderState state, PoseStack pose, SubmitNodeCollector collector, CameraRenderState camera) {
        pose.pushPose();
        float offsetY = state.automobile.getDisplacement().getVerticalOffset(state.tickDelta, state.automobile);
        var rotation = new Quaternionf();
        state.automobile.getDisplacement().getAngular(state.tickDelta, rotation);

        if (!debugLogged) {
            debugLogged = true;
            com.skd.vehiclery.Vehiclery.LOG.info(
                    "[DEBUG render] AutomobileEntityRenderer.submit reached: entity={} pos={} offsetY={} frame={} wheel={} engine={}",
                    state.automobile.getId(), state.automobile.position(), offsetY,
                    state.automobile.getFrame().isEmpty() ? "EMPTY" : state.automobile.getFrame().getId(),
                    state.automobile.getWheels().isEmpty() ? "EMPTY" : state.automobile.getWheels().getId(),
                    state.automobile.getEngine().isEmpty() ? "EMPTY" : state.automobile.getEngine().getId());
        }

        pose.translate(0, offsetY, 0);
        pose.mulPose(rotation);

        AutomobileRenderer.render(pose, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, state.tickDelta, state.automobile);
        pose.popPose();
    }
}
