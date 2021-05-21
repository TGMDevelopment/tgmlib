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

public class MathHelper {

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
        return num < min ? min : (num > max ? max : num);
    }

    public static long clamp_long(long num, long min, long max) {
        return num < min ? min : (num > max ? max : num);
    }

    public static float lerp(float start, float end, float interpolation) {
        return start + (end - start) * clamp01(interpolation);
    }

}