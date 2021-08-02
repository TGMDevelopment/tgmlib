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
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.List;

/**
 * Used to enhance bits of code relating to the Minecraft {@link GuiScreen}.
 */
public final class GuiHelper {

    private static GuiScreen awaitingOpen;
    private static final GuiScreen GUI_NULL = new GuiNull();

    /**
     * @param button The button to fix.
     * @param display The display string re-iterated.
     * @author MatthewTGM
     */
    public static void fixDisplayString(GuiButton button, String display) {
        if (!button.displayString.equals(display))
            button.displayString = display;
    }

    /**
     * @param buttonList The button list to check from.
     * @author MatthewTGM
     */
    public static boolean isHoveringOverButton(List<GuiButton> buttonList) {
        int mouseX = MouseHelper.getMouseX();
        int mouseY = MouseHelper.getMouseY();
        return buttonList.stream().anyMatch(btn -> (mouseX >= btn.xPosition && mouseX <= btn.xPosition + btn.width) && (mouseY >= btn.yPosition && mouseY <= btn.yPosition + btn.height));
    }

    /**
     * @param buttonList The button list to check from.
     * @param id The button id to check for.
     * @author MatthewTGM
     */
    public static boolean isHoveringOverButton(List<GuiButton> buttonList, int id) {
        int mouseX = MouseHelper.getMouseX();
        int mouseY = MouseHelper.getMouseY();
        return buttonList.stream().anyMatch(btn -> (mouseX >= btn.xPosition && mouseX <= btn.xPosition + btn.width) && (mouseY >= btn.yPosition && mouseY <= btn.yPosition + btn.height) && btn.id == id);
    }

    /**
     * @param buttonList The button list to check from.
     * @param button The button to check for.
     * @author MatthewTGM
     */
    public static boolean isHoveringOverButton(List<GuiButton> buttonList, GuiButton button) {
        int mouseX = MouseHelper.getMouseX();
        int mouseY = MouseHelper.getMouseY();
        return buttonList.stream().anyMatch(btn -> (mouseX >= btn.xPosition && mouseX <= btn.xPosition + btn.width) && (mouseY >= btn.yPosition && mouseY <= btn.yPosition + btn.height) && btn == button);
    }

    /**
     * @param textLines The lines of text to draw.
     * @param x The x position to draw at.
     * @param y The y position to draw at.
     * @author MatthewTGM
     */
    public static void drawTooltip(List<String> textLines, int x, int y) {
        net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(textLines, x, y, ScreenHelper.getScaledWidth(), ScreenHelper.getScaledHeight(), -1, Minecraft.getMinecraft().fontRendererObj);
    }

    /**
     * @param screen The screen to force.
     * @param scale The new scale.
     * @author MatthewTGM
     */
    public static void forceGuiScale(GuiScreen screen, int scale) {
        CustomScaledResolution res = new CustomScaledResolution(Minecraft.getMinecraft(), scale);
        ScreenHelper.updateOrtho(res);
        int scaledWidth = res.getScaledWidth();
        int scaledHeight = res.getScaledHeight();
        if (screen.width != scaledWidth)
            screen.width = scaledWidth;
        if (screen.height != scaledHeight)
            screen.height = scaledHeight;
    }

    /**
     *
     * @param screen The screen to force.
     * @param scale The new scale.
     * @author MatthewTGM
     */
    public static void forceGuiScale(GuiScreen screen, GuiScale scale) {
        forceGuiScale(screen, scale.ordinal());
    }

    /**
     * Opens a {@link GuiScreen}. (will be most commonly used in commands.)
     *
     * @param screen the screen to open.
     * @author MatthewTGM
     */
    public static void open(GuiScreen screen) {
        awaitingOpen = screen;
    }

    public static void drawBackground(GuiScreen screen, int alpha) {
        if (Minecraft.getMinecraft().theWorld == null) {
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.optionsBackground);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            float f = 32.0F;
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos(0.0D, screen.height, 0.0D).tex(0.0D, ((float)screen.height / 32.0F + (float)0)).color(64, 64, 64, 255).endVertex();
            worldrenderer.pos(screen.width, screen.height, 0.0D).tex(((float)screen.width / 32.0F), ((float)screen.height / 32.0F + (float)0)).color(64, 64, 64, 255).endVertex();
            worldrenderer.pos(screen.width, 0.0D, 0.0D).tex(((float)screen.width / 32.0F), 0).color(64, 64, 64, 255).endVertex();
            worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0).color(64, 64, 64, 255).endVertex();
            tessellator.draw();
        } else Gui.drawRect(0, 0, screen.width, screen.height, new Color(0, 0, 0, alpha).getRGB());
    }

    @SubscribeEvent
    protected void onTick(TickEvent event) {
        if (awaitingOpen != GUI_NULL) {
            Minecraft.getMinecraft().displayGuiScreen(awaitingOpen);
            awaitingOpen = GUI_NULL;
        }
    }

    private static class GuiNull extends GuiScreen {}

    public enum GuiScale {
        AUTO,
        SMALL,
        NORMAL,
        LARGE
    }

}