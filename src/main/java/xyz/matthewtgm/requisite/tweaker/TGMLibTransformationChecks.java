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

package xyz.matthewtgm.requisite.tweaker;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.util.ForgeHelper;

public class TGMLibTransformationChecks {

    private static final Logger logger = LogManager.getLogger(Requisite.NAME + " (Transformation Checks)");
    private static boolean checked;

    @Getter private static Boolean deobfuscated;
    @Getter private static Boolean seargeMappings;

    public static void check() {
        if (checked)
            throw new IllegalStateException("Already checked for obfuscation and mappings!");

        deobfuscated = ForgeHelper.isDevelopmentEnvironment();
        seargeMappings = !deobfuscated;

        checked = true;
        logger.warn("(Deobfuscated: {}) | (Searge: {})", deobfuscated, seargeMappings);
    }

}