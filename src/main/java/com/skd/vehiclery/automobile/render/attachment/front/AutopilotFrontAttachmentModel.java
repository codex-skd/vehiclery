package com.skd.vehiclery.automobile.render.attachment.front;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.vehiclery.Vehiclery;
import com.skd.vehiclery.automobile.attachment.front.AutopilotFrontAttachment;
import com.skd.vehiclery.automobile.attachment.front.FrontAttachment;
import com.skd.vehiclery.automobile.model.ModelDefinition;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class AutopilotFrontAttachmentModel extends FrontAttachmentRenderModel {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Vehiclery.rl("automobile/front_attachment/autopilot"), "main");

    public static final int LIGHT_OFF = 0x0a0600;
    public static final int GLOW_OFF = 0x000000;

    private final ModelPart light;
    private final ModelPart glow;

    private int lightColor = LIGHT_OFF;
    private int glowColor = GLOW_OFF;
    private boolean on = false;

    public AutopilotFrontAttachmentModel(EntityRendererProvider.Context ctx,
                                         ModelDefinition.RenderMaterial material,
                                         ModelLayerLocation layer,
                                         Vector3fc translation, Vector3fc rotation, Vector3fc scale) {
        super(ctx, material, layer, translation, rotation, scale);
        this.light = getChildSafe(this.root, "light");
        this.glow = getChildSafe(this.root, "glow");
    }

    @Override
    public void renderOtherLayer(PoseStack matrices, SubmitNodeCollector consumers, int light, int overlay) {
        this.light.visible = true;

        var lightRenderType = this.on ? RenderTypes.eyes(TEXTURE_SOLID) : RenderTypes.entitySolid(TEXTURE_SOLID);
        consumers.submitCustomGeometry(matrices, lightRenderType, (framePose, buffer) ->
                this.light.render(matrices, buffer, light, overlay, 0xff000000 | this.lightColor));

        if (this.on) {
            this.glow.visible = true;
            var glowRenderType = RenderTypes.beaconBeam(TEXTURE_SOLID, true);

            consumers.submitCustomGeometry(matrices, glowRenderType, (framePose, buffer) ->
                    this.glow.render(matrices, buffer, light, overlay, 0x8e000000 | this.glowColor));
        }
    }

    @Override
    public void setDefaultState(float tickDelta) {
        super.setDefaultState(tickDelta);

        this.lightColor = LIGHT_OFF;
        this.glowColor = GLOW_OFF;
        this.on = false;

        this.light.visible = false;
        this.glow.visible = false;
    }

    @Override
    public void setRenderState(@Nullable FrontAttachment attachment, float groundHeight, float tickDelta) {
        super.setRenderState(attachment, groundHeight, tickDelta);

        this.light.visible = false;
        this.glow.visible = false;
        if (attachment instanceof AutopilotFrontAttachment att) {
            var state = att.getState();

            on = false;
            if (state.flashPeriod > 0) {
                int flashTime = att.getAnimationTimer() % (state.flashPeriod * 2);

                if ((flashTime / state.flashPeriod) == 0) {
                    on = true;
                    if (state.flashSubPeriod > 0) {
                        int subFlashTime = att.getAnimationTimer() % (state.flashSubPeriod * 2);

                        if ((subFlashTime / state.flashSubPeriod) == 1) {
                            on = false;
                        }
                    }
                }
            } else {
                on = true;
            }

            if (on) {
                this.lightColor = state.lightColor;
                this.glowColor = state.glowColor;
            } else {
                this.lightColor = LIGHT_OFF;
                this.glowColor = GLOW_OFF;
            }
        } else {
            this.on = false;
        }
    }
}
