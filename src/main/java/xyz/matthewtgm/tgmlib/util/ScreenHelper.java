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

package xyz.matthewtgm.tgmlib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ScreenHelper {

   private static ScaledResolution resolution;

    /**
     * @return The scaled screen width.
     * @author MatthewTGM
     */
   public static int getScaledWidth() {
       makeNullChecks();
       return resolution.getScaledWidth();
   }

    /**
     * @return The scaled screen height.
     * @author MatthewTGM
     */
   public static int getScaledHeight() {
       makeNullChecks();
       return resolution.getScaledHeight();
   }

    /**
     * @return The current scale factor.
     * @author MatthewTGM
     */
   public static int getScaleFactor() {
       makeNullChecks();
       return resolution.getScaleFactor();
   }

    /**
     * @author MatthewTGM
     */
   public static void updateOrtho() {
       updateOrtho(Minecraft.getMinecraft().gameSettings.guiScale);
   }

    /**
     * @param scaleFactor The new scale factor.
     * @author MatthewTGM
     */
    public static void updateOrtho(int scaleFactor) {
        updateOrtho(new CustomScaledResolution(Minecraft.getMinecraft(), scaleFactor));
    }

    /**
     * @param res The new scale.
     * @author MatthewTGM
     */
    public static void updateOrtho(CustomScaledResolution res) {
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0d, res.getScaledWidth_double(), res.getScaledHeight_double(), 0.0d, 1000.0d, 3000.0d);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
    }

    /**
     * @author MatthewTGM
     */
   private static void makeNullChecks() {
       if (resolution == null)
           resolution = new ScaledResolution(Minecraft.getMinecraft());
   }

   @SubscribeEvent
    protected void onGameOverlayRendered(RenderGameOverlayEvent event) {
       resolution = event.resolution;
   }

    public static ScaledResolution getResolution() {
       makeNullChecks();
        return resolution;
    }

}