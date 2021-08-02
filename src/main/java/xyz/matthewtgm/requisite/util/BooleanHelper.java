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

public final class BooleanHelper {

    private BooleanHelper() {}

    /**
     * @param booleans All of the booleans to check.
     * @return Whether or not any of the booleans are true.
     * @author MatthewTGM
     */
    public static boolean anyTrue(boolean... booleans) {
        for (boolean bool : booleans)
            if (bool)
                return true;
        return false;
    }

    /**
     * @param booleans All of the booleans to check.
     * @return Whether or not any of the booleans are false.
     * @author MatthewTGM
     */
    public static boolean anyFalse(boolean... booleans) {
        for (boolean bool : booleans)
            if (!bool)
                return true;
        return false;
    }

    public static Boolean objectify(boolean bool) {
        return new Boolean(bool);
    }

}