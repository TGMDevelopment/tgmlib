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

package xyz.matthewtgm.lib.startup;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import xyz.matthewtgm.lib.commands.bettercommands.Command;
import xyz.matthewtgm.lib.config.ConfigMenu;
import xyz.matthewtgm.lib.config.ConfigOption;
import xyz.matthewtgm.lib.config.ConfigOptionType;
import xyz.matthewtgm.lib.util.ArrayHelper;
import xyz.matthewtgm.lib.util.ChatHandler;
import xyz.matthewtgm.lib.util.MessageQueue;
import xyz.matthewtgm.lib.util.Notifications;
import xyz.matthewtgm.lib.util.global.GlobalMinecraft;
import xyz.matthewtgm.tgmconfig.TGMConfig;

import java.io.File;

@Command(name = "tgmlib", tabCompleteOptions = {"notitest1", "notitest2", "messagequeuetest", "configframeworktest"})
public class TGMLibCommand {

    static TGMLibCommand $this = null;
    private final TestConfigMenu testConfigMenu;
    public final TGMConfig config = new TGMConfig("tgmlib", new File(GlobalMinecraft.getGameDirectory(), "config"));

    public TGMLibCommand() {
        $this = this;
        testConfigMenu = new TestConfigMenu();
    }

    @Command.Process
    protected void process(String[] args) {
        if (args.length <= 0) ChatHandler.sendMessage(ChatHandler.tgmLibChatPrefix, EnumChatFormatting.RED + "This command requires arguments! Press tab with the command entered in chat to see options.");
    }

    @Command.Argument(name = "notitest1")
    protected void notificationTest1() {
        Notifications.push("Test Notification", "Test Description");
    }

    @Command.Argument(name = "notitest2")
    protected void notificationTest2() {
        Notifications.push("Test Clickable Notification", "Test Clickable Description", () -> ChatHandler.sendMessage(ChatHandler.tgmLibChatPrefix, "Notification clicked!"));
    }

    @Command.Argument(name = "messagequeuetest")
    protected void messageQueueTest() {
        MessageQueue.queue("Default delay - 25 ticks");
        MessageQueue.queue("Default delay 2 - 25 ticks");
        MessageQueue.queue("Default delay 3 - 25 ticks");
        MessageQueue.queue("Custom delay - 50 ticks", 50);
    }

    @Command.Argument(name = "configframeworktest")
    protected void configFrameworkTest(String[] args) {
        if (ArrayHelper.contains(args, "load")) {
            logTestConfigBools("Load pre");
            testConfigMenu.load();
            logTestConfigBools("Load post");
        }
        if (ArrayHelper.contains(args, "save")) {
            logTestConfigBools("Save pre");
            testConfigMenu.standardBoolean = !testConfigMenu.standardBoolean;
            testConfigMenu.standardTrueBoolean = !testConfigMenu.standardTrueBoolean;
            testConfigMenu.save();
            logTestConfigBools("Save post");
        }
        if (ArrayHelper.contains(args, "open")) testConfigMenu.open();
    }

    private void logTestConfigBools(String pre) {
        System.out.println(pre + " standard boolean log: " + testConfigMenu.standardBoolean);
        System.out.println(pre + " standard true boolean log: " + testConfigMenu.standardTrueBoolean);
    }

    public static class TestConfigMenu extends ConfigMenu {

        @ConfigOption(
                name = "Standard boolean",
                category = "Bools",
                type = ConfigOptionType.SWITCH
        )
        public boolean standardBoolean;

        @ConfigOption(
                name = "Standard true boolean",
                category = "Bools",
                type = ConfigOptionType.SWITCH
        )
        public boolean standardTrueBoolean = true;

        public String title() {
            return "Test";
        }

        public TGMConfig config() {
            return $this.config;
        }

    }

}