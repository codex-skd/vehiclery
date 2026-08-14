package com.skd.vehiclery.block.entity.render;

import com.skd.vehiclery.block.entity.AutomobileAssemblerBlockEntity;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

// TODO(port): same shortcut as AutomobileRenderState -- keeps a live BlockEntity reference instead
// of extracting individual fields, defeating the thread-safety intent of the state-extraction split.
public class AutomobileAssemblerRenderState extends BlockEntityRenderState {
    public AutomobileAssemblerBlockEntity entity;
    public float tickDelta;
}
