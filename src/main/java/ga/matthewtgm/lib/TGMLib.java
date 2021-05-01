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

package ga.matthewtgm.lib;
import ga.matthewtgm.lib.util.*;
import ga.matthewtgm.lib.util.betterkeybinds.KeyBindManager;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TGMLib {

    private static TGMLib INSTANCE;

    public static String NAME = "TGMLib", VERSION = "@VER@";

    private final Logger logger = LogManager.getLogger("TGMLib");

    @Getter private boolean listenersRegistered;

    public void onForgePreInit() {
        if (!listenersRegistered) {
            logger.info("Registering listeners...");
            ForgeUtils.registerEventListeners(new KeyBindManager(), new GuiHelper(), new HypixelHelper(), new Notifications());
            logger.info("Listeners registered!");
            listenersRegistered = true;
        }
    }

    public static TGMLib getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TGMLib();
        return INSTANCE;
    }

}