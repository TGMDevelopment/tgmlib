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
import net.minecraft.launchwrapper.Launch;
import xyz.matthewtgm.tgmconfig.Configuration;

public class ConfigHandler {

    @Getter private final Configuration configuration;

    @Getter private boolean lightMode = false;

    @Getter private boolean showCosmetics = true;
    @Getter private boolean overrideCapes = true;

    @Getter private boolean showIndicators = true;

    public ConfigHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    public void start() {
        if (!configuration.hasKey("light_mode"))
            configuration.add("light_mode", false).save();
        if (!configuration.hasKey("show_cosmetics"))
            configuration.add("show_cosmetics", true).save();
        if (!configuration.hasKey("override_capes"))
            configuration.add("override_capes", true).save();
        if (!configuration.hasKey("show_indicators"))
            configuration.add("show_indicators", true).save();
        update();
    }

    public void update() {
        setLightMode(configuration.getAsBoolean("light_mode"));
        setShowCosmetics(configuration.getAsBoolean("show_cosmetics"));
        setOverrideCapes(configuration.getAsBoolean("override_capes"));
        setShowIndicators(configuration.getAsBoolean("show_indicators"));
    }

    public void setLightMode(boolean lightMode) {
        this.lightMode = lightMode;
        configuration.add("light_mode", lightMode).save();
    }

    public void setShowCosmetics(boolean showCosmetics) {
        this.showCosmetics = showCosmetics;
        configuration.add("show_cosmetics", showCosmetics).save();
        Launch.blackboard.put("tgmLib_show_cosmetics", showCosmetics);
    }

    public void setOverrideCapes(boolean overrideCapes) {
        this.overrideCapes = overrideCapes;
        configuration.add("override_capes", overrideCapes).save();
        Launch.blackboard.put("tgmLib_override_capes", overrideCapes);
    }

    public void setShowIndicators(boolean showIndicators) {
        this.showIndicators = showIndicators;
        configuration.add("show_indicators", showIndicators).save();
        Launch.blackboard.put("tgmLib_show_indicators", showIndicators);
    }

}