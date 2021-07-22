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

package xyz.matthewtgm.tgmlib.tweaker;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.util.ForgeHelper;

public class TGMLibTransformationChecks {

    private static final Logger logger = LogManager.getLogger(TGMLib.NAME + " (Transformation Checks)");
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