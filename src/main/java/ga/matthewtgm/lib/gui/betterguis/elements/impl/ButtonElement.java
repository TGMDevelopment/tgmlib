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

package ga.matthewtgm.lib.gui.betterguis.elements.impl;

import ga.matthewtgm.lib.gui.betterguis.elements.ElementBase;
import ga.matthewtgm.lib.gui.betterguis.elements.other.ElementText;
import ga.matthewtgm.lib.other.ColourRGB;
import ga.matthewtgm.lib.util.RenderHelper;
import lombok.Getter;
import lombok.Setter;

public class ButtonElement extends ElementBase {

    @Getter @Setter private ElementText text;
    @Getter @Setter private ButtonStyle style;
    @Getter @Setter private Runnable onClick;

    @Getter private boolean hovered;
    private final ColourRGB originalTextColour;

    public ButtonElement(int id, int x, int y, int width, int height, ElementText text, ButtonStyle style, Runnable onClick) {
        super(id, x, y, width, height);
        this.text = text;
        this.style = style;
        this.onClick = onClick;

        this.originalTextColour = text.getColour();
    }

    public ButtonElement(int id, int x, int y, ElementText text, ButtonStyle style, Runnable onClick) {
        this(id, x, y, 200, 20, text, style, onClick);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        hovered = ((mouseX >= getX() && mouseX <= getX() + getWidth()) && (mouseY >= getY() && mouseY <= getY() + getHeight()));
        text.setColour(hovered ? style.getTextHoveredColour() : originalTextColour);
        RenderHelper.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), hovered ? style.getHoveredBackgroundColour().getRGBA() : style.getBackgroundColour().getRGBA());
        text.render(getX() + getWidth() / 2, getY() + (getHeight() - 8) / 2, true);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isInBounds(mouseX, mouseY) && onClick != null)
            onClick.run();
    }

    public static class ButtonStyle {
        @Getter @Setter private ColourRGB backgroundColour;
        @Getter @Setter private ColourRGB textHoveredColour;
        @Getter @Setter private ColourRGB hoveredBackgroundColour;
        public ButtonStyle(ColourRGB backgroundColour, ColourRGB textHoveredColour, ColourRGB hoveredBackgroundColour) {
            this.backgroundColour = backgroundColour;
            this.textHoveredColour = textHoveredColour;
            this.hoveredBackgroundColour = hoveredBackgroundColour;
        }
        public static class DefaultStyle extends ButtonStyle {
            public DefaultStyle() {
                super(new ColourRGB(0, 0, 0, 80), new ColourRGB(255, 255, 160, 255), new ColourRGB(0, 0, 0, 100));
            }
        }
    }

}