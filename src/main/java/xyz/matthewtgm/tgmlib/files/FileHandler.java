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
import net.minecraft.client.Minecraft;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandler {

    @Getter private final File mcDir = Minecraft.getMinecraft().mcDataDir, configDir = new File(mcDir, "config"), tgmDevelopmentDir = new File(configDir, "TGMDevelopment"), tgmLibDir = new File(tgmDevelopmentDir, "TGMLib");
    @Getter private final List<File> directories = new ArrayList<>(Arrays.asList(mcDir, configDir, tgmDevelopmentDir, tgmLibDir));

    public void start() {
        for (File directory : directories) if (!directory.exists() && !directory.mkdirs()) throw new IllegalStateException("Unable to create directories.");
    }

}