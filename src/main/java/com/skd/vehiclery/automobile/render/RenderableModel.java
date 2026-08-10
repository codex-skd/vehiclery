package com.skd.vehiclery.automobile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

// TODO(port): net.minecraft.client.model.Model<S>#renderToBuffer became final in this Minecraft
// version (it now only does the plain root-ModelPart render), so BaseModel/EmptyModel/ObjModel can
// no longer override it to add their own transform/extra-layer/custom-geometry behavior. This
// interface is the replacement hook callers use instead; renderType(Identifier) is still the
// inherited final Model method (declared here only so callers holding a RenderableModel reference
// can call it without also needing the concrete Model type).
public interface RenderableModel {
    void renderModel(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color);

    RenderType renderType(Identifier texture);
}
