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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(name = TGMLib.NAME, version = TGMLib.VERSION, modid = TGMLib.ID)
public class TGMLib {

    @Mod.Instance
    private static TGMLib INSTANCE;

    public static final String NAME = "TGMLib", VERSION = "@VER@", ID = "tgmlib";

    private final Logger logger = LogManager.getLogger("TGMLib");

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        logger.info("Registering listeners...");
        ForgeUtils.registerEventListeners(new KeyBindManager(), new GuiHelper(), new HypixelHelper(), new Notifications(), new MessageQueue());
        logger.info("Listeners registered!");

        StartupRegistry.init(logger);
    }

    public static TGMLib getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TGMLib();
        return INSTANCE;
    }

}