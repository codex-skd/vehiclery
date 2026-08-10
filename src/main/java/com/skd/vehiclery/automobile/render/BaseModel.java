package com.skd.vehiclery.automobile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.skd.vehiclery.Vehiclery;
import com.skd.vehiclery.automobile.model.ModelDefinition;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

// TODO(port): net.minecraft.client.model.Model<S>#renderToBuffer is now final (root-only render),
// and its constructor requires the ModelPart root directly instead of computing it after calling
// super(). The old renderToBuffer override -- which combined root render + renderExtra() in one
// call -- had to be split: renderToBuffer is now just the inherited root render, and callers must
// separately invoke renderExtra() afterwards (see AutomobileRenderer/VehicleryClient call sites).
// Similarly, MultiBufferSource -> SubmitNodeCollector for the "other layer" pass.
public class BaseModel extends Model implements RenderableModel {
    protected final Vector3fc translation;
    protected final Vector3fc rotation;
    protected final Vector3fc scale;

    public static final ModelPart PART_EMPTY = new ModelPart(List.of(), Map.of());
    public static final Identifier TEXTURE_SOLID = Vehiclery.rl("textures/solid.png");

    public BaseModel(EntityRendererProvider.Context ctx,
                     ModelDefinition.RenderMaterial material,
                     ModelLayerLocation layer,
                     Vector3fc translation, Vector3fc rotation, Vector3fc scale) {
        super(resolveRoot(ctx, layer), material.renderType);
        this.translation = translation;
        this.rotation = rotation;
        this.scale = scale;
    }

    private static ModelPart resolveRoot(EntityRendererProvider.Context ctx, ModelLayerLocation layer) {
        var head = ctx.bakeLayer(layer);
        var root = getChildSafe(head, "main");
        return root == PART_EMPTY ? head : root;
    }

    protected static ModelPart getChildSafe(ModelPart parent, String child) {
        try {
            return parent.getChild(child);
        } catch (NoSuchElementException ex) {
            return PART_EMPTY;
        }
    }

    protected void prepare(PoseStack matrices) {
        matrices.translate(translation.x(), translation.y(), translation.z());
        matrices.mulPose(Axis.ZP.rotationDegrees(rotation.z()));
        matrices.mulPose(Axis.XP.rotationDegrees(rotation.x()));
        matrices.mulPose(Axis.YP.rotationDegrees(rotation.y()));
        matrices.scale(scale.x(), scale.y(), scale.z());
    }

    public void setDefaultState(float tickDelta) {
    }

    @Override
    public final void renderModel(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        matrices.pushPose();
        this.prepare(matrices);
        this.root().render(matrices, vertices, light, overlay, color);
        renderExtra(matrices, vertices, light, overlay, color);
        matrices.popPose();
    }

    public final void doOtherLayerRender(PoseStack matrices, SubmitNodeCollector consumers, int light, int overlay) {
        matrices.pushPose();
        this.prepare(matrices);
        this.renderOtherLayer(matrices, consumers, light, overlay);
        matrices.popPose();
    }

    public void renderExtra(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
    }

    public void renderOtherLayer(PoseStack matrices, SubmitNodeCollector consumers, int light, int overlay) {
    }
}
