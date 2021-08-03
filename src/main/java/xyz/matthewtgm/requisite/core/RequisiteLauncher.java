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

package xyz.matthewtgm.requisite.core;

import lombok.Getter;
import net.minecraftforge.fml.relauncher.CoreModManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.net.URL;
import java.security.CodeSource;

public class RequisiteLauncher {

    @Getter private static boolean initialized = false;

    public static void initialize() {
        if (initialized)
            return;
        initialized = true;
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.requisite.json");

        /* https://github.com/Mouse0w0/forge-mixin-example/blob/08a51985822736f51e0b77a58850ff3abc4628bd/src/main/java/com/yourname/modid/core/CoreMod.java */
        CodeSource source = RequisiteLauncher.class.getProtectionDomain().getCodeSource();
        if (source != null) {
            URL location = source.getLocation();
            try {
                File sourceFile = new File(location.toURI());
                if (sourceFile.isFile()) {
                    CoreModManager.getIgnoredMods().remove(sourceFile.getName());
                    CoreModManager.getReparseableCoremods().remove(sourceFile.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Logger logger = LogManager.getLogger("Requisite (Launcher)");
            logger.warn("No CodeSource, if this isn't a development environment we may run into problems!");
        }
    }

}