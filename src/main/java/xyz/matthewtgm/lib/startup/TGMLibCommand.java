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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import xyz.matthewtgm.lib.TGMLib;
import xyz.matthewtgm.lib.commands.bettercommands.Command;
import xyz.matthewtgm.lib.config.ConfigMenu;
import xyz.matthewtgm.lib.config.ConfigOption;
import xyz.matthewtgm.lib.config.ConfigOptionType;
import xyz.matthewtgm.lib.gui.menus.GuiCosmeticSelector;
import xyz.matthewtgm.lib.other.GifResourceLocation;
import xyz.matthewtgm.lib.other.ScreenPosition;
import xyz.matthewtgm.lib.util.*;
import xyz.matthewtgm.lib.util.global.GlobalMinecraft;
import xyz.matthewtgm.tgmconfig.TGMConfig;

import java.io.File;

@Command(name = "tgmlib", tabCompleteOptions = {"cosmetics", "notitest1", "notitest2", "positiontest", "messagequeuetest", "threedimtexttest", "configframeworktest"})
public class TGMLibCommand {

    private final TestConfigMenu testConfigMenu = new TestConfigMenu();
    static boolean drawThreeDimText = false;
    public static boolean logPackets = false;
    public final TGMConfig config = new TGMConfig("tgmlib", new File(GlobalMinecraft.getGameDirectory(), "config"));

    @Command.Process
    private void process(String[] args) {
        if (args.length <= 0) ChatHandler.sendMessage(ChatHandler.tgmLibChatPrefix, EnumChatFormatting.RED + "This command requires arguments! Press tab with the command entered in chat to see options.");
    }

    @Command.Argument(name = "cosmetics")
    private void cosmetics() {
        System.out.println("Opening cosmetics menu...");
        GuiHelper.open(new GuiCosmeticSelector());
    }

    @Command.Argument(name = "logpackets")
    private void logPackets() {
        logPackets = !logPackets;
    }

    @Command.Argument(name = "notitest1")
    private void notificationTest1() {
        Notifications.push("Test Notification", "Test Description");
    }

    @Command.Argument(name = "notitest2")
    private void notificationTest2() {
        Notifications.push("Test Clickable Notification", "Test Clickable Description", () -> ChatHandler.sendMessage(ChatHandler.tgmLibChatPrefix, "Notification clicked!"));
    }

    @Command.Argument(name = "positiontest")
    private void positionTest() {
        GuiHelper.open(new GuiPositionTest());
    }

    @Command.Argument(name = "messagequeuetest")
    private void messageQueueTest() {
        MessageQueue.queue("Default delay - 25 ticks");
        MessageQueue.queue("Default delay 2 - 25 ticks");
        MessageQueue.queue("Default delay 3 - 25 ticks");
        MessageQueue.queue("Custom delay - 50 ticks", 50);
    }

    @Command.Argument(name = "threedimtexttest")
    private void threeDimTextTest() {
        drawThreeDimText = !drawThreeDimText;
    }

    @Command.Argument(name = "configframeworktest", tabCompleteOptions = {"load", "save", "open"})
    private void configFrameworkTest(String[] args) {
        if (ArrayHelper.contains(args, "load")) {
            logTestConfigBools("Load pre");
            testConfigMenu.load();
            logTestConfigBools("Load post");
        }
        if (ArrayHelper.contains(args, "save")) {
            logTestConfigBools("Save pre");
            testConfigMenu.standardBoolean = !testConfigMenu.standardBoolean;
            testConfigMenu.standardTrueBoolean = !testConfigMenu.standardTrueBoolean;
            testConfigMenu.standardFalseBoolean = !testConfigMenu.standardFalseBoolean;
            testConfigMenu.save();
            logTestConfigBools("Save post");
        }
        if (ArrayHelper.contains(args, "open")) testConfigMenu.open();
    }

    private void logTestConfigBools(String pre) {
        System.out.println(pre + " standard boolean log: " + testConfigMenu.standardBoolean);
        System.out.println(pre + " standard true boolean log: " + testConfigMenu.standardTrueBoolean);
        System.out.println(pre + " standard false boolean log: " + testConfigMenu.standardFalseBoolean);
    }

    public TGMConfig getConfig() {
        return config;
    }

    public static class GuiPositionTest extends GuiScreen {

        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            ScreenPosition position = new ScreenPosition(353, 530);
            EnhancedFontRenderer.drawCenteredText("Hello, World!", position.getX(), position.getY(), -1);
            super.drawScreen(mouseX, mouseY, partialTicks);
        }

    }

    public class TestConfigMenu extends ConfigMenu {

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

        @ConfigOption(
                name = "Standard false boolean",
                category = "False bools",
                type = ConfigOptionType.SWITCH
        )
        public boolean standardFalseBoolean = false;

        @ConfigOption(
                name = "Standard string",
                category = "Strings",
                type = ConfigOptionType.TEXT
        )
        public String standardString = "";

        public String title() {
            return "Test";
        }

        public TGMConfig config() {
            return getConfig();
        }

    }

}