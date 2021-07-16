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

package xyz.matthewtgm.tgmlib;

import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.matthewtgm.tgmlib.commands.advanced.Command;
import xyz.matthewtgm.tgmlib.data.ColourRGB;
import xyz.matthewtgm.tgmlib.gui.menus.GuiTGMLibCosmetics;
import xyz.matthewtgm.tgmlib.gui.menus.GuiTGMLibKeyBinds;
import xyz.matthewtgm.tgmlib.gui.menus.GuiTGMLibMain;
import xyz.matthewtgm.tgmlib.socket.packets.impl.announcer.AnnouncementPacket;
import xyz.matthewtgm.tgmlib.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Command(name = "tgmlib", autoGenTabCompleteOptions = true)
public class TGMLibCommand {

    private final Pattern announcementPattern = Pattern.compile("(\\\".+\\\") (\\\".+\\\") (\\\".+\\\")");

    @Command.Process
    private void process() {
        GuiHelper.open(new GuiTGMLibMain(null));
    }

    @Command.Argument(name = "cosmetics")
    private void cosmetics() {
        GuiHelper.open(new GuiTGMLibCosmetics(null));
    }

    @Command.Argument(name = "keybinds", aliases = "keybindings")
    private void keybinds() {
        GuiHelper.open(new GuiTGMLibKeyBinds(null));
    }

    @Command.Argument(name = "test")
    private void test() {
        Logger logger = LogManager.getLogger("TGMLib (TGMLibCommand - Test)");
    }

    @Command.Argument(name = "announce")
    private void announce(String[] args) {
        List<String> argsList = new ArrayList<>(Arrays.asList(args));
        if (argsList.get(0).equalsIgnoreCase("announce")) argsList.remove(0);
        String jointArgs = StringUtils.join(argsList, " ");
        Matcher announcementMatcher = announcementPattern.matcher(jointArgs);
        if (announcementMatcher.find())
            TGMLib.getManager().getWebSocket().send(new AnnouncementPacket(announcementMatcher.group(1).replaceAll("\"", "").trim(), announcementMatcher.group(2).replaceAll("\"", "").trim(), announcementMatcher.group(3).replaceAll("\"", "").trim()));
        else
            ChatHelper.sendMessage(ChatHelper.tgmLibChatPrefix, ChatColour.RED + "Invalid format! (/tgmlib announce \"password\" \"title\" \"description\")");
    }

    @Command.Argument(name = "locraw")
    private void locraw() {
        ChatHelper.sendMessage(ChatHelper.tgmLibChatPrefix, HypixelHelper.getLocraw());
    }

    @Command.Argument(name = "notification")
    private void notification() {
        Notifications.push("Hello, World!", "I'm a cooler, clickable  notification!", notification -> {
            notification.title = "Click!";
            notification.description = "I was clicked! Oh my!";
        });
        Notifications.push("Hello, World 2!", "I'm an even cooler notification with text wrappinggg YOOOOOOOOOO!");
        Notifications.push("Hello, World 3!", "I'm a custom coloured notification!", new Notifications.Notification.NotificationColour(null, new ColourRGB(0, 0, 255)));
        Notifications.push("Hello, World 4!", "I'm a an even more custom coloured notification!", new Notifications.Notification.NotificationColour(new ColourRGB(255, 0, 0), new ColourRGB(0, 0, 255)));
    }

    @Command.Argument(name = "debug")
    private void debug(String[] args) {

    }

}