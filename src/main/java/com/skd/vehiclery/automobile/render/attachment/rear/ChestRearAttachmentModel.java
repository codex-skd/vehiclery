package com.skd.vehiclery.automobile.render.attachment.rear;

import com.skd.vehiclery.Vehiclery;
import com.skd.vehiclery.automobile.attachment.rear.BaseChestRearAttachment;
import com.skd.vehiclery.automobile.attachment.rear.RearAttachment;
import com.skd.vehiclery.automobile.model.ModelDefinition;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class ChestRearAttachmentModel extends RearAttachmentRenderModel {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Vehiclery.rl("automobile/rear_attachment/chest"), "main");

    private final ModelPart lid;

    public ChestRearAttachmentModel(EntityRendererProvider.Context ctx,
                                    ModelDefinition.RenderMaterial material,
                                    ModelLayerLocation layer,
                                    Vector3fc translation, Vector3fc rotation, Vector3fc scale) {
        super(ctx, material, layer, translation, rotation, scale);
        this.lid = getChildSafe(this.root, "lid");
    }

    @Override
    public void setRenderState(@Nullable RearAttachment attachment, float wheelAngle, float tickDelta) {
        super.setRenderState(attachment, wheelAngle, tickDelta);

        if (attachment instanceof BaseChestRearAttachment chest) {
            float angle = 1 - chest.lidAnimator.getOpenness(tickDelta);
            angle = 1 - (angle * angle * angle);
            this.lid.setRotation((float) (angle * Math.PI * 0.5), 0, 0);
        }
    }

    @Override
    public void resetModel() {
        super.resetModel();
        this.lid.setRotation(0, 0, 0);
    }
}
