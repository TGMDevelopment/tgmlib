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

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public final class ArrayHelper {

    private ArrayHelper() {}

    /**
     * @param array The array to convert.
     * @param <T> The type of the array.
     * @return The array converted to a list.
     * @author MatthewTGM
     */
    @SafeVarargs
    public static <T> List<T> convert(T... array) {
        return Arrays.asList(array);
    }

    /**
     * @param list The list to convert.
     * @param <T> The type of the list.
     * @return The list converted to an array.
     * @author MatthewTGM
     */
    public static <T> T[] convert(List<T> list) {
        return (T[]) list.toArray();
    }

    /**
     * @param array The array to check from.
     * @param check The object to check for.
     * @param <T> The type of the array and object.
     * @return Whether or not the array contains the check.
     * @author MatthewTGM
     */
    public static <T> boolean contains(T[] array, T check) {
        return Arrays.asList(array).contains(check);
    }

    /**
     * @param list The list to filter.
     * @param predicate The filter itself.
     * @param <T> The type of the list.
     * @return The filtered stream.
     * @author MatthewTGM
     */
    public static <T> Stream<T> filter(List<T> list, Predicate<? super T> predicate) {
        return list.stream().filter(predicate);
    }

    /**
     * @param array The array to filter from.
     * @param predicate The filter itself.
     * @param <T> The type of the array.
     * @return The filtered stream.
     * @author MatthewTGM
     */
    public static <T> Stream<T> filter(T[] array, Predicate<? super T> predicate) {
        return convert(array).stream().filter(predicate);
    }

    /**
     * @param list The list to average.
     * @return The average.
     * @author Danterus
     */
    public static double averageInts(List<Integer> list) {
        int total = 0;
        for(Integer integer : list)
            total += integer;
        return (double) total / list.size();
    }

}