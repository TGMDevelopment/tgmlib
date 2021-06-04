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

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ScreenHelper {

   @Getter
   private static ScaledResolution resolution;

   public static int getScaledWidth() {
       makeNullChecks();
       return resolution.getScaledWidth();
   }

   public static int getScaledHeight() {
       makeNullChecks();
       return resolution.getScaledHeight();
   }

   public static int getScaleFactor() {
       makeNullChecks();
       return resolution.getScaleFactor();
   }

   private static void makeNullChecks() {
       if (resolution == null) resolution = new ScaledResolution(Minecraft.getMinecraft());
   }

   @SubscribeEvent
    protected void onGameOverlayRendered(RenderGameOverlayEvent event) {
       if (resolution == null)
           resolution = event.resolution;
   }

}