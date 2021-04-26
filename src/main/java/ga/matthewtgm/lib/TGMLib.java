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
import ga.matthewtgm.lib.util.ForgeUtils;
import ga.matthewtgm.lib.util.GuiHelper;
import ga.matthewtgm.lib.util.HypixelHelper;
import ga.matthewtgm.lib.util.Notifications;
import ga.matthewtgm.lib.util.betterkeybinds.KeyBindManager;
import lombok.Getter;

public class TGMLib {

    private static TGMLib INSTANCE;

    public static String NAME = "TGMLib", VERSION = "@VER@";

    @Getter private boolean listenersRegistered;

    public void onForgePreInit() {
        if (!listenersRegistered) {
            ForgeUtils.registerEventListeners(new KeyBindManager(), new GuiHelper(), new HypixelHelper(), new Notifications());
            listenersRegistered = true;
        }
    }

    public static TGMLib getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TGMLib();
        return INSTANCE;
    }

}