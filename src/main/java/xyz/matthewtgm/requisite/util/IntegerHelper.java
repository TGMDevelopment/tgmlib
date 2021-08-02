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

import lombok.Getter;

import java.util.Random;

public final class IntegerHelper {

    @Getter private static final Random random = new Random();

    private IntegerHelper() {}

    /**
     * @param min The minimum number that can be returned.
     * @param max The maximum number that can be returned.
     * @return A number within the given range.
     * @author MatthewTGM
     */
    public static Integer getRandomNumber(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    /**
     * @param integer The integer to check.
     * @return Whether or not the integer is positive.
     * @author MatthewTGM
     */
    public static boolean isPositive(int integer) {
        return integer >= 0;
    }

    /**
     * @param integer The integer to check.
     * @return Whether or not the integer is negative.
     * @author MatthewTGM
     */
    public static boolean isNegative(int integer) {
        return integer < 0;
    }

}