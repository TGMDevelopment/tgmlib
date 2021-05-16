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
import ga.matthewtgm.lib.commands.CommandManager;
import ga.matthewtgm.lib.commands.bettercommands.Command;
import ga.matthewtgm.lib.util.*;
import ga.matthewtgm.lib.util.betterkeybinds.KeyBindManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(name = TGMLib.NAME, version = TGMLib.VERSION, modid = TGMLib.ID)
public class TGMLib {

    @Mod.Instance
    private static TGMLib INSTANCE;

    public static final String NAME = "TGMLib", VERSION = "@VER@", ID = "tgmlib";
    public static final String chatPrefix = EnumChatFormatting.GRAY + "[" + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD.toString() + "TGMLib" + EnumChatFormatting.RESET + EnumChatFormatting.GRAY.toString() + "]";

    private final Logger logger = LogManager.getLogger("TGMLib");

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        logger.info("Registering listeners...");
        ForgeUtils.registerEventListeners(new KeyBindManager(), new GuiHelper(), new HypixelHelper(), new Notifications());
        logger.info("Listeners registered!");

        logger.info("Registering commands...");
        CommandManager.register(TGMLibCommand.class);
        logger.info("Commands registered!!");
    }

    public static TGMLib getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TGMLib();
        return INSTANCE;
    }

    @Command(name = "tgmlib")
    private static class TGMLibCommand {

        @Command.Process
        protected void process(EntityPlayer player, String[] args) {
            if (args.length <= 0) {
                ChatHandler.sendMessage(TGMLib.chatPrefix, EnumChatFormatting.RED + "This command requires arguments! Press tab with the command entered in chat to see options.");
            }
        }

        @Command.Argument(name = "notitest")
        protected void notificationTest(String[] args) {
            Notifications.push("Test Notification", "Test Description");
        }

        @Command.Argument(name = "notitest2")
        protected void notificationTest2(String[] args) {
            Notifications.push("Test Clickable Notification", "Test Clickable Description", () -> ChatHandler.sendMessage(TGMLib.chatPrefix, "Notification clicked!"));
        }

    }

}