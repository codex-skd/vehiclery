package com.skd.vehiclery.neoforge.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.skd.vehiclery.util.HexCons;
import net.minecraft.client.renderer.SubmitNodeCollector;
// TODO(port): this registry is currently unused since BlockEntityWithoutLevelRenderer (its
// integration point) no longer exists -- see BlockEntityWithoutLevelRendererMixin's TODO. Kept
// intact (and updated to the new SubmitNodeCollector type) so it's ready to wire into the
// SpecialModelRenderer<T> replacement once that's implemented.
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class BEWLRs {
    private static final Map<Item, HexCons<ItemStack, ItemDisplayContext, PoseStack, SubmitNodeCollector, Integer, Integer>> BEWLRS = new HashMap<>();

    public static void add(Item item, HexCons<ItemStack, ItemDisplayContext, PoseStack, SubmitNodeCollector, Integer, Integer> renderer) {
        BEWLRS.put(item, renderer);
    }

    /**
     * @return {@code true} to cancel the rest of the BEWLR item rendering
     */
    public static boolean tryRender(ItemStack stack, ItemDisplayContext transform, PoseStack pose, SubmitNodeCollector buffers, int light, int overlay) {
        var item = stack.getItem();
        if (BEWLRS.containsKey(item)) {
            BEWLRS.get(item).accept(stack, transform, pose, buffers, light, overlay);
            return true;
        }

        return false;
    }
}
