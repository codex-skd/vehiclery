package com.skd.vehiclery.automobile.render.attachment.rear;

import com.skd.vehiclery.Vehiclery;
import com.skd.vehiclery.automobile.attachment.rear.ExtendableRearAttachment;
import com.skd.vehiclery.automobile.attachment.rear.RearAttachment;
import com.skd.vehiclery.automobile.model.ModelDefinition;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class PlowRearAttachmentModel extends RearAttachmentRenderModel {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(Vehiclery.rl("automobile/rear_attachment/plow"), "main");

    private final ModelPart assembly;
    private final ModelPart instrument;

    public PlowRearAttachmentModel(EntityRendererProvider.Context ctx,
                                   ModelDefinition.RenderMaterial material,
                                   ModelLayerLocation layer,
                                   Vector3f translation, Vector3f rotation, Vector3f scale) {
        super(ctx, material, layer, translation, rotation, scale);

        this.assembly = getChildSafe(this.root, "assembly");
        this.instrument = getChildSafe(assembly, "instrument");
    }

    @Override
    public void setDefaultState(float tickDelta) {
        super.setDefaultState(tickDelta);

        this.assembly.setRotation(0, 0, 0);
        this.instrument.setRotation(0, 0, 0);
    }

    @Override
    public void setRenderState(@Nullable RearAttachment attachment, float wheelAngle, float tickDelta) {
        super.setRenderState(attachment, wheelAngle, tickDelta);

        if (this.assembly != null && this.instrument != null && attachment instanceof ExtendableRearAttachment att) {
            float anim = att.extendAnimation(tickDelta);
            this.assembly.setRotation(6.5f * anim, 0, 0);
            this.instrument.setRotation(-3 * anim, 0, 0);
        }
    }
}
