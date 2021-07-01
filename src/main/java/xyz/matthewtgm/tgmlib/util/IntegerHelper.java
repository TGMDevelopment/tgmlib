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

import lombok.Getter;

import java.util.Random;

public class IntegerHelper {

    @Getter private static final Random random = new Random();

    /**
     * @param min The minimum number that can be returned.
     * @param max The maximum number that can be returned.
     * @return A number within the given range.
     * @author MatthewTGM
     */
    public static Integer getRandomNumber(int min, int max) {
        return random.nextInt(max - min) + min;
    }

}