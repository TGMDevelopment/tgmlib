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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import xyz.matthewtgm.lib.commands.CommandManager;
import xyz.matthewtgm.lib.commands.bettercommands.Command;
import xyz.matthewtgm.lib.gui.GuiTransButton;
import xyz.matthewtgm.lib.gui.betterguis.AugmentedGuiScreen;
import xyz.matthewtgm.lib.gui.betterguis.AugmentedScreenManager;
import xyz.matthewtgm.lib.gui.betterguis.elements.impl.ButtonElement;
import xyz.matthewtgm.lib.gui.betterguis.elements.impl.TextFieldElement;
import xyz.matthewtgm.lib.gui.betterguis.elements.other.ElementText;
import xyz.matthewtgm.lib.util.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StartupRegistry {

    static void init(Logger logger) {
        logger.info("Registering commands...");
        CommandManager.register(TGMLibCommand.class);
        logger.info("Commands registered!");
    }

    @Command(name = "tgmlib", tabCompleteOptions = {"notitest1", "notitest2", "messagequeuetest", "guiframeworktest"})
    public static class TGMLibCommand {

        @Command.Process
        protected void process(EntityPlayer sender, String[] args) {
            if (args.length <= 0) {
                ChatHandler.sendMessage(ChatHandler.tgmLibChatPrefix, EnumChatFormatting.RED + "This command requires arguments! Press tab with the command entered in chat to see options.");
            }
        }

        @Command.Argument(name = "guitest")
        protected void guiTest(String[] args) {
            boolean tooltip = ArrayHelper.contains(args, "tooltip");
            boolean hoverButtonChecks = ArrayHelper.contains(args, "buttonHovers");
            GuiHelper.open(new GuiScreen() {

                public void initGui() {
                    buttonList.add(new GuiTransButton(0, width / 2 - 50, height - 20, 100, 20, "Close") {
                        public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
                            if (super.mousePressed(mc, mouseX, mouseY))
                                mc.displayGuiScreen(null);
                            return false;
                        }
                    });
                    buttonList.add(new GuiTransButton(1, width / 2 - 50, height - 50, 100, 20, "Eee") {
                        public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
                            if (super.mousePressed(mc, mouseX, mouseY))
                                ChatHandler.sendMessage("Eee!");
                            return false;
                        }
                    });
                }

                public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
                    if (tooltip)
                        GuiHelper.drawTooltip(new ArrayList<>(Collections.singletonList("Hello, World!")), mouseX, mouseY);
                    if (hoverButtonChecks) {
                        System.out.println("Simple hover check:" + GuiHelper.isHoveringOverButton(buttonList));
                        System.out.println("Hover check for button with id 1:" + GuiHelper.isHoveringOverButton(buttonList, 1));
                        System.out.println("Hover check for back button: " + GuiHelper.isHoveringOverButton(buttonList, buttonList.get(0)));
                    }
                    super.drawScreen(mouseX, mouseY, partialTicks);
                }

            });
        }

        @Command.Argument(name = "notitest1")
        protected void notificationTest1(String[] args) {
            Notifications.push("Test Notification", "Test Description");
        }

        @Command.Argument(name = "notitest2")
        protected void notificationTest2(String[] args) {
            Notifications.push("Test Clickable Notification", "Test Clickable Description", () -> ChatHandler.sendMessage(ChatHandler.tgmLibChatPrefix, "Notification clicked!"));
        }

        @Command.Argument(name = "messagequeuetest")
        protected void messageQueueTest(String[] args) {
            MessageQueue.queue("Default delay - 25 ticks");
            MessageQueue.queue("Default delay 2 - 25 ticks");
            MessageQueue.queue("Default delay 3 - 25 ticks");
            MessageQueue.queue("Custom delay - 50 ticks", 50);
        }

        @Command.Argument(name = "guiframeworktest")
        protected void guiFrameworkTest(String[] args) {
            AugmentedScreenManager.open(new AugmentedGuiScreen() {

                private TextFieldElement textField;

                @Override
                public void init() {
                    elements.add(new ButtonElement(0, width / 2 - 50, height - 40, 100, 20, new ElementText("Back", false, false, false), new ButtonElement.ButtonStyle.DefaultStyle(), () -> System.out.println("Attempting to go BACK!")));
                    elements.add(new ButtonElement(1, width / 2 - 50, height - 70, 100, 20, new ElementText("Back", true, false, false), new ButtonElement.ButtonStyle.DefaultStyle(), () -> System.out.println("Attempting to go BAACK!")));
                    elements.add(new ButtonElement(2, width / 2 - 50, height - 100, 100, 20, new ElementText("Back", false, true, false), new ButtonElement.ButtonStyle.DefaultStyle(), () -> System.out.println("Attempting to go BAAACK!")));
                    elements.add(new ButtonElement(3, width / 2 - 50, height - 130, 100, 20, new ElementText("Back", true, true, false), new ButtonElement.ButtonStyle.DefaultStyle(), () -> System.out.println("Attempting to go BAAAACK!")));
                    elements.add(new ButtonElement(4, width / 2 - 50, height - 160, 100, 20, new ElementText("Back", true, true, true), new ButtonElement.ButtonStyle.DefaultStyle(), () -> System.out.println("Attempting to go BAAAAACK!")));
                }

                @Override
                public void keyTyped(char typedChar, int keyCode) {
                    super.keyTyped(typedChar, keyCode);
                    System.out.println(textField.getContent());
                }

                @Override
                public void draw(int mouseX, int mouseY, float partialTicks) {

                }

                @Override
                public boolean pausesGame() {
                    return false;
                }
            });
        }

    }

}