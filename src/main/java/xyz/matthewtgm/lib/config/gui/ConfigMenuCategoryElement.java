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
import xyz.matthewtgm.lib.util.EnhancedFontRenderer;
import xyz.matthewtgm.lib.util.RenderHelper;

import java.awt.*;

public class ConfigMenuCategoryElement extends ConfigMenuElement {

    @Getter @Setter
    private String text;

    @Getter @Setter
    private boolean selected;

    public ConfigMenuCategoryElement(int x, int y, String text) {
        super(x, y, 100, 20, null);
        this.text = text;
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        Color backgroundCol;
        int textCol;
        if (isMouseOver(mouseX, mouseY) || selected) backgroundCol = new Color(40, 40, 40, 100);
        else backgroundCol = new Color(0, 0, 0, 0);
        if (isMouseOver(mouseX, mouseY) || selected) textCol = new Color(210, 203, 0, 255).getRGB();
        else textCol = -1;
        RenderHelper.drawRoundedRect(getX(), getY(), getWidth(), getHeight(), 3, backgroundCol);
        EnhancedFontRenderer.drawCenteredText(text, getX() + getWidth() / 2, getY() + (getHeight() - 8) / 2, textCol);
    }

    public void mouse(int mouseX, int mouseY, int button) {}

    public void keyboard(char typedChar, int keyCode) {}

}