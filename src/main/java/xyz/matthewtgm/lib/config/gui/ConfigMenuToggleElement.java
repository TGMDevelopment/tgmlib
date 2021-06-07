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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import xyz.matthewtgm.lib.config.ConfigMenu;
import xyz.matthewtgm.lib.util.EnhancedFontRenderer;
import xyz.matthewtgm.lib.util.ResourceCaching;

public class ConfigMenuToggleElement extends ConfigMenuElement {

    @Getter
    private boolean bool;

    public ConfigMenuToggleElement(ConfigMenu menu, ConfigMenu.ConfigOptionHolder optionHolder, boolean bool, int width, int height, Runnable onClick) {
        super(menu, optionHolder, width, height, onClick);
        this.bool = bool;
    }

    public void render(final int xPos, final int yPos, final int mouseX, final int mouseY, final float partialTicks) {
        EnhancedFontRenderer.drawText(getOptionHolder().option.name(), xPos, yPos + (getHeight() - 8) / 2, -1, true);
        GlStateManager.color(1f, 1f, 1f);

        if (bool) Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceCaching.getFromCache("TGMLib", "switch_on.png"));
        else Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceCaching.getFromCache("TGMLib", "switch_off.png"));
        int switchWidth = 32;
        Gui.drawScaledCustomSizeModalRect(xPos + getWidth() - switchWidth * 6 + 15, yPos, 0, 0, 64, 32, switchWidth, getHeight(), 64, 32);
    }

    public void mouse(final int mouseX, final int mouseY, final int button) {
        if (isMouseOver(mouseX, mouseY)) {
            System.out.println("Modifying " + getOptionHolder().field.getName());
            try {
                getOptionHolder().field.set(getOptionHolder().instance, !bool);
                bool = (boolean) getOptionHolder().field.get(getOptionHolder().instance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void keyboard(final char typedChar, final int keyCode) {}

}