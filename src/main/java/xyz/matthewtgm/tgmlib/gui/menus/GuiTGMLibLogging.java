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
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import xyz.matthewtgm.json.entities.JsonArray;
import xyz.matthewtgm.tgmconfig.ConfigEntry;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.gui.GuiTGMLibBase;
import xyz.matthewtgm.tgmlib.gui.GuiTransFadingButton;
import xyz.matthewtgm.tgmlib.socket.packets.impl.other.GameOpenPacket;
import xyz.matthewtgm.tgmlib.util.EnhancedFontRenderer;
import xyz.matthewtgm.tgmlib.util.global.GlobalMinecraft;

public class GuiTGMLibLogging extends GuiTGMLibBase {

    public GuiTGMLibLogging(GuiScreen parent) {
        super("Logging Prompt", parent);
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
        EnhancedFontRenderer.drawCenteredStyledText("Will you allow TGMLib", width / 2, height / 2 - 60, -1);
        EnhancedFontRenderer.drawCenteredStyledText("to log data such as", width / 2, height / 2 - 50, -1);
        EnhancedFontRenderer.drawCenteredStyledText("log-in/out times and", width / 2, height / 2 - 40, -1);
        EnhancedFontRenderer.drawCenteredStyledText("mods you use?", width / 2, height / 2 - 30, -1);
        EnhancedFontRenderer.drawCenteredStyledText("(denying this will stop", width / 2, height / 2 - 20, -1);
        EnhancedFontRenderer.drawCenteredStyledText("some features from working)", width / 2, height / 2 - 10, -1);
    }

    private void input(boolean value) {
        TGMLib.getManager().getData().add(new ConfigEntry<>("prompt_received", true));
        TGMLib.getManager().getData().add(new ConfigEntry<>("log_data", value));
        TGMLib.getManager().getData().save();
        TGMLib.getManager().getDataHandler().update();
        if (value)
            TGMLib.getManager().getWebSocket().send(new GameOpenPacket(GlobalMinecraft.getSession().getProfile().getId().toString()));
        mc.displayGuiScreen(getParent());
    }

}