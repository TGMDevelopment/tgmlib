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

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class ArrayHelper {

    @SafeVarargs
    public static <T> List<T> convert(T... array) {
        return Arrays.asList(array);
    }

    public static <T> T[] convert(List<T> list) {
        return (T[]) list.toArray();
    }

    public static <T> boolean contains(T[] array, T check) {
        return Arrays.asList(array).contains(check);
    }

    public static <T> Stream<T> filter(List<T> list, Predicate<? super T> predicate) {
        return list.stream().filter(predicate);
    }

    public static <T> Stream<T> filter(T[] array, Predicate<? super T> predicate) {
        return convert(array).stream().filter(predicate);
    }

    public static double averageInts(List<Integer> list) {
        int total = 0;
        for(Integer integer : list)
            total += integer;
        return (double) total / list.size();
    }

}