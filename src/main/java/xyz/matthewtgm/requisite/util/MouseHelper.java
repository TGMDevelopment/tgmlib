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
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public final class MouseHelper {

    private static final Minecraft mc = Minecraft.getMinecraft();

    private MouseHelper() {}

    /**
     * @return The current mouse x postion.
     * @author MatthewTGM
     */
    public static int getMouseX() {
        ScaledResolution res = new ScaledResolution(mc);
        return Mouse.getX() * res.getScaledWidth() / mc.displayWidth;
    }

    /**
     * @return The current mouse y position.
     * @author MatthewTGM
     */
    public static int getMouseY() {
        ScaledResolution res = new ScaledResolution(mc);
        return res.getScaledHeight() - Mouse.getY() * res.getScaledHeight() / mc.displayHeight - 1;
    }

    /**
     * @return Whether or not any mouse button is currently down.
     * @author MatthewTGM
     */
    public static boolean isMouseDown() {
        return Mouse.getEventButtonState();
    }

}