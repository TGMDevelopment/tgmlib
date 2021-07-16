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

import net.minecraft.util.StringUtils;

import java.util.Iterator;
import java.util.regex.Pattern;

public class StringHelper {

    private static final Pattern FORMATTING_CODE_PATTERN = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

    public static String getLongestString(Object[] strings) {
        String longestString = "";
        int longest = 0;
        for (Object o : strings) {
            String string = (String) o;
            if (string.length() > longest) {
                longestString = string;
                longest = string.length();
            }
        }
        return longestString;
    }

    public static int getLongestStringWidth(Object[] strings) {
        return getLongestString(strings).length();
    }

    public static String removeColourCodes(String input) {
        return FORMATTING_CODE_PATTERN.matcher(input).replaceAll("");
    }

    public static String join(Iterable<?> iterable, String separator) {
        if (iterable == null)
            return null;
        return join(iterable.iterator(), separator);
    }

    public static String join(Iterator<?> iterator, String separator) {
        if (iterator == null)
            return null;
        if (!iterator.hasNext())
            return "";
        Object first = iterator.next();
        if (!iterator.hasNext())
            return ObjectHelper.stringify(first);
        StringBuilder buf = new StringBuilder();
        if (first != null)
            buf.append(first);
        while (iterator.hasNext()) {
            if (separator != null)
                buf.append(separator);
            Object obj = iterator.next();
            if (obj != null)
                buf.append(obj);
        }
        return buf.toString();
    }

}