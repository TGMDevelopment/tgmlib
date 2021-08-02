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

package xyz.matthewtgm.requisite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.matthewtgm.requisite.commands.advanced.Command;
import xyz.matthewtgm.requisite.data.ColourRGB;
import xyz.matthewtgm.requisite.gui.menus.GuiRequisiteCosmetics;
import xyz.matthewtgm.requisite.gui.menus.GuiRequisiteKeyBinds;
import xyz.matthewtgm.requisite.gui.menus.GuiRequisiteMain;
import xyz.matthewtgm.requisite.gui.menus.GuiRequisiteSettings;
import xyz.matthewtgm.requisite.networking.packets.impl.announcer.AnnouncementPacket;
import xyz.matthewtgm.requisite.util.*;
import xyz.matthewtgm.requisite.util.global.GlobalMinecraft;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lets the user interact with Requisite in the simplest way possible.
 */
@Command(
        name = "requisite",
        autoGenTabCompleteOptions = true
)
public final class RequisiteCommand {

    private final Pattern announcementPattern = Pattern.compile("(\\\".+\\\") (\\\".+\\\") (\\\".+\\\")");
    private final Logger logger = LogManager.getLogger("TGMLib (TGMLibCommand)");

    @Command.Process
    private void process() {
        GuiHelper.open(new GuiRequisiteMain(GlobalMinecraft.getCurrentScreen()));
    }

    @Command.Argument(name = "cosmetics")
    private void cosmetics() {
        GuiHelper.open(new GuiRequisiteCosmetics(GlobalMinecraft.getCurrentScreen()));
    }

    @Command.Argument(name = "keybinds", aliases = "keybindings")
    private void keybinds() {
        GuiHelper.open(new GuiRequisiteKeyBinds(GlobalMinecraft.getCurrentScreen()));
    }

    @Command.Argument(name = "settings", aliases = {"config", "options"})
    private void settings() {
        GuiHelper.open(new GuiRequisiteSettings(GlobalMinecraft.getCurrentScreen()));
    }

    @Command.Argument(name = "announce")
    private void announce(String[] args) {
        List<String> argsList = new ArrayList<>(Arrays.asList(args));
        if (argsList.get(0).equalsIgnoreCase("announce"))
            argsList.remove(0);
        String jointArgs = StringHelper.join(argsList, " ");
        Matcher announcementMatcher = announcementPattern.matcher(jointArgs);
        if (announcementMatcher.find())
            Requisite.getManager().getWebSocket().send(new AnnouncementPacket(announcementMatcher.group(1).replaceAll("\"", "").trim(), announcementMatcher.group(2).replaceAll("\"", "").trim(), announcementMatcher.group(3).replaceAll("\"", "").trim()));
        else
            ChatHelper.sendMessage(ChatHelper.requisiteChatPrefix, ChatColour.RED + "Invalid format! (/tgmlib announce \"password\" \"title\" \"description\")");
    }

    @Command.Argument(name = "locraw")
    private void locraw() {
        ChatHelper.sendMessage(ChatHelper.requisiteChatPrefix, HypixelHelper.getLocraw());
    }

    @Command.Argument(name = "notification")
    private void notification() {
        Notifications.push("Hello, World!", "I'm a cooler, clickable  notification!", notification -> {
            notification.title = "Click!";
            notification.description = "I was clicked! Oh my!";
        });
        Notifications.push("Hello, World 2!", "I'm an even cooler notification with text wrappinggg YOOOOOOOOOO!", notification -> {
            notification.title = "Loading...";
            notification.description = "";
            GuiHelper.open(new GuiRequisiteMain(null));
            notification.close();
        });
        Notifications.push("Hello, World 3!", "I'm a custom coloured notification!", new Notifications.Notification.NotificationColour(null, new ColourRGB(0, 0, 255)));
        Notifications.push("Hello, World 4!", "I'm a an even more custom coloured notification!", new Notifications.Notification.NotificationColour(new ColourRGB(255, 0, 0), new ColourRGB(0, 0, 255)));
    }

    @Command.Argument(name = "debug")
    private void debug(String[] args) throws Exception {
        if (args.length <= 0)
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
                Desktop.getDesktop().browse(URI.create("https://youtu.be/dQw4w9WgXcQ"));
    }

}