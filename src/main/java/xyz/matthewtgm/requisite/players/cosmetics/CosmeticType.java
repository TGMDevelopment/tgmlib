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

package xyz.matthewtgm.requisite.players.cosmetics;

import lombok.Getter;

public enum CosmeticType {
    CLOAK("Cloaks", 0),
    WINGS("Wings", 1),
    HAT("Hats", 2),
    TAIL("Tails", 3),
    EARS("Ears", 4);

    @Getter
    private final String name;
    @Getter
    private final int id;
    CosmeticType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static CosmeticType fromId(int id) {
        for (CosmeticType type : values()) if (type.getId() == id) return type;
        return null;
    }

}