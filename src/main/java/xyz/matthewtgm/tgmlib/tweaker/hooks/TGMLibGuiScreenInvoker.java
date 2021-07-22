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

package xyz.matthewtgm.tgmlib.tweaker.hooks;

import net.minecraft.client.gui.GuiButton;

import java.io.IOException;

public interface TGMLibGuiScreenInvoker {
    void invokeMouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException;
    void invokeMouseReleased(int mouseX, int mouseY, int state) throws IOException;
    void invokeKeyTyped(char typedChar, int keyCode) throws IOException;
    void invokeMouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick);
    void invokeActionPerformed(GuiButton button) throws IOException;
}