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

package ga.matthewtgm.lib.gui.betterguis.elements.other;

import ga.matthewtgm.lib.other.ColourRGB;
import ga.matthewtgm.lib.util.EnhancedFontRenderer;
import lombok.Getter;
import lombok.Setter;

public class ElementText {

    private final ColourRGB defaultColour = new ColourRGB(255, 255, 255, 255);

    @Getter @Setter private ColourRGB colour;
    @Getter @Setter private boolean styled;
    @Getter @Setter private boolean chroma;
    @Getter @Setter private boolean textShadow;
    @Getter @Setter private String text;

    public ElementText(String text, boolean textShadow) {
        this.text = text;
        this.colour = defaultColour;
        this.styled = false;
        this.chroma = false;
        this.textShadow = textShadow;
    }

    public ElementText(String text, ColourRGB colour, boolean textShadow) {
        this.text = text;
        this.colour = colour;
        this.styled = false;
        this.chroma = false;
        this.textShadow = textShadow;
    }

    public ElementText(String text, ColourRGB colour, boolean styled, boolean textShadow) {
        this.text = text;
        this.colour = colour;
        this.chroma = false;
        this.styled = styled;
        this.textShadow = textShadow;
    }

    public ElementText(String text, boolean chroma, boolean styled, boolean textShadow) {
        this.text = text;
        this.colour = defaultColour;
        this.chroma = chroma;
        this.styled = styled;
        this.textShadow = textShadow;
    }

    public void render(int x, int y, boolean centered) {
        if (!styled && !chroma)
            if (centered) EnhancedFontRenderer.drawCenteredText(text, x, y, colour.getRGBA(), textShadow);
            else EnhancedFontRenderer.drawText(text, x, y, colour.getRGBA(), textShadow);
        if (styled && !chroma)
            if (centered) EnhancedFontRenderer.drawCenteredStyledText(text, x, y, colour.getRGBA());
            else EnhancedFontRenderer.drawStyledText(text, x, y, colour.getRGBA());
        if (!styled && chroma)
            if  (centered) EnhancedFontRenderer.drawCenteredChromaText(text, x, y, textShadow);
            else EnhancedFontRenderer.drawChromaText(text, x, y, textShadow);
        if (styled && chroma)
            if (centered) EnhancedFontRenderer.drawCenteredStyledChromaText(text, x, y);
            else EnhancedFontRenderer.drawStyledChromaText(text, x, y);
    }

}