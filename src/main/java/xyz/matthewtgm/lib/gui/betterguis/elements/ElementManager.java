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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ElementManager {

    @Getter private final List<ElementBase> elements = new ArrayList<>();

    public void add(ElementBase element) {
        elements.add(element);
    }

    public void add(ElementBase... elements) {
        this.elements.addAll(Arrays.asList(elements));
    }

    public void clear() {
        elements.clear();
    }

    public Optional<ElementBase> get(int id) {
        return elements.stream().filter(element -> element.getId() == id).findAny();
    }

    public ElementBase getFirst() {
        return elements.get(0);
    }

    public ElementBase getLast() {
        return elements.get(elements.size() - 1);
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        for (ElementBase element : elements)
            element.render(mouseX, mouseY, partialTicks);
    }

    public void keyTyped(char typedChar, int keyCode) {
        for (ElementBase element : elements)
            element.keyTyped(typedChar, keyCode);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (ElementBase element : elements)
            element.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (ElementBase element : elements)
            element.mouseReleased(mouseX, mouseY, state);
    }

}