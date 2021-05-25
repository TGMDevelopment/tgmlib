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

package xyz.matthewtgm.lib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;

import java.awt.*;

@SuppressWarnings({"unused"})
public class EnhancedFontRenderer {

    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @param dropShadow Whether or not to render a dropshadow under the text.
     * @author MatthewTGM
     */
    public static void drawText(String text, float x, float y, int colour, boolean dropShadow) {
        mc.fontRendererObj.drawString(text, x, y, colour, dropShadow);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @param dropShadow Whether or not to render a dropshadow under the text.
     * @author MatthewTGM
     */
    public static void drawText(String text, double x, double y, int colour, boolean dropShadow) {
        drawText(text, (float) x, (float) y, colour, dropShadow);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale to render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @param dropShadow Whether or not to render a dropshadow under the text.
     * @author MatthewTGM
     */
    public static void drawScaledText(String text, int scale, float x, float y, int colour, boolean dropShadow) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawText(text, x, y, colour, dropShadow);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale the render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @param dropShadow Whether or not to render a dropshadow under the text.
     * @author MatthewTGM
     */
    public static void drawScaledText(String text, int scale, double x, double y, int colour, boolean dropShadow) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawText(text, x, y, colour, dropShadow);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param dropShadow Whether or not to render a dropshadow under the text.
     * @author Wyvest
     */
    public static void drawChromaText(String text, float x, float y, boolean dropShadow) {
        for (char c : text.toCharArray()) {
            int col = ColourUtils.getChroma(x, y).getRGB();
            String charStr = String.valueOf(c);
            drawText(charStr, x, y, col, dropShadow);
            x += mc.fontRendererObj.getStringWidth(charStr);
        }
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param dropShadow Whether or not to render a dropshadow under the text.
     * @author Wyvest
     */
    public static void drawChromaText(String text, double x, double y, boolean dropShadow) {
        for (char c : text.toCharArray()) {
            int col = ColourUtils.getChroma(x, y).getRGB();
            String charStr = String.valueOf(c);
            drawText(charStr, x, y, col, dropShadow);
            x += mc.fontRendererObj.getStringWidth(charStr);
        }
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @param dropShadow Whether or not to render a dropshadow under the text.
     * @author Minecraft/Mojang
     */
    public static void drawCenteredText(String text, float x, float y, int colour, boolean dropShadow) {
        drawText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y, colour, dropShadow);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @param dropShadow Whether or not to render a dropshadow under the text.
     * @author Minecraft/Mojang
     */
    public static void drawCenteredText(String text, double x, double y, int colour, boolean dropShadow) {
        drawText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y, colour, dropShadow);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawText(String text, float x, float y, int colour) {
        drawText(text, x, y, colour, false);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawText(String text, double x, double y, int colour) {
        drawText(text, (float) x, (float) y, colour, false);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale to render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawScaledText(String text, int scale, float x, float y, int colour) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawText(text, x, y, colour);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale to render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawScaledText(String text, int scale, double x, double y, int colour) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawText(text, x, y, colour);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawCenteredChromaText(String text, float x, float y, boolean textShadow) {
        drawChromaText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y, textShadow);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawCenteredChromaText(String text, double x, double y, boolean textShadow) {
        drawChromaText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y, textShadow);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawCenteredChromaText(String text, float x, float y) {
        drawCenteredChromaText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y, false);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawCenteredChromaText(String text, double x, double y) {
        drawCenteredChromaText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y, false);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawCenteredText(String text, float x, float y, int colour) {
        drawCenteredText(text, x, y, colour, false);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawCenteredText(String text, double x, double y, int colour) {
        drawText(text, x, y, colour, false);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale to render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawCenteredScaledText(String text, int scale, float x, float y, int colour) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawCenteredText(text, x, y, colour);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale to render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawCenteredScaledText(String text, int scale, double x, double y, int colour) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawCenteredText(text, x, y, colour);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author Biscuit/MatthewTGM
     */
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

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawStyledText(String text, double x, double y, int colour) {
        drawStyledText(text, (float) x, (float) y, colour);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale to render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawScaledStyledText(String text, int scale, float x, float y, int colour) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawStyledText(text, x, y, colour);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale to render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawScaledStyledText(String text, int scale, double x, double y, int colour) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawStyledText(text, x, y, colour);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author Wyvest/MatthewTGM
     */
    public static void drawStyledChromaText(String text, float x, float y) {
        for (char c : text.toCharArray()) {
            int col = ColourUtils.getChroma(x, y).getRGB();
            String charStr = String.valueOf(c);
            drawStyledText(charStr, x, y, col);
            x += mc.fontRendererObj.getStringWidth(charStr);
        }
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author Wyvest/MatthewTGM
     */
    public static void drawStyledChromaText(String text, double x, double y) {
        for (char c : text.toCharArray()) {
            int col = ColourUtils.getChroma(x, y).getRGB();
            String charStr = String.valueOf(c);
            drawStyledText(charStr, x, y, col);
            x += mc.fontRendererObj.getStringWidth(charStr);
        }
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale to render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawStyledChromaScaledText(String text, int scale, float x, float y) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawStyledChromaText(text, x, y);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale to render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawStyledChromaScaledText(String text, int scale, double x, double y) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawStyledChromaText(text, x, y);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawCenteredStyledText(String text, float x, float y, int colour) {
        drawStyledText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y, colour);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawCenteredStyledText(String text, double x, double y, int colour) {
        drawStyledText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y, colour);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale to render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawCenteredStyledScaledText(String text, int scale, float x, float y, int colour) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawCenteredStyledText(text, x, y, colour);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale to render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @param colour The colour of the text.
     * @author MatthewTGM
     */
    public static void drawCenteredStyledScaledText(String text, int scale, double x, double y, int colour) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawCenteredStyledText(text, x, y, colour);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawCenteredStyledChromaText(String text, float x, float y) {
        drawStyledChromaText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawCenteredStyledChromaText(String text, double x, double y) {
        drawStyledChromaText(text, (x - mc.fontRendererObj.getStringWidth(text) / 2), y);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale to render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawCenteredStyledChromaScaledText(String text, int scale, float x, float y) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawCenteredStyledChromaText(text, x, y);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param scale The scale to render the text at.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawCenteredStyledChromaScaledText(String text, int scale, double x, double y) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);
        drawCenteredStyledChromaText(text, x, y);
        GlStateManager.popMatrix();
    }

}