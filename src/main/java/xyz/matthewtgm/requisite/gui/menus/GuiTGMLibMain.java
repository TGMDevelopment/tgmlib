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

package xyz.matthewtgm.requisite.gui.menus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.players.cosmetics.CosmeticManager;
import xyz.matthewtgm.requisite.gui.GuiTGMLibBase;
import xyz.matthewtgm.requisite.gui.GuiTransFadingImageButton;
import xyz.matthewtgm.requisite.util.ResourceHelper;

import java.awt.*;

public class GuiTGMLibMain extends GuiTGMLibBase {

    public GuiTGMLibMain(GuiScreen parent) {
        super("TGMLib", new Color(255, 175, 0).getRGB(), parent);
    }

    public GuiTGMLibMain() {
        this(null);
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

        if (CosmeticManager.isLoaded() && Requisite.getManager().getWebSocket().isOpen()) {
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