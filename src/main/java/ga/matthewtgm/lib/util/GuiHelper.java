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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Used to enhance bits of code relating to the Minecraft {@link GuiScreen}.
 */
public class GuiHelper {

    private static GuiScreen toOpen;

    /**
     * Opens a {@link GuiScreen}. (will be most commonly used in commands.)
     *
     * @param screen the screen to open.
     */
    public static void open(GuiScreen screen) {
        toOpen = screen;
    }

    @SubscribeEvent
    protected void onTick(TickEvent event) {
        if (toOpen != null) {
            Minecraft.getMinecraft().displayGuiScreen(toOpen);
            toOpen = null;
        }
    }

}