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
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.players.cosmetics.CosmeticManager;
import xyz.matthewtgm.tgmlib.gui.GuiTGMLibBase;
import xyz.matthewtgm.tgmlib.gui.GuiTransFadingImageButton;
import xyz.matthewtgm.tgmlib.util.ResourceHelper;

import java.awt.*;

public class GuiTGMLibMain extends GuiTGMLibBase {

    public GuiTGMLibMain(GuiScreen parent) {
        super("TGMLib", new Color(255, 175, 0).getRGB(), parent);
    }

    public void initialize() {
        buttonList.add(new GuiTransFadingImageButton(1, width / 2 - 52, height / 2 - 10, 50, 50, ResourceHelper.get("tgmlib", "gui/icons/keybinds_icon.png")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY))
                    mc.displayGuiScreen(new GuiTGMLibKeyBinds(GuiTGMLibMain.this));
                return false;
            }
        });
        buttonList.add(new GuiTransFadingImageButton(2, width / 2 + 2, height / 2 - 10, 50, 50, ResourceHelper.get("tgmlib", "gui/icons/settings_icon.png")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY))
                    mc.displayGuiScreen(new GuiTGMLibSettings(GuiTGMLibMain.this));
                return false;
            }
        });

        if (CosmeticManager.isLoaded() && TGMLib.getManager().getWebSocket().isOpen()) {
            buttonList.add(new GuiTransFadingImageButton(3, width / 2 - 25, height / 2 - 63, 50, 50, ResourceHelper.get("tgmlib", "gui/icons/cosmetics_icon.png")) {
                public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                    if (super.mousePressed(mc, mouseX, mouseY))
                        mc.displayGuiScreen(new GuiTGMLibCosmetics(GuiTGMLibMain.this));
                    return false;
                }
            });
        }
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {}

}