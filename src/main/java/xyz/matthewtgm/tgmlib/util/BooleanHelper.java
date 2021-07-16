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

public class BooleanHelper {

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