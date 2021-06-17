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

package xyz.matthewtgm.lib;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.matthewtgm.lib.startup.StartupRegistry;
import xyz.matthewtgm.lib.util.*;
import xyz.matthewtgm.lib.util.betterkeybinds.KeyBindManager;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.matthewtgm.lib.util.channels.CustomChannelPipeline;

@Mod(name = TGMLib.NAME, version = TGMLib.VERSION, modid = TGMLib.ID)
public class TGMLib {

    @Mod.Instance
    private static TGMLib INSTANCE;

    public static final String NAME = "TGMLib", VERSION = "@VER@", ID = "tgmlib";

    private final Logger logger = LogManager.getLogger("TGMLib");

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        StartupRegistry startupRegistry = new StartupRegistry();
        startupRegistry.init(logger);

        logger.info("Registering listeners...");
        ForgeUtils.registerEventListeners(startupRegistry, new CustomChannelPipeline(), new KeyBindManager(), new ScreenHelper(), new GuiHelper(), new GuiHelper.Editor(), new HypixelHelper(), new TitleHandler(), new Notifications(), new MessageQueue());
        logger.info("Listeners registered!");
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        logger.info("Downloading resources...");
        ResourceCaching.download("TGMLib", "button_light.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/button_light.png");
        ResourceCaching.download("TGMLib", "button_dark.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/button_dark.png");
        ResourceCaching.download("TGMLib", "switch_on.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/config_framework/switch_on.png");
        ResourceCaching.download("TGMLib", "switch_off.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/config_framework/switch_off.png");
        logger.info("Resources downloaded!");
    }

    public static TGMLib getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TGMLib();
        return INSTANCE;
    }

}