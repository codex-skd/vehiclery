package com.skd.vehiclery.screen;

import com.skd.vehiclery.Vehiclery;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

// TODO(port): render(GuiGraphics, ...) -> extractRenderState(GuiGraphicsExtractor, ...) and
// renderBg(...) -> extractBackground(...); extractTooltip is already called internally by the base
// extractRenderState flow, so the old explicit renderTooltip() call after super.render() is no
// longer needed. blit() now takes a RenderPipeline as its first argument.
public class SingleSlotScreen extends AbstractContainerScreen<SingleSlotScreenHandler> implements MenuAccess<SingleSlotScreenHandler> {
    private static final Identifier TEXTURE = Vehiclery.rl("textures/gui/container/single_slot.png");

    public SingleSlotScreen(SingleSlotScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title, 176, 140);

        this.inventoryLabelY = 47;
        this.titleLabelX = 60;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractBackground(graphics, mouseX, mouseY, delta);
        // TODO(port): RenderSystem.setShaderColor(...) no longer exists; the reference blit()
        // pattern in sibling mods doesn't reset shader color before blitting, so dropped it.
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
    }
}
