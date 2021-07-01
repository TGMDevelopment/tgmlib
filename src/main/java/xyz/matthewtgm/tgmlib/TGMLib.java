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

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.matthewtgm.tgmlib.commands.CommandManager;
import xyz.matthewtgm.tgmlib.core.TGMLibManager;
import xyz.matthewtgm.tgmlib.keybinds.KeyBindManager;
import xyz.matthewtgm.tgmlib.tweaker.TGMLibClassTransformer;
import xyz.matthewtgm.tgmlib.util.*;

import java.io.File;
import java.util.List;

public class TGMLib {

    @Getter private static final TGMLib instance = new TGMLib();
    public static final String NAME = "@NAME@", ID = "@ID@", VER = "@VER@", TRANSFORMER = TGMLibClassTransformer.class.getName();
    @Getter private static final TGMLibManager manager = new TGMLibManager();
    @Getter private static boolean initialized = false, dev = false;
    @Getter private final Logger logger = LogManager.getLogger(NAME);

    public void initialize(File mcDir) {
        if (initialized) return;
        manager.initialize(mcDir);

        initialized = true;
        start();
    }

    private void start() {
        logger.info("Starting TGMLib...");
        ForgeHelper.registerEventListeners(
                this,
                new CommandQueue(),
                new GuiHelper(),
                new GuiEditor(),
                new HypixelHelper(),
                new KeyBindManager(),
                new MessageQueue(),
                new Notifications(),
                new ScreenHelper(),
                new TitleHandler()
        );
        manager.start();
        GuiEditor.addEdit(GuiOptions.class, new GuiEditor.GuiEditRunnable() {
            public void init(GuiScreen screen, List<GuiButton> buttonList) {
                buttonList.add(new GuiButton(234523, screen.width / 2 - 50, screen.height - 20, 100, 20, "TGMLib") {
                    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                        //if (super.mousePressed(mc, mouseX, mouseY)) mc.displayGuiScreen();
                        return false;
                    }
                });
            }

            public void draw(GuiScreen screen, int mouseX, int mouseY, float partialTicks) {}
        });
        CommandManager.register(TGMLibCommand.class);
        logger.info("TGMLib started.");
    }

}