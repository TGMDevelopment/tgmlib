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
import net.minecraft.client.gui.GuiTextField;
import xyz.matthewtgm.lib.config.ConfigMenu;
import xyz.matthewtgm.lib.util.EnhancedFontRenderer;

import java.awt.*;
import java.util.Random;

public class ConfigMenuTextFieldElement extends ConfigMenuElement {

    @Getter private GuiTextField textField;
    @Getter private final String initialText;

    public ConfigMenuTextFieldElement(ConfigMenu menu, ConfigMenu.ConfigOptionHolder optionHolder, int width, int height, CharSequence initialText, Runnable onClick) {
        super(menu, optionHolder, width, height, onClick);
        this.initialText = (String) initialText;
    }

    @Override
    public void render(int xPos, int yPos, int mouseX, int mouseY, float partialTicks) {
        if (textField == null) {
            textField = new GuiTextField(new Random().nextInt(), EnhancedFontRenderer.getFontRenderer(), xPos, yPos, getWidth() - xPos, getHeight());
            textField.setEnableBackgroundDrawing(false);
            textField.setCanLoseFocus(true);
        }
        if (initialText != null && !initialText.isEmpty()) textField.setText(initialText);
        textField.drawTextBox();
    }

    @Override
    public void mouse(int mouseX, int mouseY, int button) {
        if (textField != null) textField.setFocused(isMouseOver(mouseX, mouseY));
    }

    @Override
    public void keyboard(char typedChar, int keyCode) {
        if (textField != null) {
            textField.textboxKeyTyped(typedChar, keyCode);
            try {
                getOptionHolder().field.set(getOptionHolder().instance, textField.getText());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}