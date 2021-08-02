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
import net.minecraft.client.renderer.GlStateManager;
import xyz.matthewtgm.requisite.mixins.game.MinecraftAccessor;
import xyz.matthewtgm.requisite.util.EasingHelper;
import xyz.matthewtgm.requisite.util.EnhancedFontRenderer;
import xyz.matthewtgm.requisite.util.MathHelper;
import xyz.matthewtgm.requisite.util.RenderHelper;

import java.awt.*;

/**
 * @author Basilicous
 */
public class GuiTransFadingButton extends GuiTransButton {

    protected int fade = 0;
    protected int size = 0;
    protected int frame = 0;

    public GuiTransFadingButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }
    public GuiTransFadingButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        float partialTicks = ((MinecraftAccessor) mc).getTimer().renderPartialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.color(1f, 1f, 1f, 1f);
        boolean hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);

        if (hovered) {
            fade = (int) MathHelper.clamp((float) (fade + EasingHelper.easeInQuint((int) (frame * partialTicks))), 25.0f, 255.0f);
            size = (int) ((int) MathHelper.clamp((float) (size + EasingHelper.easeInQuint((int) (frame * partialTicks))), 0.0f, 2.0f));
            frame += 1;
        } else {
            fade = (int) MathHelper.clamp((float) (fade - EasingHelper.easeInQuint((int) (frame * partialTicks))), 25.0f, 255.0f);
            size = (int) ((int) MathHelper.clamp((float) (size - EasingHelper.easeInQuint((int) (frame * partialTicks))), 0.0f, 2.0f));
            frame -= 1;
        }

        if (frame >= 50)
            frame = 50;
        else if (frame <= 0)
            frame = 0;

        RenderHelper.drawRectEnhanced(xPosition - size, yPosition - size, width + size * 2, height + size * 2, new Color(fade, fade, fade, 150).getRGB());

        EnhancedFontRenderer.drawCenteredText(displayString, xPosition + width / 2, yPosition + (height - 8) / 2, new Color(255, 255, 255).getRGB());
        GlStateManager.popMatrix();
    }

}