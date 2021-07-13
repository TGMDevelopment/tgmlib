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

import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.tgmconfig.ConfigEntry;
import xyz.matthewtgm.tgmconfig.TGMConfig;

public class KeyBindConfigHandler {

    private final TGMConfig config;

    public KeyBindConfigHandler(TGMConfig config) {
        this.config = config;
    }

    public void update() {
        for (KeyBind keyBind : KeyBindManager.getKeyBinds()) {
            if (!config.containsKey(keyBind.category()))
                update(keyBind);
            if (!config.getAsJsonObject(keyBind.category()).hasKey(keyBind.name()))
                update(keyBind);
            keyBind.updateKey(config.getAsJsonObject(keyBind.category()).get(keyBind.name()).getAsInt());
        }
    }

    public void update(KeyBind keyBind) {
        if (!config.containsKey(keyBind.category()))
            config.addAndSave(new ConfigEntry<>(keyBind.category(), new JsonObject().add(keyBind.name(), keyBind.getKey())));
        config.addAndSave(new ConfigEntry<>(keyBind.category(), config.getAsJsonObject(keyBind.category()).add(keyBind.name(), keyBind.getKey())));
    }

}