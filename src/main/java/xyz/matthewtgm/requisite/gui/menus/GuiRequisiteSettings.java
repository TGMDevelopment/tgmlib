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
import xyz.matthewtgm.requisite.core.RequisiteManager;
import xyz.matthewtgm.requisite.gui.GuiRequisiteBase;
import xyz.matthewtgm.requisite.gui.GuiTransFadingButton;
import xyz.matthewtgm.requisite.util.ChatColour;

public class GuiRequisiteSettings extends GuiRequisiteBase {

    public GuiRequisiteSettings(GuiScreen parent) {
        super(Requisite.NAME + " - Settings", parent);
    }

    public void initialize() {
        int baseX = backgroundHitBox.getIntX() + 4;
        int baseY = backgroundHitBox.getIntY() + 38;

        buttonList.add(new GuiTransFadingButton(1, baseX, baseY, backgroundHitBox.getIntWidth() - 6, 20, "Light Mode: " + (Requisite.getManager().getConfigHandler().isLightMode() ? ChatColour.GREEN + "ON" : ChatColour.RED + "OFF")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) {
                    RequisiteManager manager = Requisite.getManager();
                    manager.getConfigHandler().setLightMode(!manager.getConfigHandler().isLightMode());
                    manager.getConfigHandler().update();
                    refresh(2);
                }
                return false;
            }
        });
        buttonList.add(new GuiTransFadingButton(2, baseX, baseY + 22, backgroundHitBox.getIntWidth() - 6, 20, "Log Data: " + (Requisite.getManager().getDataHandler().isMayLogData() ? ChatColour.GREEN + "ON" : ChatColour.RED + "OFF")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) {
                    RequisiteManager manager = Requisite.getManager();
                    manager.getDataHandler().setMayLogData(!manager.getDataHandler().isMayLogData());
                    manager.getDataHandler().update();
                    refresh(2);
                }
                return false;
            }
        });
        buttonList.add(new GuiTransFadingButton(3, baseX, baseY + 44, backgroundHitBox.getIntWidth() - 6, 20, "Show Indicators: " + (Requisite.getManager().getConfigHandler().isShowIndicators() ? ChatColour.GREEN + "ON" : ChatColour.RED + "OFF")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) {
                    RequisiteManager manager = Requisite.getManager();
                    manager.getConfigHandler().setShowIndicators(!manager.getConfigHandler().isShowIndicators());
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