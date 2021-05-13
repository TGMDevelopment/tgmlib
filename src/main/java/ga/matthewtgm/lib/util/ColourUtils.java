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

import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

/**
 * Used to create and get colours easily.
 */
public class ColourUtils {

    /**
     * @return A changing colour based on the users computer time. Simulates a "chroma" colour.
     * @author MatthewTGM
     */
    public static int timeBasedChroma() {
        long l = System.currentTimeMillis();
        return Color.HSBtoRGB(l % 2000L / 2000.0f, 0.8f, 0.8f);
    }

    /**
     * @param fontRenderer The game's font renderer.
     * @param text The text to render.
     * @param x
     * @param y
     * @param shadow Whether or not to render a text shadow along-side the text.
     * @author Wyvest
     */
    public static void drawChromaString(FontRenderer fontRenderer, String text, float x, float y, boolean shadow) {
        for (char c : text.toCharArray()) {
            int i = getChroma(x, y).getRGB();
            String tmp = String.valueOf(c);
            fontRenderer.drawString(tmp, x, y, i, shadow);
            x += fontRenderer.getStringWidth(tmp);
        }
    }

    /**
     * @author Wyvest
     */
    public static Color getChroma(double x, double y) {
        float v = 2000.0f;
        return new Color(Color.HSBtoRGB((float)((System.currentTimeMillis() - x * 10.0 * 1.0 - y * 10.0 * 1.0) % v) / v, 0.8f, 0.8f));
    }

    public static int getAlpha(int colour) {
        return (colour >> 24 & 255);
    }

}