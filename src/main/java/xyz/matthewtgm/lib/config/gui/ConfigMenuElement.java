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

package xyz.matthewtgm.lib.config.gui;

import lombok.Getter;
import lombok.Setter;

public abstract class ConfigMenuElement {

    @Getter @Setter
    private int x;
    @Getter @Setter
    private int y;
    @Getter @Setter
    private int width;
    @Getter @Setter
    private int height;
    @Getter
    private final Runnable onClick;

    public ConfigMenuElement(int x, int y, int width, int height, Runnable onClick) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.onClick = onClick;
    }

    public abstract void render(int mouseX, int mouseY, float partialTicks);
    public abstract void mouse(int mouseX, int mouseY, int button);
    public abstract void keyboard(char typedChar, int keyCode);

    public boolean isMouseOver(int mouseX, int mouseY) {
        return (mouseX >= getX() && mouseX <= getX() + getWidth()) && (mouseY >= getY() && mouseY <= getY() + getHeight());
    }

}