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
import net.minecraft.client.Minecraft;
import xyz.matthewtgm.requisite.Requisite;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandler {

    @Getter private final File
            mcDir = Minecraft.getMinecraft().mcDataDir,
            configDir = new File(mcDir, "config"),
            tgmDevelopmentDir = new File(configDir, "TGMDevelopment"),
            requisiteDir = new File(tgmDevelopmentDir, Requisite.NAME);
    @Getter private final List<File> directories = new ArrayList<>(Arrays.asList(
            mcDir,
            configDir,
            tgmDevelopmentDir,
            requisiteDir
    ));

    public void start() {
        for (File directory : directories)
            if (!directory.exists() && !directory.mkdirs())
                throw new IllegalStateException("Unable to create TGMLib directories.");
    }

}