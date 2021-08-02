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

package xyz.matthewtgm.requisite.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"unused"})
public final class EnhancedFontRenderer {

    private EnhancedFontRenderer() {}

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static FontRenderer getFontRenderer() {
        return mc.fontRendererObj;
    }

    public static int getFontHeight() {
        return getFontRenderer().FONT_HEIGHT;
    }

    /**
     * @param input The input to return the width of.
     * @return The width of the given input.
     * @author Mojang/Minecraft
     */
    public static int getWidth(CharSequence input) {
        return getFontRenderer().getStringWidth(input.toString());
    }

    /**
     * @param input The input to return the width of.
     * @return The width of the given input.
     * @author Mojang/Minecraft
     */
    public static int getWidth(char input) {
        return getFontRenderer().getCharWidth(input);
    }

    public static String trimToWidth(String text, int width, boolean reverse) {
        return getFontRenderer().trimStringToWidth(text, width, reverse);
    }

    public static String trimToWidth(String text, int width) {
        return getFontRenderer().trimStringToWidth(text, width, false);
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
    public static void drawText(String text, float x, float y, int colour, boolean dropShadow) {
        getFontRenderer().drawString(text, x, y, colour, dropShadow);
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
    public static void drawScaledText(String text, float scale, float x, float y, int colour, boolean dropShadow) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0f);
        drawText(text, x / scale, y / scale, colour, dropShadow);
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
    public static void drawScaledText(String text, float scale, double x, double y, int colour, boolean dropShadow) {
        drawScaledText(text, scale, (float) x, (float) y, colour, dropShadow);
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
            int col = ColourHelper.getChroma(x, y).getRGB();
            String charStr = String.valueOf(c);
            drawText(charStr, x, y, col, dropShadow);
            x += getWidth(charStr);
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
        drawChromaText(text, (float) x, (float) y, dropShadow);
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
        drawText(text, (x - getWidth(text) / 2), y, colour, dropShadow);
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
        drawCenteredText(text, (float) x, (float) y, colour, dropShadow);
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
    public static void drawScaledText(String text, float scale, float x, float y, int colour) {
        drawScaledText(text, scale, x, y, colour, false);
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
    public static void drawScaledText(String text, float scale, double x, double y, int colour) {
        drawScaledText(text, scale, (float) x, (float) y, colour);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawCenteredChromaText(String text, float x, float y, boolean textShadow) {
        drawChromaText(text, (x - getWidth(text) / 2), y, textShadow);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawCenteredChromaText(String text, double x, double y, boolean textShadow) {
        drawCenteredChromaText(text, (float) x, (float) y, textShadow);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawCenteredChromaText(String text, float x, float y) {
        drawCenteredChromaText(text, (x - getWidth(text) / 2), y, false);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawCenteredChromaText(String text, double x, double y) {
        drawCenteredChromaText(text, (float) x, (float) y);
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
        drawCenteredText(text, (float) x, (float) y, colour);
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
        drawCenteredText(text, x / scale, y / scale, colour);
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
        drawCenteredText(text, x / scale, y / scale, colour);
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
        int colAlpha = Math.max(ColourHelper.getAlpha(colour), 4);
        int colBlack = new Color(0, 0, 0, colAlpha / 255).getRGB();
        String stripped = StringUtils.stripControlCodes(text);
        drawText(stripped,1, 0, colBlack);
        drawText(stripped, -1, 0, colBlack);
        drawText(stripped, 0, 1, colBlack);
        drawText(stripped, 0, -1, colBlack);
        drawText(text, 0, 0, colour);
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
        drawStyledText(text, x / scale, y / scale, colour);
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
        drawStyledText(text, x / scale, y / scale, colour);
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
            int col = ColourHelper.getChroma(x, y).getRGB();
            String charStr = String.valueOf(c);
            drawStyledText(charStr, x, y, col);
            x += getWidth(charStr);
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
            int col = ColourHelper.getChroma(x, y).getRGB();
            String charStr = String.valueOf(c);
            drawStyledText(charStr, x, y, col);
            x += getWidth(charStr);
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
        drawStyledChromaText(text, x / scale, y / scale);
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
        drawStyledChromaText(text, x / scale, y / scale);
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
        drawStyledText(text, (x - getWidth(text) / 2), y, colour);
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
        drawStyledText(text, (x - getWidth(text) / 2), y, colour);
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
        drawCenteredStyledText(text, x / scale, y / scale, colour);
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
        drawCenteredStyledText(text, x / scale, y / scale, colour);
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
        drawStyledChromaText(text, (x - getWidth(text) / 2), y);
    }

    /**
     * Renders a string of text to the screen.
     * @param text The text to render.
     * @param x The x coordinate to render to.
     * @param y The y coordinate to render to.
     * @author MatthewTGM
     */
    public static void drawCenteredStyledChromaText(String text, double x, double y) {
        drawStyledChromaText(text, (x - getWidth(text) / 2), y);
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
        drawCenteredStyledChromaText(text, x / scale, y / scale);
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
        drawCenteredStyledChromaText(text, x / scale, y / scale);
        GlStateManager.popMatrix();
    }

    public static void drawThreeDimensionalText(String text, Vec3 pos, boolean drawBackground, Color color) {
        FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
        float f = 1.6f;
        float f2 = 0.016666668f * f;
        EntityPlayer player = mc.thePlayer;
        double x = (pos.xCoord - player.posX);
        double y = (pos.yCoord - player.posY);
        double z = (pos.zCoord - player.posZ);
        System.out.println("X: " + x + "\nY: " + y + "\nZ: " + z);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        int xMultiplier = 1;
        if (mc.gameSettings != null && mc.gameSettings.thirdPersonView == 2) xMultiplier = -1;
        GlStateManager.rotate(mc.getRenderManager().playerViewX * (float)xMultiplier, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-f2, -f2, f2);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        if (drawBackground) {
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            int i = 0;
            int j = fontrenderer.getStringWidth(text) / 2;
            GlStateManager.depthMask(true);
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos(-j - 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos(-j - 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos(j + 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos(j + 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            tessellator.draw();
        }
        GlStateManager.enableTexture2D();
        fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, 0, 553648127);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, 0, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }

    /**
     * Adapted from XanderLib under GPL 3.0 license
     * https://github.com/isXander/XanderLib/blob/main/LICENSE
     *
     * @author isXander
     */
    public static List<String> wrapTextLines(String text, int lineWidth, String split) {
        String wrapped = wrapText(text, lineWidth, split);
        if (wrapped.isEmpty())
            return new ArrayList<>();
        return Arrays.asList(wrapped.split("\n"));
    }
  
    /**
     * Adapted from XanderLib under GPL 3.0 license
     * https://github.com/isXander/XanderLib/blob/main/LICENSE
     *
     * @author isXander
     */
    public static String wrapText(String text, int lineWidth, String split) {
        String[] words = text.split("(" + split + "|\n)");
        int lineLength = 0;
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (i != words.length - 1)
                word += split;
            int wordLength = getWidth(word);
            if (lineLength + wordLength <= lineWidth) {
                output.append(word);
                lineLength += wordLength;
            } else if (wordLength <= lineWidth) {
                output.append("\n").append(word);
                lineLength = wordLength;
            } else
                output.append(wrapText(word, lineWidth, "")).append(split);
        }
        return output.toString();
    }

}