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

package xyz.matthewtgm.requisite.keybinds;

import lombok.Getter;

public abstract class KeyBind {

    @Getter private int key;

    public KeyBind(int key) {
        this.key = key;
    }

    public abstract String name();
    public abstract String category();
    public abstract void pressed();
    public abstract void held();
    public abstract void released();

    public boolean worksInGuis() {
        return false;
    }

    public void updateKey(int key) {
        this.key = key;
    }

}