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
import xyz.matthewtgm.requisite.Requisite;

import java.util.ArrayList;
import java.util.List;

public class KeyBindManager {

    @Getter private final List<KeyBind> keyBinds = new ArrayList<>();

    public void register(KeyBind keyBind) {
        keyBinds.add(keyBind);
        Requisite.getManager().getKeyBindConfigHandler().update();
    }

    public void unregister(String name) {
        keyBinds.stream().filter(keyBind -> keyBind.name().equalsIgnoreCase(name)).findFirst().ifPresent(keyBinds::remove);
        Requisite.getManager().getKeyBindConfigHandler().update();
    }

    public void unregister(KeyBind keyBind) {
        unregister(keyBind.name());
    }

}