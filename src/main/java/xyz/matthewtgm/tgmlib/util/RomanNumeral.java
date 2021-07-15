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

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// https://stackoverflow.com/a/19759564
public class RomanNumeral {

    private final static TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    private static final Map<Integer, String> numeralCache = new HashMap<>();

    public static String getCached(int number) {
        if (numeralCache.containsKey(number))
            return numeralCache.get(number);

        String roman = toRoman(number);
        numeralCache.put(number, roman);
        return roman;
    }

    public static String toRoman(int number) {
        int l = map.floorKey(number);
        if (number == l)
            return map.get(number);
        return map.get(l) + toRoman(number - l);
    }

}