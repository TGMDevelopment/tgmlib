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

package ga.matthewtgm.lib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;

import java.awt.*;

public class EnhancedFontRenderer {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void drawText(String text, float x, float y, int colour, boolean dropShadow) {
        mc.fontRendererObj.drawString(text, x, y, colour, dropShadow);
    }

    public static void drawText(String text, double x, double y, int colour, boolean dropShadow) {
        drawText(text, (float) x, (float) y, colour, dropShadow);
    }

    public static void drawCenteredText(String text, float x, float y, int colour, boolean dropShadow) {
        drawText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y / 2, colour, dropShadow);
    }

    public static void drawCenteredText(String text, double x, double y, int colour, boolean dropShadow) {
        drawText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y / 2, colour, dropShadow);
    }

    public static void drawText(String text, float x, float y, int colour) {
        drawText(text, x, y, colour, false);
    }

    public static void drawText(String text, double x, double y, int colour) {
        drawText(text, (float) x, (float) y, colour, false);
    }

    public static void drawCenteredText(String text, float x, float y, int colour) {
        drawCenteredText(text, x, y, colour, false);
    }

    public static void drawCenteredText(String text, double x, double y, int colour) {
        drawText(text, x, y, colour, false);
    }

    public static void drawStyledText(String text, float x, float y, int colour) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        int colAlpha = Math.max(ColourUtils.getAlpha(colour), 4);
        int colBlack = new Color(0, 0, 0, colAlpha / 255).getRGB();
        String stripped = StringUtils.stripControlCodes(text);
        mc.fontRendererObj.drawString(stripped,1, 0, colBlack);
        mc.fontRendererObj.drawString(stripped, -1, 0, colBlack);
        mc.fontRendererObj.drawString(stripped, 0, 1, colBlack);
        mc.fontRendererObj.drawString(stripped, 0, -1, colBlack);
        mc.fontRendererObj.drawString(text, 0, 0, colour);
        GlStateManager.popMatrix();
    }

    public static void drawStyledText(String text, double x, double y, int colour) {
        drawStyledText(text, (float) x, (float) y, colour);
    }

    public static void drawCenteredStyledText(String text, float x, float y, int colour) {
        drawStyledText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y / 2, colour);
    }

    public static void drawCenteredStyledText(String text, double x, double y, int colour) {
        drawStyledText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y / 2, colour);
    }

}