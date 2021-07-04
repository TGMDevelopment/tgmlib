/*
 * Copyright (C) MatthewTGM
 * This file is part of TGMLib <https://github.com/TGMDevelopment/TGMLib>.
 *
 * TGMLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TGMLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TGMLib. If not, see <http://www.gnu.org/licenses/>.
 */

package xyz.matthewtgm.tgmlib.gui;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import xyz.matthewtgm.tgmlib.util.EasingHelper;
import xyz.matthewtgm.tgmlib.util.MathHelper;
import xyz.matthewtgm.tgmlib.util.RenderHelper;

import java.awt.*;

public class GuiTransFadingImageButton extends GuiTransFadingButton {

    @Getter @Setter private ResourceLocation rlImage;

    public GuiTransFadingImageButton(int buttonId, int x, int y, ResourceLocation rlImage) {
        super(buttonId, x, y, "");
        this.rlImage = rlImage;
    }

    public GuiTransFadingImageButton(int buttonId, int x, int y, int widthIn, int heightIn, ResourceLocation rlImage) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.rlImage = rlImage;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        boolean hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
        float partialTicks = mc.timer.renderPartialTicks;

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
        GlStateManager.color(1f, 1f, 1f);
        RenderHelper.bindTexture(rlImage);
        Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, 0, 0, width, height, width, height);
    }

}