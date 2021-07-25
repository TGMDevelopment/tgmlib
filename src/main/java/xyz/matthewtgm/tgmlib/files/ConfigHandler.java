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

package xyz.matthewtgm.tgmlib.files;

import lombok.Getter;
import xyz.matthewtgm.tgmconfig.ConfigEntry;
import xyz.matthewtgm.tgmconfig.TGMConfig;
import xyz.matthewtgm.tgmconfig.annotations.TGMConfigAnnotationsAPI;
import xyz.matthewtgm.tgmconfig.annotations.options.impl.BooleanOption;

import java.io.File;

public class ConfigHandler {

    @Getter private final TGMConfig config;

    @Getter private final BooleanOption lightMode = new BooleanOption(false);

    @Getter private final BooleanOption showCosmetics = new BooleanOption(true);
    @Getter private final BooleanOption overrideCapes = new BooleanOption(true);

    @Getter private final BooleanOption showIndicators = new BooleanOption(true);

    public ConfigHandler(String name, File dir) {
        this.config = TGMConfigAnnotationsAPI.handle(name, dir, this);
    }

    public void start() {
        if (!config.containsKey("light_mode"))
            config.addAndSave(new ConfigEntry<>("light_mode", false));
        if (!config.containsKey("show_cosmetics"))
            config.addAndSave(new ConfigEntry<>("show_cosmetics", true));
        if (!config.containsKey("override_capes"))
            config.addAndSave(new ConfigEntry<>("override_capes", true));
        if (!config.containsKey("show_indicators"))
            config.addAndSave(new ConfigEntry<>("show_indicators", true));
        update();
    }

    public void update() {
        lightMode.set(config.getAsBoolean("light_mode"));
        showCosmetics.set(config.getAsBoolean("show_cosmetics"));
        overrideCapes.set(config.getAsBoolean("override_capes"));
        showIndicators.set(config.getAsBoolean("show_indicators"));
    }

}