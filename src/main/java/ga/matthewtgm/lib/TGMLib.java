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
import ga.matthewtgm.lib.commands.SimpleCommand;
import ga.matthewtgm.lib.commands.SimpleCommandEntry;
import ga.matthewtgm.lib.util.*;
import ga.matthewtgm.lib.util.betterkeybinds.KeyBindManager;
import lombok.Getter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class TGMLib {

    private static TGMLib INSTANCE;

    public static String NAME = "TGMLib", VERSION = "@VER@";

    private Logger logger = LogManager.getLogger("TGMLib");

    @Getter private boolean listenersRegistered;
    @Getter private boolean commandsRegistered;

    public void onForgePreInit() {
        if (!listenersRegistered) {
            logger.info("Registering listeners...");
            ForgeUtils.registerEventListeners(new KeyBindManager(), new GuiHelper(), new HypixelHelper(), new Notifications());
            logger.info("Listeners registered!");
            listenersRegistered = true;
        }

        if (!commandsRegistered) {
            logger.info("Registering commands...");
            ClientCommandHandler.instance.registerCommand(new SimpleCommand(new SimpleCommandEntry() {
                @Override
                public String name() {
                    return null;
                }
                @Override
                public String usage() {
                    return null;
                }
                @Override
                public int permissionLevel() {
                    return -1;
                }
                @Override
                public void process(EntityPlayer sender, String[] args) {
                    String text = EnumChatFormatting.RED + "This command is unfinished";
                    if (args.length <= 0) {
                        ChatHandler.sendMessage(text);
                        return;
                    }
                    switch (args[0].toLowerCase()) {
                        case "1":
                            Notifications.push("Test Notification", "Test Description");
                            break;
                        case "2":
                            Notifications.push("Test Notification + Runnable", "Test Description + Runnable", () -> ChatHandler.sendMessage("Hello World!"));
                            break;
                        default:
                            ChatHandler.sendMessage(text);
                    }
                }
                @Override
                public List<String> tabCompleteOptions() {
                    return Collections.emptyList();
                }
            }));
            logger.info("Commands registered!");
            commandsRegistered = true;
        }
    }

    public static TGMLib getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TGMLib();
        return INSTANCE;
    }

}