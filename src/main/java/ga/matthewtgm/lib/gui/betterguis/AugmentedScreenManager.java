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

package ga.matthewtgm.lib.gui.betterguis;

import ga.matthewtgm.lib.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class AugmentedScreenManager {

    public static void open(AugmentedGuiScreen screen) {
        if (screen == null)
            GuiHelper.open(null);
        else
            GuiHelper.open(convert(screen));
    }

    public static GuiScreen convert(AugmentedGuiScreen screen) {
        if (screen == null)
            return null;
        return new GuiScreen() {

            @Override
            public void setWorldAndResolution(Minecraft mc, int width, int height) {
                screen.setSize(width, height);
                super.setWorldAndResolution(mc, width, height);
            }

            @Override
            public void setGuiSize(int w, int h) {
                screen.setSize(w, h);
                super.setGuiSize(w, h);
            }

            @Override
            public void initGui() {
                screen.getElements().clear();
                buttonList.clear();
                screen.init();
                buttonList.addAll(screen.getButtonList());
            }

            @Override
            protected void keyTyped(char typedChar, int keyCode) throws IOException {
                screen.keyTyped(typedChar, keyCode);
                screen.getElements().keyTyped(typedChar, keyCode);
                super.keyTyped(typedChar, keyCode);
            }

            @Override
            protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
                screen.mouseClicked(mouseX, mouseY, mouseButton);
                screen.getElements().mouseClicked(mouseX, mouseY, mouseButton);
                super.mouseClicked(mouseX, mouseY, mouseButton);
            }

            @Override
            protected void mouseReleased(int mouseX, int mouseY, int state) {
                screen.mouseReleased(mouseX, mouseY, state);
                screen.getElements().mouseReleased(mouseX, mouseY, state);
                super.mouseReleased(mouseX, mouseY, state);
            }

            @Override
            protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
                screen.mouseDragged(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
                super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
            }

            @Override
            public void drawScreen(int mouseX, int mouseY, float partialTicks) {
                screen.draw(mouseX, mouseY, partialTicks);
                screen.getElements().draw(mouseX, mouseY, partialTicks);
                super.drawScreen(mouseX, mouseY, partialTicks);
            }

            @Override
            public boolean doesGuiPauseGame() {
                return screen.pausesGame();
            }

        };
    }

}