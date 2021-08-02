/*
 * Requisite - Minecraft library mod
 * Copyright (C) 2021 MatthewTGM
 *
 * Requisite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * Requisite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Requisite. If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.matthewtgm.requisite.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * A transparent-ish version of {@link net.minecraft.client.gui.GuiButton} with a little more "pizazz".
 */
public class GuiTransImageButton extends GuiTransButton {

    private ResourceLocation rlImage;

    public GuiTransImageButton(int buttonId, int x, int y, ResourceLocation image) {
        super(buttonId, x, y, 20, 20, "");
        this.rlImage = image;
    }

    public GuiTransImageButton(int buttonId, int x, int y, int width, int height, ResourceLocation image) {
        super(buttonId, x, y, width, height, "");
        this.rlImage = image;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(rlImage);
        Gui.drawModalRectWithCustomSizedTexture(this.xPosition, this.yPosition, 0, 0, this.width, this.height, this.width, this.height);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        this.mouseDragged(mc, mouseX, mouseY);
    }

    /**
     * Sets the xPosition of the button.
     *
     * @param x new xPosition.
     */
    public void setX(int x) {
        this.xPosition = x;
    }

    /**
     * Sets the yPosition of the button.
     *
     * @param y new yPosition.
     */
    public void setY(int y) {
        this.yPosition = y;
    }

    /**
     * Sets the height of the button.
     *
     * @param height new height.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Lets you set the image rendered on the button.
     *
     * @param image image rendered.
     */
    public void setImage(ResourceLocation image) {
        this.rlImage = image;
    }

}