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

package xyz.matthewtgm.lib.gui.betterguis;

import com.google.common.collect.Lists;
import xyz.matthewtgm.lib.gui.betterguis.elements.ElementManager;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.util.List;

public abstract class AugmentedGuiScreen {

    protected final Minecraft mc = Minecraft.getMinecraft();
    @Getter protected final ElementManager elements = new ElementManager();
    @Getter protected final List<GuiButton> buttonList = Lists.newArrayList();

    protected int width;
    protected int height;

    public abstract void init();
    public void keyTyped(char typedChar, int keyCode) {}
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
    public void mouseReleased(int mouseX, int mouseY, int state) {}
    public void mouseDragged(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {}
    public abstract void draw(int mouseX, int mouseY, float partialTicks);
    public abstract boolean pausesGame();

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

}