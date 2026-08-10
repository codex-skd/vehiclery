package com.skd.vehiclery.automobile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.skd.vehiclery.automobile.AutomobileEngine;
import com.skd.vehiclery.automobile.WheelBase;
import com.skd.vehiclery.automobile.render.attachment.front.FrontAttachmentRenderModel;
import com.skd.vehiclery.automobile.render.attachment.rear.RearAttachmentRenderModel;
import com.skd.vehiclery.entity.AutomobileEntity;
import com.skd.vehiclery.util.AUtils;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

// TODO(port): MultiBufferSource (immediate-mode: get a VertexConsumer for a RenderType, write to it
// directly) no longer exists -- rendering now goes through SubmitNodeCollector's deferred submission
// API. We bridge back to our existing VertexConsumer-based model rendering (BaseModel#renderModel
// etc., unchanged) via SubmitNodeCollector#submitCustomGeometry, which hands back a VertexConsumer
// for a given RenderType/pose. This assumes the callback runs synchronously against the *live*
// PoseStack captured here (not the frozen PoseStack.Pose snapshot the callback also receives, which
// we ignore) -- reasonable for a per-frame render callback, but unverified against actual gameplay
// since this can't be tested without running the game.
public enum AutomobileRenderer {;
    public static void render(
            PoseStack pose, SubmitNodeCollector buffers, int light, int overlay,
            float tickDelta, RenderableAutomobile automobile
    ) {
        var frame = automobile.getFrame();
        var wheels = automobile.getWheels();
        var engine = automobile.getEngine();

        var skidEffectModel = AutomobileModels.getSkidEffectModel();
        var exhaustFumesModel = AutomobileModels.getExhaustFumesModel();

        pose.pushPose();

        pose.mulPose(Axis.ZP.rotationDegrees(180));
        pose.mulPose(Axis.YP.rotationDegrees(automobile.getAutomobileYaw(tickDelta) + 180));

        float chassisRaise = wheels.model().radius() / 16;
        float bounce = automobile.getSuspensionBounce(tickDelta) * 0.048f;

        var frameModel = AutomobileModels.getModel(automobile.getFrame().model().modelId());
        var wheelModel = AutomobileModels.getModel(automobile.getWheels().model().modelId());
        var engineModel = AutomobileModels.getModel(automobile.getEngine().model().modelId());
        var rearAttachmentModel = AutomobileModels.getModel(automobile.getRearAttachmentType().model().modelId());
        var frontAttachmentModel = AutomobileModels.getModel(automobile.getFrontAttachmentType().model().modelId());

        pose.translate(0, -chassisRaise, 0);

        // Frame, engine, exhaust
        pose.pushPose();

        pose.translate(0, bounce + (automobile.engineRunning() ? (Math.cos((automobile.getTime() + tickDelta) * 2.7) / 156) : 0), 0);
        var frameTexture = frame.model().texture();
        var engineTexture = engine.model().texture();
        if (!frame.isEmpty() && frameModel != null) {
            buffers.submitCustomGeometry(pose, frameModel.renderType(frameTexture), (framePose, buffer) -> frameModel.renderModel(pose, buffer, light, overlay, 0xFFFFFFFF));
            if (frameModel instanceof BaseModel base) {
                base.doOtherLayerRender(pose, buffers, light, overlay);
            }
        }

        var ePos = frame.model().enginePos().scale(1.0 / 16);
        pose.translate(ePos.x(), -ePos.y(), -ePos.z());
        pose.mulPose(Axis.YP.rotationDegrees(180));
        if (!engine.isEmpty() && engineModel != null) {
            buffers.submitCustomGeometry(pose, engineModel.renderType(engineTexture), (framePose, buffer) -> engineModel.renderModel(pose, buffer, light, overlay, 0xFFFFFFFF));
            if (engineModel instanceof BaseModel base) {
                base.doOtherLayerRender(pose, buffers, light, overlay);
            }
        }

        RenderType exhaustRenderType = null;
        Identifier[] exhaustTexes;
        if (automobile.getBoostTimer() > 0) {
            exhaustTexes = ExhaustFumesModel.FLAME_TEXTURES;
            int index = (int)(automobile.getTime() % exhaustTexes.length);
            exhaustRenderType = RenderTypes.eyes(exhaustTexes[index]);
        } else if (automobile.engineRunning()) {
            exhaustTexes = ExhaustFumesModel.SMOKE_TEXTURES;
            int index = (int)Math.floor(((automobile.getTime() + tickDelta) / 1.5f) % exhaustTexes.length);
            exhaustRenderType = RenderTypes.entityTranslucent(exhaustTexes[index]);
        }
        if (exhaustRenderType != null) {
            for (AutomobileEngine.ExhaustPos exhaust : engine.model().exhausts()) {
                pose.pushPose();

                pose.translate(exhaust.x() / 16, -exhaust.y() / 16, exhaust.z() / 16);
                pose.mulPose(Axis.YP.rotationDegrees(exhaust.yaw()));
                pose.mulPose(Axis.XP.rotationDegrees(exhaust.pitch()));
                buffers.submitCustomGeometry(pose, exhaustRenderType, (framePose, buffer) -> exhaustFumesModel.renderModel(pose, buffer, light, overlay, 0xFFFFFFFF));

                pose.popPose();
            }
        }
        pose.popPose();

        // WHEELS ----------------------------------------
        var wPoses = frame.model().wheelBase().wheels();

        if (!wheels.isEmpty() && wheelModel != null) {
            var wheelRenderType = wheelModel.renderType(wheels.model().texture());
            float wheelAngle = automobile.getWheelAngle(tickDelta);
            int wheelCount = automobile.getWheelCount();

            for (var pos : wPoses) {
                if (wheelCount <= 0) {
                    break;
                }

                float scale = pos.scale();
                float wheelRadius = wheels.model().radius() - (wheels.model().radius() * (scale - 1));
                pose.pushPose();

                pose.translate(pos.right() / 16, wheelRadius / 16, -pos.forward() / 16);

                if (pos.end() == WheelBase.WheelEnd.FRONT) pose.mulPose(Axis.YP.rotationDegrees(automobile.getSteering(tickDelta) * 27));
                pose.translate(0, -chassisRaise, 0);
                pose.mulPose(Axis.XP.rotationDegrees(wheelAngle));
                pose.scale(scale, scale, scale);

                pose.mulPose(Axis.YP.rotationDegrees(180 + pos.yaw()));

                buffers.submitCustomGeometry(pose, wheelRenderType, (framePose, buffer) -> wheelModel.renderModel(pose, buffer, light, overlay, 0xFFFFFFFF));
                if (wheelModel instanceof BaseModel base) {
                    base.doOtherLayerRender(pose, buffers, light, overlay);
                }

                pose.popPose();

                wheelCount--;
            }
        }

        // Rear Attachment
        var rearAtt = automobile.getRearAttachmentType();
        if (!rearAtt.isEmpty() && rearAttachmentModel != null) {
            pose.pushPose();
            pose.translate(0, chassisRaise, frame.model().rearAttachmentPos() / 16);
            pose.mulPose(Axis.YN.rotationDegrees(automobile.getAutomobileYaw(tickDelta) - automobile.getRearAttachmentYaw(tickDelta)));

            pose.translate(0, 0, rearAtt.model().pivotDistPx() / 16);
            if (rearAttachmentModel instanceof RearAttachmentRenderModel rm) {
                rm.setRenderState(automobile.getRearAttachment(), (float) Math.toRadians(automobile.getWheelAngle(tickDelta)), tickDelta);
            }
            buffers.submitCustomGeometry(pose, rearAttachmentModel.renderType(rearAtt.model().texture()), (framePose, buffer) -> rearAttachmentModel.renderModel(pose, buffer, light, overlay, 0xFFFFFFFF));
            if (rearAttachmentModel instanceof BaseModel base) {
                base.doOtherLayerRender(pose, buffers, light, overlay);
            }
            pose.popPose();
        }

        // Front Attachment
        var frontAtt = automobile.getFrontAttachmentType();
        if (!frontAtt.isEmpty() && frontAttachmentModel != null) {
            pose.pushPose();
            pose.translate(0, 0, frame.model().frontAttachmentPos() / -16);

            if (frontAttachmentModel instanceof FrontAttachmentRenderModel fm) {
                fm.setRenderState(automobile.getFrontAttachment(), chassisRaise, tickDelta);
            }
            buffers.submitCustomGeometry(pose, frontAttachmentModel.renderType(frontAtt.model().texture()), (framePose, buffer) -> frontAttachmentModel.renderModel(pose, buffer, light, overlay, 0xFFFFFFFF));
            if (frontAttachmentModel instanceof BaseModel base) {
                base.doOtherLayerRender(pose, buffers, light, overlay);
            }
            pose.popPose();
        }

        // Skid effects
        if ((automobile.getTurboCharge() > AutomobileEntity.SMALL_TURBO_TIME || automobile.debris()) && automobile.automobileOnGround()) {
            var skidTexes = SkidEffectModel.COOL_SPARK_TEXTURES;
            boolean bright = true;
            float r = 1;
            float g = 1;
            float b = 1;
            if (automobile.getTurboCharge() > AutomobileEntity.LARGE_TURBO_TIME) {
                skidTexes = SkidEffectModel.FLAME_TEXTURES;
            } else if (automobile.getTurboCharge() > AutomobileEntity.MEDIUM_TURBO_TIME) {
                skidTexes = SkidEffectModel.HOT_SPARK_TEXTURES;
            } else if (automobile.debris()) {
                skidTexes = SkidEffectModel.DEBRIS_TEXTURES;
                var c = automobile.debrisColor();
                r = c.x() * 0.85f;
                g = c.y() * 0.85f;
                b = c.z() * 0.85f;
                bright = false;
            }
            int index = (int)Math.floor(((automobile.getTime() + tickDelta) / 1.5f) % skidTexes.length);
            var skidEffectRenderType = bright ? RenderTypes.eyes(skidTexes[index]) : RenderTypes.entityCutout(skidTexes[index]);

            for (var pos : wPoses) {
                if (pos.end() == WheelBase.WheelEnd.BACK) {
                    float scale = pos.scale();
                    float heightOffset = wheels.model().radius();
                    float wheelRadius = wheels.model().radius() * scale;
                    float wheelWidth =  (wheels.model().width() / 16) * scale;
                    float back = (wheelRadius > 2) ? (float) (Math.sqrt((wheelRadius * wheelRadius) - Math.pow(wheelRadius - 2, 2)) - 0.85) / 16 : 0.08f;
                    pose.pushPose();

                    float[] skids;
                    switch (pos.side()) {
                        case RIGHT -> skids = new float[] {1};
                        case LEFT -> skids = new float[] {-1};
                        default -> skids = new float[] {-1, 1};
                    }

                    for (float s : skids) {
                        pose.pushPose();
                        pose.translate((pos.right() / 16) + (wheelWidth * s), heightOffset / 16, (-pos.forward() / 16) + back);
                        pose.scale(s, 1, -1);
                        var colorFinal = AUtils.colorToInt(0.6f, r, g, b);
                        buffers.submitCustomGeometry(pose, skidEffectRenderType, (framePose, buffer) -> skidEffectModel.renderModel(pose, buffer, light, overlay, colorFinal));
                        pose.popPose();
                    }
                    pose.popPose();
                }
            }
        }
        // -----------------------------------------------

        pose.popPose();
    }
}
