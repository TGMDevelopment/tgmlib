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
import org.lwjgl.input.Mouse;

public class MouseHelper {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static int getMouseX() {
        ScaledResolution res = new ScaledResolution(mc);
        return Mouse.getX() * res.getScaledWidth() / mc.displayWidth;
    }

    public static int getMouseY() {
        ScaledResolution res = new ScaledResolution(mc);
        return res.getScaledHeight() - Mouse.getY() * res.getScaledHeight() / mc.displayHeight - 1;
    }

    public static boolean isMouseDown() {
        return Mouse.getEventButtonState();
    }

}