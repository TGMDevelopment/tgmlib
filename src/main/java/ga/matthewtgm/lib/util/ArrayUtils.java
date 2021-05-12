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

import java.util.Set;

public class ArrayUtils {

    /**
     * @param set The list or set.
     * @param value The value to check for.
     * @return The object index.
     * @author MatthewTGM
     */
    public static int getIndex(Set<?> set, Object value) {
        int result = 0;
        for (Object entry : set) {
            if (entry.equals(value))
                return result;
            result++;
        }
        return -1;
    }

}