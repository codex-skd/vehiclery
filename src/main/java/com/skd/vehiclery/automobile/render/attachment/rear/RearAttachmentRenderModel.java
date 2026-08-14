package com.skd.vehiclery.automobile.render.attachment.rear;

import com.skd.vehiclery.automobile.attachment.rear.RearAttachment;
import com.skd.vehiclery.automobile.model.ModelDefinition;
import com.skd.vehiclery.automobile.render.BaseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class RearAttachmentRenderModel extends BaseModel {
    private final @Nullable ModelPart wheels;

    public RearAttachmentRenderModel(EntityRendererProvider.Context ctx,
                                     ModelDefinition.RenderMaterial material,
                                     ModelLayerLocation layer,
                                     Vector3fc translation, Vector3fc rotation, Vector3fc scale) {
        super(ctx, material, layer, translation, rotation, scale);

        this.wheels = getChildSafe(this.root, "wheels");
    }

    @Override
    public void setDefaultState(float tickDelta) {
        super.setDefaultState(tickDelta);

        resetModel();
    }

    public void setRenderState(@Nullable RearAttachment attachment, float wheelAngle, float tickDelta) {
        if (this.wheels != null) {
            this.wheels.setRotation(wheelAngle, 0, 0);
        }
    }

    public void resetModel() {
        this.setRenderState(null, 0, 0);
    }
}
