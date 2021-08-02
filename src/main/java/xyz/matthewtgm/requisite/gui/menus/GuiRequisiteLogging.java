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
import xyz.matthewtgm.requisite.gui.GuiRequisiteBase;
import xyz.matthewtgm.requisite.gui.GuiTransFadingButton;
import xyz.matthewtgm.requisite.networking.packets.impl.other.GameOpenPacket;
import xyz.matthewtgm.requisite.util.ChatColour;
import xyz.matthewtgm.requisite.util.EnhancedFontRenderer;
import xyz.matthewtgm.requisite.util.global.GlobalMinecraft;

public class GuiRequisiteLogging extends GuiRequisiteBase {

    public GuiRequisiteLogging(GuiScreen parent) {
        super(Requisite.NAME + " - Logging Prompt", parent);
    }

    public void initialize() {
        buttonList.clear();
        buttonList.add(new GuiTransFadingButton(1, width / 2 - 50, height / 2 + 2, 100, 20, "Yes") {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY))
                    input(true);
                return false;
            }
        });
        buttonList.add(new GuiTransFadingButton(1, width / 2 - 50, height / 2 + 27, 100, 20, "No") {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY))
                    input(false);
                return false;
            }
        });
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        EnhancedFontRenderer.drawCenteredStyledText("Will you allow " + Requisite.NAME, width / 2, height / 2 - 60, -1);
        EnhancedFontRenderer.drawCenteredStyledText("to log data such as", width / 2, height / 2 - 50, -1);
        EnhancedFontRenderer.drawCenteredStyledText("when you log-in/out?", width / 2, height / 2 - 40, -1);
        EnhancedFontRenderer.drawCenteredStyledText(ChatColour.RED + "(denying this will stop", width / 2, height / 2 - 30, -1);
        EnhancedFontRenderer.drawCenteredStyledText(ChatColour.RED + "some features from working)", width / 2, height / 2 - 20, -1);
    }

    private void input(boolean value) {
        Requisite.getManager().getDataHandler().setReceivedPrompt(true);
        Requisite.getManager().getDataHandler().setMayLogData(value);
        Requisite.getManager().getDataHandler().update();
        if (value)
            Requisite.getManager().getWebSocket().send(new GameOpenPacket(GlobalMinecraft.getSession().getProfile().getId().toString()));
        mc.displayGuiScreen(getParent());
    }

}