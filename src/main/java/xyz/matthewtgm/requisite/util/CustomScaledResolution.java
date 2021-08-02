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
import net.minecraft.util.MathHelper;

public final class CustomScaledResolution extends ScaledResolution {

    private final double scaledWidthD;
    private final double scaledHeightD;
    private int scaledWidth;
    private int scaledHeight;
    private int scaleFactor;

    public CustomScaledResolution(Minecraft mc) {
        super(mc);
        this.scaledWidth = mc.displayWidth;
        this.scaledHeight = mc.displayHeight;
        this.scaleFactor = 1;
        boolean flag = mc.isUnicode();
        int i = mc.gameSettings.guiScale;
        if (i == 0)
            i = 1000;
        while (scaleFactor < i && scaledWidth / (scaleFactor + 1) >= 320 && scaledHeight / (scaleFactor + 1) >= 240)
            ++scaleFactor;
        if (flag && scaleFactor % 2 != 0 && scaleFactor != 1)
            --scaleFactor;
        this.scaledWidthD = (double) scaledWidth / (double) scaleFactor;
        this.scaledHeightD = (double) scaledHeight / (double) scaleFactor;
        this.scaledWidth = MathHelper.ceiling_double_int(scaledWidthD);
        this.scaledHeight = MathHelper.ceiling_double_int(scaledHeightD);
    }

    public CustomScaledResolution(Minecraft mc, int scaleFactor) {
        super(mc);
        this.scaledWidth = mc.displayWidth;
        this.scaledHeight = mc.displayHeight;
        this.scaleFactor = 1;
        boolean flag = mc.isUnicode();
        int i = scaleFactor;
        if (i == 0)
            i = 1000;
        while (scaleFactor < i && scaledWidth / (scaleFactor + 1) >= 320 && scaledHeight / (scaleFactor + 1) >= 240)
            ++scaleFactor;
        if (flag && scaleFactor % 2 != 0 && scaleFactor != 1)
            --scaleFactor;
        this.scaledWidthD = (double) scaledWidth / (double) scaleFactor;
        this.scaledHeightD = (double) scaledHeight / (double) scaleFactor;
        this.scaledWidth = MathHelper.ceiling_double_int(scaledWidthD);
        this.scaledHeight = MathHelper.ceiling_double_int(scaledHeightD);
    }

    public int getScaledWidth() {
        return scaledWidth;
    }

    public int getScaledHeight() {
        return scaledHeight;
    }

    public double getScaledWidth_double() {
        return this.scaledWidthD;
    }

    public double getScaledHeight_double() {
        return scaledHeightD;
    }

    public int getScaleFactor() {
        return scaleFactor;
    }

}