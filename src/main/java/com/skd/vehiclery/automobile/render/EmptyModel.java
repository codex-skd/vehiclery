package com.skd.vehiclery.automobile.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderTypes;

import java.util.List;
import java.util.Map;

public class EmptyModel extends Model<Object> implements RenderableModel {
    private static final ModelPart EMPTY_ROOT = new ModelPart(List.of(), Map.of());

    public EmptyModel() {
        super(EMPTY_ROOT, RenderTypes::entitySolid);
    }

    @Override
    public void renderModel(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
    }
}
