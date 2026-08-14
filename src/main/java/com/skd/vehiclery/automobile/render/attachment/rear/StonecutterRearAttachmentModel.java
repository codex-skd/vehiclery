package com.skd.vehiclery.automobile.render.attachment.rear;

import com.skd.vehiclery.Vehiclery;
import com.skd.vehiclery.automobile.attachment.rear.RearAttachment;
import com.skd.vehiclery.automobile.model.ModelDefinition;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class StonecutterRearAttachmentModel extends RearAttachmentRenderModel {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Vehiclery.rl("automobile/rear_attachment/stonecutter"), "main");

    private final ModelPart blade;

    public StonecutterRearAttachmentModel(EntityRendererProvider.Context ctx,
                                          ModelDefinition.RenderMaterial material,
                                          ModelLayerLocation layer,
                                          Vector3fc translation, Vector3fc rotation, Vector3fc scale) {
        super(ctx, material, layer, translation, rotation, scale);
        this.blade = getChildSafe(this.root, "blade");
    }

    @Override
    public void setDefaultState(float tickDelta) {
        super.setDefaultState(tickDelta);

        this.blade.setRotation(0, 0, 0);
    }

    @Override
    public void setRenderState(@Nullable RearAttachment attachment, float wheelAngle, float tickDelta) {
        super.setRenderState(attachment, wheelAngle, tickDelta);

        if (this.blade != null) {
            this.blade.setRotation(wheelAngle * 0.45f, 0, 0);
        }
    }
}
