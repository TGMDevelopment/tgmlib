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

package xyz.matthewtgm.tgmlib.keybinds;

import lombok.Getter;
import xyz.matthewtgm.json.entities.JsonElement;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.tgmconfig.Configuration;

public class KeyBindConfigHandler {

    @Getter private final Configuration configuration;

    public KeyBindConfigHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    public void update() {
        for (KeyBind keyBind : KeyBindManager.getKeyBinds()) {
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