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

import ga.matthewtgm.lib.other.ColourRGB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TitleHandler {

    private static String titleString;
    private static int titleTicks;
    private static ColourRGB titleColour;

    @SubscribeEvent
    protected void onGameOverlayRendered(RenderGameOverlayEvent.Post event) {
        renderTitles(event.resolution);
    }

    /**
     * Adapted from Skytils under GNU v3.0 license
     * @link https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
     * @author SkytilsMod
     */
    @SubscribeEvent
    protected void onClientTick(TickEvent.ClientTickEvent event) {
        if (titleTicks > 0) {
            titleTicks--;
        } else {
            titleTicks = 0;
            titleString = null;
        }
    }

    /**
     * Adapted from SkyblockAddons under MIT license
     * @link https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
     * @author BiscuitDevelopment
     */
    private void renderTitles(ScaledResolution scaledResolution) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null)
            return;

        int scaledWidth = scaledResolution.getScaledWidth();
        int scaledHeight = scaledResolution.getScaledHeight();
        if (titleString != null) {

            int stringWidth = mc.fontRendererObj.getStringWidth(titleString);

            float scale = 4; // Scale is normally 4, but if its larger than the screen, scale it down...
            if (stringWidth * scale > (scaledWidth * 0.9F)) {
                scale = (scaledWidth * 0.9F) / (float) stringWidth;
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate((float) (scaledWidth / 2), (float) (scaledHeight / 2), 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale);

            mc.fontRendererObj.drawString(titleString, (float) (-mc.fontRendererObj.getStringWidth(titleString) / 2), -20.0F, titleColour == null ? 0xFF0000 : titleColour.getRGBA(), true);

            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
            GlStateManager.color(1, 1, 1);
            mc.getTextureManager().bindTexture(Gui.icons);
        }
    }

    public static void setTitle(String titleString, int titleTicks) {
        TitleHandler.titleString = titleString;
        TitleHandler.titleTicks = titleTicks;
        TitleHandler.titleColour = null;
    }

    public static void setTitle(String titleString, int titleTicks, ColourRGB titleColour) {
        TitleHandler.titleString = titleString;
        TitleHandler.titleTicks = titleTicks;
        TitleHandler.titleColour = titleColour;
    }

}