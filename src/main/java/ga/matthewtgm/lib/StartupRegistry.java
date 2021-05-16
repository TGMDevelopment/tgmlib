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
import ga.matthewtgm.lib.util.ChatHandler;
import ga.matthewtgm.lib.util.Notifications;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.Logger;

public class StartupRegistry {

    static void init(Logger logger) {
        logger.info("Registering commands...");
        CommandManager.register(TGMLibCommand.class);
        logger.info("Commands registered!!");
    }

    @Command(name = "tgmlib", tabCompleteOptions = {"notitest1", "notitest2"})
    public static class TGMLibCommand {

        @Command.Process
        protected void process(EntityPlayer sender, String[] args) {
            if (args.length <= 0) {
                ChatHandler.sendMessage(ChatHandler.tgmLibChatPrefix, EnumChatFormatting.RED + "This command requires arguments! Press tab with the command entered in chat to see options.");
            }
        }

        @Command.Argument(name = "notitest1")
        protected void notificationTest1(String[] args) {
            Notifications.push("Test Notification", "Test Description");
        }

        @Command.Argument(name = "notitest2")
        protected void notificationTest2(String[] args) {
            Notifications.push("Test Clickable Notification", "Test Clickable Description", () -> ChatHandler.sendMessage(ChatHandler.tgmLibChatPrefix, "Notification clicked!"));
        }

    }

}