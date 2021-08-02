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

public final class MathHelper {

    // TODO: 2021/07/16 : Javadoc!

    private MathHelper() {}

    public static float clamp01(float value) {
        if ((double)value < 0.0)
            return 0.0f;
        return (double)value > 1.0 ? 1f : value;
    }

    public static float clamp(float val, float min, float max) {
        if (val > max) val = max;
        else if (val < min) val = min;
        return val;
    }

    public static int clamp_int(int num, int min, int max) {
        return num < min ? min : (Math.min(num, max));
    }

    public static long clamp_long(long num, long min, long max) {
        return num < min ? min : (Math.min(num, max));
    }

    public static float lerp(float start, float end, float interpolation) {
        return start + (end - start) * clamp01(interpolation);
    }

    public static float percentageOf(float val, float min, float max) {
        return (val - min) / (max - min);
    }

    public static int percentageOf_int(int val, int min, int max) {
        return (val - min) / (max - min);
    }

    public static boolean isBetween(int val, int min, int max) {
        return (val > min) && (val < max);
    }

}