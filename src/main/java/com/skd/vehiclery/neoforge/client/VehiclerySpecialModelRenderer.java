package com.skd.vehiclery.neoforge.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class VehiclerySpecialModelRenderer implements SpecialModelRenderer.Unbaked<ItemStack> {
    public static final MapCodec<VehiclerySpecialModelRenderer> CODEC = MapCodec.unit(VehiclerySpecialModelRenderer::new);

    @Override
    public SpecialModelRenderer<ItemStack> bake(SpecialModelRenderer.BakingContext context) {
        return new SpecialModelRenderer<ItemStack>() {
            @Override
            public void submit(ItemStack argument, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
                if (argument != null) {
                    BEWLRs.tryRender(argument, ItemDisplayContext.NONE, poseStack, submitNodeCollector, lightCoords, overlayCoords);
                }
            }

            @Override
            public void getExtents(Consumer<org.joml.Vector3fc> output) {
                for (float x = -1f; x <= 2f; x += 3f) {
                    for (float y = -1f; y <= 2f; y += 3f) {
                        for (float z = -1f; z <= 2f; z += 3f) {
                            output.accept(new org.joml.Vector3f(x, y, z));
                        }
                    }
                }
            }

            @Override
            public ItemStack extractArgument(ItemStack stack) {
                return stack;
            }
        };
    }

    @Override
    public MapCodec<? extends SpecialModelRenderer.Unbaked<ItemStack>> type() {
        return CODEC;
    }
}
