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

package xyz.matthewtgm.lib.gui.betterguis.elements;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

public abstract class ElementBase {

    protected final Minecraft mc = Minecraft.getMinecraft();

    @Getter private final int id;
    @Getter @Setter private int x;
    @Getter @Setter private int y;
    @Getter @Setter private int width;
    @Getter @Setter private int height;

    public ElementBase(int id, int x, int y, int width, int height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void render(int mouseX, int mouseY, float partialTicks);
    public void keyTyped(char typedChar, int keyCode) {}
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
    public void mouseReleased(int mouseX, int mouseY, int state) {}

    public boolean isInBounds(int mouseX, int mouseY) {
        return ((mouseX >= x && mouseX <= x + width) && (mouseY >= y && mouseY <= y + height));
    }

    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    public void setDimensions(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

}