package com.skd.vehiclery.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.vehiclery.automobile.render.AutomobileRenderer;
import com.skd.vehiclery.entity.AutomobileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.joml.Quaternionf;

public class AutomobileEntityRenderer extends EntityRenderer<AutomobileEntity> {
    public AutomobileEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTextureLocation(AutomobileEntity entity) {
        return null;
    }

    @Override
    public void render(AutomobileEntity entity, float yaw, float tickDelta, PoseStack pose, MultiBufferSource buffers, int light) {
        pose.pushPose();
        float offsetY = entity.getDisplacement().getVerticalOffset(tickDelta, entity);
        var rotation = new Quaternionf();
        entity.getDisplacement().getAngular(tickDelta, rotation);

        pose.translate(0, offsetY, 0);
        pose.mulPose(rotation);

        AutomobileRenderer.render(pose, buffers, light, OverlayTexture.NO_OVERLAY, tickDelta, entity);
        pose.popPose();
    }
}
