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
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

/**
 * Used to enhance bits of code relating to the Minecraft {@link GuiScreen}.
 */
public class GuiHelper {

    private static GuiScreen toOpen;

    public static void fixDisplayString(GuiButton button, String display) {
        if (!button.displayString.equals(display)) {
            button.displayString = display;
        }
    }

    /**
     * Opens a {@link GuiScreen}. (will be most commonly used in commands.)
     *
     * @param screen the screen to open.
     */
    public static void open(GuiScreen screen) {
        toOpen = screen;
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
            worldrenderer.pos(screen.width, screen.height, 0.0D).tex(((float)screen.width / 32.0F), (double)((float)screen.height / 32.0F + (float)0)).color(64, 64, 64, 255).endVertex();
            worldrenderer.pos(screen.width, 0.0D, 0.0D).tex(((float)screen.width / 32.0F), 0).color(64, 64, 64, 255).endVertex();
            worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0).color(64, 64, 64, 255).endVertex();
            tessellator.draw();
            return;
        }
        Gui.drawRect(0, 0, screen.width, screen.height, new Color(0, 0, 0, 60).getRGB());
    }

    @SubscribeEvent
    protected void onTick(TickEvent event) {
        if (toOpen != null) {
            Minecraft.getMinecraft().displayGuiScreen(toOpen);
            toOpen = null;
        }
    }

}