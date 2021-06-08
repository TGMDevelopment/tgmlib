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
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Used to make Minecraft rendering easier.
 */
public class RenderHelper {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void bindTexture(ResourceLocation location) {
        mc.getTextureManager().bindTexture(location);
    }

    public static void bindTexture(String textureName, DynamicTexture texture) {
        bindTexture(mc.getTextureManager().getDynamicTextureLocation(textureName, texture));
    }

    /**
     * Draws a rounded rectangle using arcs.
     *
     * @param x            x position.
     * @param y            y position.
     * @param width        rectangle width.
     * @param height       rectangle height.
     * @param cornerRadius the radius of the rounded corners.
     * @param color        the colour of the rectangle.
     */
    public static void drawRoundedRect(int x, int y, int width, int height, int cornerRadius, Color color) {
        Gui.drawRect(x, y + cornerRadius, x + cornerRadius, y + height - cornerRadius, color.getRGB());
        Gui.drawRect(x + cornerRadius, y, x + width - cornerRadius, y + height, color.getRGB());
        Gui.drawRect(x + width - cornerRadius, y + cornerRadius, x + width, y + height - cornerRadius, color.getRGB());

        drawArc(x + cornerRadius, y + cornerRadius, cornerRadius, 0, 90, color);
        drawArc(x + width - cornerRadius, y + cornerRadius, cornerRadius, 270, 360, color);
        drawArc(x + width - cornerRadius, y + height - cornerRadius, cornerRadius, 180, 270, color);
        drawArc(x + cornerRadius, y + height - cornerRadius, cornerRadius, 90, 180, color);
    }

    /**
     * Draws a hollowed out rounded rectangle using hollow arcs.
     *
     * @param x         x position.
     * @param y         y position.
     * @param width     rectangle width.
     * @param height    rectangle height.
     * @param thickness outline thickness.
     * @param color     outline colour.
     */
    public static void drawHollowRoundedRect(int x, int y, int width, int height, double thickness, Color color) {
        double radius = 4;
        drawHollowArc(x, y, radius, -180, -90, thickness, color);
        drawVerticalLine(x, y + 3, y + height - 4, color.getRGB());
        drawHorizontalLine(x + 3, x + width - 5, y, color.getRGB());
        drawHollowArc(x + width - 8, y, radius, -270, -180, thickness, color);
        drawVerticalLine(x + width - 1, y + 3, y + height - 4, color.getRGB());
        drawHorizontalLine(x + 4, x + width - 5, y + height - 1, color.getRGB());
        drawHollowArc(x + width - 8, y + height - 8, radius, 0, 90, thickness, color);
        drawHollowArc(x, y + height - 8, radius, -90, -0, thickness, color);
    }

    private static void drawArc(int x, int y, int radius, int startAngle, int endAngle, Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, (float) color.getAlpha() / 255);
        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        worldRenderer.begin(6, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();
        for (int i = (int) (startAngle / 360.0 * 100); i <= (int) (endAngle / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
        }
        Tessellator.getInstance().draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    private static void drawHollowArc(double x, double y, double radius, int startAngle, int endAngle, double thickness, Color color) {
        radius -= thickness / 2;
        x += thickness / 2;
        y += thickness / 2;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth((float) thickness);
        GL11.glColor4d(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (double i = startAngle; i <= endAngle; i += 1) GL11.glVertex2d(x + radius + Math.sin(Math.toRadians(i)) * radius, y + radius + Math.cos(Math.toRadians(i)) * radius);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(1);
    }

    /**
     * Draws a full rectangle.
     *
     * @param left   left side of the rectangle.
     * @param top    top of the rectangle.
     * @param right  right side of the rectangle.
     * @param bottom bottom of the rectangle.
     * @param color  colour of the rectangle.
     */
    public static void drawRect(int left, int top, int right, int bottom, int color) {
        if (left < right) {
            int i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            int j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    /**
     * Draws a hollowed out rectangle.
     *
     * @param x      x position.
     * @param y      y position.
     * @param width  rectangle width.
     * @param height rectangle height.
     * @param color  rectangle colour.
     */
    public static void drawHollowRect(int x, int y, int width, int height, int color) {
        drawHorizontalLine(x, x + width, y, color);
        drawHorizontalLine(x, x + width, y + height, color);
        drawVerticalLine(x, y + height, y, color);
        drawVerticalLine(x + width, y + height, y, color);
    }

    protected static void drawHorizontalLine(int startX, int endX, int y, int color) {
        if (endX < startX) {
            int i = startX;
            startX = endX;
            endX = i;
        }
        drawRect(startX, y, endX + 1, y + 1, color);
    }

    protected static void drawVerticalLine(int x, int startY, int endY, int color) {
        if (endY < startY) {
            int i = startY;
            startY = endY;
            endY = i;
        }
        drawRect(x, startY + 1, x + 1, endY, color);
    }

}