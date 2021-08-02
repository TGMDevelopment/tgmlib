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

package xyz.matthewtgm.requisite.files;

import lombok.Getter;
import xyz.matthewtgm.tgmconfig.Configuration;

import java.io.File;

public class DataHandler {

    @Getter private final Configuration data;

    @Getter private boolean receivedPrompt = false;
    @Getter private boolean mayLogData = true;

    public DataHandler(String name, File directory) {
        this.data = new Configuration(new File(directory, name));
    }

    public void start() {
        if (!data.hasKey("prompt_received"))
            data.add("prompt_received", false).save();
        if (!data.hasKey("log_data"))
            data.add("log_data", false).save();
        update();
    }

    public void update() {
        receivedPrompt = data.getAsBoolean("prompt_received");
        mayLogData = data.getAsBoolean("log_data");
    }

    public void setReceivedPrompt(boolean receivedPrompt) {
        this.receivedPrompt = receivedPrompt;
        data.add("prompt_received", receivedPrompt).save();
    }

    public void setMayLogData(boolean mayLogData) {
        this.mayLogData = mayLogData;
        data.add("log_data", mayLogData).save();
    }

}