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
import org.lwjgl.input.Keyboard;
import xyz.matthewtgm.json.JsonVersion;
import xyz.matthewtgm.tgmconfig.ConfigVersion;
import xyz.matthewtgm.tgmlib.commands.CommandManager;
import xyz.matthewtgm.tgmlib.core.TGMLibManager;
import xyz.matthewtgm.tgmlib.gui.menus.GuiTGMLibMain;
import xyz.matthewtgm.tgmlib.keybinds.KeyBind;
import xyz.matthewtgm.tgmlib.keybinds.KeyBindManager;
import xyz.matthewtgm.tgmlib.tweaker.TGMLibClassTransformer;
import xyz.matthewtgm.tgmlib.util.*;

import java.io.File;
import java.util.List;

public final class TGMLib {

    public static final String NAME = "@NAME@", ID = "@ID@", VER = "@VER@", TRANSFORMER = TGMLibClassTransformer.class.getName();
    private static TGMLib instance;
    @Getter private static TGMLibManager manager;
    @Getter private static boolean initialized = false;
    @Getter private final Logger logger = LogManager.getLogger(NAME);

    public void initialize(File mcDir) {
        if (manager == null)
            manager = new TGMLibManager();
        if (initialized)
            return;
        manager.initialize(mcDir);
        initialized = true;
        start();
    }

    private void start() {
        logger.info("Starting TGMLib...");
        if (!JsonVersion.CURRENT.isAtLeast(2, 4, 1))
            throw new IllegalStateException("JsonTGM is outdated! (minimum version is 2.4.1)");
        if (!ConfigVersion.CURRENT.isAtLeast(3, 1, 0))
            throw new IllegalStateException("TGMConfig is outdated! (minimum version is 3.1.0)");
        ForgeHelper.registerEventListeners(
                this,
                new CommandQueue(),
                new GuiHelper(),
                new GuiEditor(),
                new HypixelHelper(),
                new MessageQueue(),
                new Notifications(),
                new PlayerHelper(),
                new ScreenHelper(),
                new TitleHandler(),
                new TGMLibBetterEventsListener()
        );
        manager.start();
        GuiEditor.addEdit(GuiOptions.class, new GuiEditor.GuiEditRunnable() {
            public void init(GuiScreen screen, List<GuiButton> buttonList) {
                buttonList.add(new GuiButton(234523, screen.width / 2 - 50, screen.height - 24, 100, 20, "TGMLib") {
                    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                        if (super.mousePressed(mc, mouseX, mouseY))
                            mc.displayGuiScreen(new GuiTGMLibMain(screen));
                        return false;
                    }
                });
            }
            public void draw(GuiScreen screen, int mouseX, int mouseY, float partialTicks) {}
        });
        CommandManager.register(TGMLibCommand.class);
        KeyBindManager.register(new KeyBind(Keyboard.KEY_H) {
            public String name() {
                return "TGMLib";
            }
            public String category() {
                return "TGMLib";
            }
            public void pressed() {
                GuiHelper.open(new GuiTGMLibMain(null));
            }
            public void held() {}
            public void released() {}
        });
        logger.info("TGMLib started.");
    }

    public static TGMLib getInstance() {
        if (instance == null)
            instance = new TGMLib();
        return instance;
    }

}