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

import xyz.matthewtgm.requisite.data.ColourRGB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public final class GlHelper {

    private GlHelper() {}

    /**
     * @param x      The x coordinate to start the box.
     * @param y      The y coordinate to start the box.
     * @param width  The width of the box.
     * @param height The height of the box.
     * @author MatthewTGM
     */
    public static void startScissorBox(int x, int y, int width, int height) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        totalScissor(x, y, width, height);
    }

    /**
     * @param x      The x coordinate to start the box.
     * @param y      The y coordinate to start the box.
     * @param width  The width of the box.
     * @param height The height of the box.
     * @author MatthewTGM
     */
    public static void startScissorBox(float x, float y, float width, float height) {
        startScissorBox((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * @author MatthewTGM
     */
    public static void endScissorBox() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    /**
     * Adapted from XanderLib under GPL 3.0 license
     * https://github.com/isXander/XanderLib/blob/main/LICENSE
     *
     * @author isXander
     */
    public static void totalScissor(double xPosition, double yPosition, double width, double height) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glScissor((int) ((xPosition * Minecraft.getMinecraft().displayWidth) / scaledResolution.getScaledWidth()), (int) (((scaledResolution.getScaledHeight() - (yPosition + height)) * Minecraft.getMinecraft().displayHeight) / scaledResolution.getScaledHeight()), (int) (width * Minecraft.getMinecraft().displayWidth / scaledResolution.getScaledWidth()), (int) (height * Minecraft.getMinecraft().displayHeight / scaledResolution.getScaledHeight()));
    }

    /**
     * Adapted from XanderLib under GPL 3.0 license
     * https://github.com/isXander/XanderLib/blob/main/LICENSE
     *
     * @author isXander
     */
    public static void drawRectangle(float xPosition, float yPosition, float width, float height, ColourRGB colour) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
        GlStateManager.color(
                colour.getR() / 255.0F,
                colour.getG() / 255.0F,
                colour.getB() / 255.0F,
                colour.getA() / 255.0F);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(xPosition, yPosition + height, 0.0D).endVertex();
        worldrenderer.pos(xPosition + width, yPosition + height, 0.0D).endVertex();
        worldrenderer.pos(xPosition + width, yPosition, 0.0D).endVertex();
        worldrenderer.pos(xPosition, yPosition, 0.0D).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.bindTexture(0);
        GlStateManager.color(1f, 1f, 1f, 1f);
    }

}