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

package xyz.matthewtgm.tgmlib.gui.menus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import xyz.matthewtgm.tgmconfig.ConfigEntry;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.core.TGMLibManager;
import xyz.matthewtgm.tgmlib.gui.GuiTGMLibBase;
import xyz.matthewtgm.tgmlib.gui.GuiTransFadingButton;
import xyz.matthewtgm.tgmlib.util.ChatColour;

public class GuiTGMLibSettings extends GuiTGMLibBase {

    public GuiTGMLibSettings(GuiScreen parent) {
        super("Settings", parent);
    }

    public void initialize() {
        int baseX = backgroundHitBox.getIntX() + 4;
        int baseY = backgroundHitBox.getIntY() + 38;

        buttonList.add(new GuiTransFadingButton(1, baseX, baseY, backgroundHitBox.getIntWidth() - 28, 20, "Light Mode: " + (TGMLib.getManager().getConfigHandler().getLightMode().get() ? ChatColour.GREEN + "ON" : ChatColour.RED + "OFF")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) {
                    TGMLibManager manager = TGMLib.getManager();
                    manager.getConfig().add(new ConfigEntry<>("light_mode", !manager.getConfigHandler().getLightMode().get()));
                    manager.getConfig().save();
                    manager.getConfigHandler().update();
                    refresh(2);
                }
                return false;
            }
        });
        buttonList.add(new GuiTransFadingButton(2, baseX, baseY + 22, backgroundHitBox.getIntWidth() - 28, 20, "Log Data: " + (TGMLib.getManager().getDataHandler().getMayLogData().get() ? ChatColour.GREEN + "ON" : ChatColour.RED + "OFF")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) {
                    TGMLibManager manager = TGMLib.getManager();
                    manager.getData().add(new ConfigEntry<>("log_data", !manager.getDataHandler().getMayLogData().get()));
                    manager.getData().save();
                    manager.getDataHandler().update();
                    refresh(2);
                }
                return false;
            }
        });
        buttonList.add(new GuiTransFadingButton(3, baseX, baseY + 44, backgroundHitBox.getIntWidth() - 28, 20, "Show Indicators: " + (TGMLib.getManager().getConfigHandler().getShowIndicators().get() ? ChatColour.GREEN + "ON" : ChatColour.RED + "OFF")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) {
                    TGMLibManager manager = TGMLib.getManager();
                    manager.getConfig().add(new ConfigEntry<>("show_indicators", !manager.getConfigHandler().getShowIndicators().get()));
                    manager.getConfig().save();
                    manager.getConfigHandler().update();
                    refresh(2);
                }
                return false;
            }
        });
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {}

    public boolean allowRefreshing() {
        return true;
    }

}