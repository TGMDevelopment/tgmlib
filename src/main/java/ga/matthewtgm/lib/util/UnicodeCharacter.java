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

import lombok.Getter;

public enum UnicodeCharacter {

    LEFT_ARROW("\u2190"),
    UP_ARROW("\u2191"),
    RIGHT_ARROW("\u2192"),
    DOWN_ARROW("\u2193"),

    MULTIPLICATION_SIGN("\u00D7"),

    BULLET_POINT("\u2022"),
    DEGREES_SIGN("\u00B0"),
    STAR("\u2605"),

    TRADE_MARK_SYMBOL("\u2122"),

    LEFT_ANGLE("\u276E"),
    RIGHT_ANGLE("\u276F"),

    BLACK_FLAG("\u2691"),
    WHITE_FLAG("\u2690"),

    ARABIC_NUMERAL_1("\u2460"),
    ARABIC_NUMERAL_2("\u2461"),
    ARABIC_NUMERAL_3("\u2462"),
    ARABIC_NUMERAL_4("\u2463"),
    ARABIC_NUMERAL_5("\u2464"),
    ARABIC_NUMERAL_6("\u2465"),
    ARABIC_NUMERAL_7("\u2466"),
    ARABIC_NUMERAL_8("\u2467"),
    ARABIC_NUMERAL_9("\u2468"),
    ARABIC_NUMERAL_10("\u2469");

    @Getter private final String asString;
    UnicodeCharacter(String asString) {
        this.asString = asString;
    }

    @Override
    public String toString() {
        return asString;
    }

}