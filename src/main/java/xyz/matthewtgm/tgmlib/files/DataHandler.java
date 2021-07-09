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

public class DataHandler {

    private final TGMConfig data;

    @Getter private boolean receivedPrompt;
    @Getter private boolean mayLogData;

    public DataHandler(TGMConfig data) {
        this.data = data;
    }

    public void start() {
        if (!data.containsKey("prompt_received")) data.addAndSave(new ConfigEntry<>("prompt_received", false));
        if (!data.containsKey("log_data")) data.addAndSave(new ConfigEntry<>("log_data", false));
        update();
    }

    public void update() {
        receivedPrompt = data.getAsBoolean("prompt_received");
        mayLogData = data.getAsBoolean("log_data");
    }

}