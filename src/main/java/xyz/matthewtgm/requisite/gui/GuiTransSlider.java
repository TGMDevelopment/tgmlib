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
import net.minecraftforge.fml.client.config.GuiSlider;

import java.awt.*;

/**
 * A transparent-ish version of {@link GuiSlider}
 */
public class GuiTransSlider extends GuiSlider {

    private Color buttonColour = new Color(0, 0, 0, 55);

    public GuiTransSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr) {
        super(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr);
    }

    public GuiTransSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, ISlider par) {
        super(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, par);
    }

    public GuiTransSlider(int id, int xPos, int yPos, String displayStr, double minVal, double maxVal, double currentVal, ISlider par) {
        super(id, xPos, yPos, displayStr, minVal, maxVal, currentVal, par);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            Minecraft.getMinecraft().renderEngine.bindTexture(buttonTextures);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.buttonColour.getRGB());
            this.mouseDragged(mc, mouseX, mouseY);
            int color = 14737632;
            if (packedFGColour != 0) {
                color = packedFGColour;
            } else if (!this.enabled) {
                color = 10526880;
            } else if (this.hovered) {
                color = 16777120;
            }
            String buttonText = this.displayString;
            int strWidth = mc.fontRendererObj.getStringWidth(buttonText);
            int ellipsisWidth = mc.fontRendererObj.getStringWidth("...");
            if (strWidth > width - 6 && strWidth > ellipsisWidth)
                buttonText = mc.fontRendererObj.trimStringToWidth(buttonText, width - 6 - ellipsisWidth).trim() + "...";
            this.drawCenteredString(mc.fontRendererObj, buttonText, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, color);
        }
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
     * @return the colour of the button.
     */
    public Color getButtonColour() {
        return buttonColour;
    }

    /**
     * Sets the colour of the button.
     * @param buttonColour new button colour.
     */
    public void setButtonColour(Color buttonColour) {
        this.buttonColour = buttonColour;
    }

}