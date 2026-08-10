package com.skd.vehiclery.block.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.skd.vehiclery.automobile.render.AutomobileRenderer;
import com.skd.vehiclery.block.entity.AutomobileAssemblerBlockEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

// TODO(port): BlockEntityRenderer now uses the same createRenderState()/extractRenderState()/submit()
// state-extraction split as EntityRenderer, and MultiBufferSource -> SubmitNodeCollector. Text
// rendering moved from Font#drawInBatch(..., MultiBufferSource, ...) to
// SubmitNodeCollector#submitText(...).
public class AutomobileAssemblerBlockEntityRenderer implements BlockEntityRenderer<AutomobileAssemblerBlockEntity, AutomobileAssemblerRenderState> {
    private final Font textRenderer;

    public AutomobileAssemblerBlockEntityRenderer(BlockEntityRendererProvider.Context blockEntityCtx) {
        this.textRenderer = blockEntityCtx.font();
    }

    @Override
    public AutomobileAssemblerRenderState createRenderState() {
        return new AutomobileAssemblerRenderState();
    }

    @Override
    public void extractRenderState(AutomobileAssemblerBlockEntity blockEntity, AutomobileAssemblerRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.entity = blockEntity;
        state.tickDelta = partialTicks;
    }

    @Override
    public void submit(AutomobileAssemblerRenderState state, PoseStack matrices, SubmitNodeCollector collector, CameraRenderState camera) {
        var entity = state.entity;
        int light = state.lightCoords;

        matrices.pushPose();
        matrices.translate(0.5, 0.75 - (entity.getWheels().model().radius() / 16), 0.5);
        AutomobileRenderer.render(matrices, collector, light, 0, state.tickDelta, entity);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0.5, 0, 0.5);
        matrices.mulPose(Axis.YP.rotationDegrees(-entity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()));
        matrices.translate(0, 0.372, 0.501);
        matrices.scale(0.008f, -0.008f, 0.008f);

        for (var text : entity.label) {
            matrices.pushPose();
            matrices.translate(-0.5 * textRenderer.width(text), 0, 0);
            collector.submitText(matrices, 0f, 0f, text.getVisualOrderText(), true, Font.DisplayMode.POLYGON_OFFSET, light, 0xFFFFFF, 0, 0);
            matrices.popPose();
            matrices.translate(0, 12, 0);
        }

        matrices.popPose();
    }
}
