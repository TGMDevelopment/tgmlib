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

import java.awt.*;

/**
 * Used to create and get colours easily.
 */
public final class ColourHelper {

    private ColourHelper() {}

    /**
     * @return A changing colour based on the users computer time. Simulates a "chroma" colour.
     * @author MatthewTGM
     */
    public static int timeBasedChroma() {
        long l = System.currentTimeMillis();
        return Color.HSBtoRGB(l % 2000L / 2000.0f, 0.8f, 0.8f);
    }

    /**
     * @author Unknown
     */
    public static Color getChroma(double x, double y) {
        float v = 2000.0f;
        return new Color(Color.HSBtoRGB((float)((System.currentTimeMillis() - x * 10.0 * 1.0 - y * 10.0 * 1.0) % v) / v, 0.8f, 0.8f));
    }

    /**
     * @author Unknown
     */
    public static int getAlpha(int colour) {
        return (colour >> 24 & 255);
    }

}