package ga.matthewtgm.lib.gui.custom.elements.impl;

import ga.matthewtgm.lib.util.RenderHelper;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiImagedButtonElement extends GuiButtonElement {

    @Getter
    @Setter
    private ResourceLocation location;

    public GuiImagedButtonElement(int x, int y, int width, int height, ResourceLocation location, GuiButtonElementInteract interaction, GuiButtonElementColour colour) {
        super("", x, y, width, height, interaction, colour);
        this.location = location;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(location);
        Gui.drawModalRectWithCustomSizedTexture(getX(), getY(), 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Color colourToRender;
        boolean hovered = mouseX >= getX() && mouseY >= getY() && mouseX < getX() + getWidth() && mouseY < getY() + getHeight();
        if (hovered) colourToRender = getColour().getButtonColour();
        else colourToRender = getColour().getButtonColourHovered();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        RenderHelper.drawRoundedRect(getX(), getY(), getWidth(), getHeight(), 4, colourToRender);
    }

}