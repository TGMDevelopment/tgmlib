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
import xyz.matthewtgm.json.entities.JsonElement;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.tgmconfig.Configuration;

import java.io.File;

public class KeyBindConfigHandler {

    @Getter private final Configuration configuration;

    public KeyBindConfigHandler(String name, File directory) {
        this.configuration = new Configuration(new File(directory, name));
    }

    public void update() {
        for (KeyBind keyBind : Requisite.getManager().getKeyBindManager().getKeyBinds()) {
            if (!configuration.hasKey(keyBind.category()))
                update(keyBind);
            if (!configuration.getAsJsonObject(keyBind.category()).hasKey(keyBind.name()))
                update(keyBind);
            JsonElement keyCodeElement = configuration.getAsJsonObject(keyBind.category()).get(keyBind.name());
            keyBind.updateKey(keyCodeElement.isDouble() ? (int) keyCodeElement.getAsDouble() : keyCodeElement.isFloat() ? (int) keyCodeElement.getAsFloat() : keyCodeElement.getAsInt());
        }
    }

    public void update(KeyBind keyBind) {
        if (!configuration.hasKey(keyBind.category()))
            configuration.add(keyBind.category(), new JsonObject().add(keyBind.name(), keyBind.getKey())).save();
        configuration.add(keyBind.category(), configuration.getAsJsonObject(keyBind.category()).add(keyBind.name(), keyBind.getKey())).save();
    }

}