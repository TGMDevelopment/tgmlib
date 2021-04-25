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

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

/**
 * Used along with {@link FontRenderer} to make things regarding font rendering just a little nicer.
 *
 * @author MatthewTGM
 */
public class FontRendererHelper {

    /**
     * Draws a string centered on the X and Y coordinates given.
     *
     * @param fontRenderer Minecraft font renderer instance.
     * @param text         The text to render.
     * @param x            X coordinate.
     * @param y            Y coordinate.
     * @param colour       The colour of the text.
     */
    public static void drawCenteredString(FontRenderer fontRenderer, String text, float x, float y, int colour) {
        fontRenderer.drawStringWithShadow(text, (x - fontRenderer.getStringWidth(text) / 2), y, colour);
    }

    /**
     * Draws a string centered on the X and Y coordinates given.
     *
     * @param fontRenderer Minecraft font renderer instance.
     * @param text         The text to render.
     * @param x            X coordinate.
     * @param y            Y coordinate.
     * @param colour       The colour of the text.
     */
    public static void drawCenteredString(FontRenderer fontRenderer, String text, double x, double y, int colour) {
        fontRenderer.drawStringWithShadow(text, (float) (x - fontRenderer.getStringWidth(text) / 2), (float) y, colour);
    }

    /**
     * @param fontRenderer Minecraft font renderer instance.
     * @param text         The text to render.
     * @param scale        The scale to render the text in.
     * @param x            X coordinate.
     * @param y            Y coordinate.
     * @param colour       The colour of the text.
     */
    public static void drawScaledString(FontRenderer fontRenderer, String text, int scale, float x, float y, int colour) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        fontRenderer.drawStringWithShadow(text, x, y, colour);
        GlStateManager.popMatrix();
    }

    /**
     * @param fontRenderer Minecraft font renderer instance.
     * @param text         The text to render.
     * @param scale        The scale to render the text in.
     * @param x            X coordinate.
     * @param y            Y coordinate.
     * @param colour       The colour of the text.
     */
    public static void drawScaledString(FontRenderer fontRenderer, String text, int scale, double x, double y, int colour) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        fontRenderer.drawStringWithShadow(text, (float) x, (float) y, colour);
        GlStateManager.popMatrix();
    }

    /**
     * @param fontRenderer Minecraft font renderer instance.
     * @param text         The text to render.
     * @param scale        The scale to render the text in.
     * @param x            X coordinate.
     * @param y            Y coordinate.
     * @param colour       The colour of the text.
     */
    public static void drawCenteredScaledString(FontRenderer fontRenderer, String text, int scale, float x, float y, int colour) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawCenteredString(fontRenderer, text, x, y, colour);
        GlStateManager.popMatrix();
    }

    /**
     * @param fontRenderer Minecraft font renderer instance.
     * @param text         The text to render.
     * @param scale        The scale to render the text in.
     * @param x            X coordinate.
     * @param y            Y coordinate.
     * @param colour       The colour of the text.
     */
    public static void drawCenteredScaledString(FontRenderer fontRenderer, String text, int scale, double x, double y, int colour) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawCenteredString(fontRenderer, text, x, y, colour);
        GlStateManager.popMatrix();
    }

}