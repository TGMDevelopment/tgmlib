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

package xyz.matthewtgm.lib.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import xyz.matthewtgm.lib.config.gui.ConfigMenuCategoryElement;
import xyz.matthewtgm.lib.config.gui.ConfigMenuElement;
import xyz.matthewtgm.lib.util.ExceptionHelper;
import xyz.matthewtgm.lib.util.GuiHelper;
import xyz.matthewtgm.lib.util.RenderHelper;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigScreenGenerator {

    static GuiScreen generate(ConfigMenu menu, List<ConfigMenu.ConfigOptionHolder> options) {
        AtomicReference<GuiScreen> returnValue = new AtomicReference<>(null);
        ExceptionHelper.tryCatch(() -> returnValue.set(new GuiConfigScreen(menu, options)));

        return returnValue.get();
    }

    static class GuiConfigScreen extends GuiScreen {

        private final List<ConfigMenuElement> configMenuElements = new ArrayList<>();
        private final List<String> categories = new ArrayList<>();

        public final ConfigMenu menu;
        public final List<ConfigMenu.ConfigOptionHolder> options;

        private Integer originalGuiScale;

        GuiConfigScreen(ConfigMenu menu, List<ConfigMenu.ConfigOptionHolder> options) {
            this.menu = menu;
            this.options = options;
        }

        public void setWorldAndResolution(Minecraft mc, int width, int height) {
            fixGuiScale();
            super.setWorldAndResolution(mc, width, height);
        }

        public void initGui() {
            fixGuiScale();
            configMenuElements.clear();
            for (ConfigMenu.ConfigOptionHolder optionHolder : options) if (categories.stream().noneMatch(category -> category.equalsIgnoreCase(optionHolder.option.category()))) categories.add(optionHolder.option.category());
            AtomicInteger yCategoryOffset = new AtomicInteger(0);
            for (String category : categories) if (configMenuElements.stream().noneMatch(configMenuElement -> ((ConfigMenuCategoryElement) configMenuElement).getText().equalsIgnoreCase(category))) configMenuElements.add(new ConfigMenuCategoryElement(15, yCategoryOffset.getAndAdd(22) + 12, category));
            System.out.println(options);
            System.out.println(categories);
            System.out.println(configMenuElements);
        }

        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            fixGuiScale();
            GuiHelper.drawBackground(this, 60);
            RenderHelper.drawHollowRect(10, 10, width - 20, height - 20, new Color(255, 255, 255, 255).getRGB());
            int categoryDividerX = 120;
            RenderHelper.drawRect(categoryDividerX, 10, categoryDividerX + 1, height - 10, new Color(255, 255, 255, 255).getRGB());
            GlStateManager.pushMatrix();
            int scale = 3;
            GlStateManager.scale(scale, scale, 0);
            drawCenteredString(fontRendererObj, menu.title(), (width / 2) / scale + (categoryDividerX / 2 / 2 / 2), 20 / scale, -1);
            GlStateManager.popMatrix();
            RenderHelper.drawRect(categoryDividerX, (20 / scale) * fontRendererObj.FONT_HEIGHT, width - 10, (20 / scale) * fontRendererObj.FONT_HEIGHT + 1, -1);

            for (ConfigMenuElement element : configMenuElements) if (element != null) element.render(mouseX, mouseY, partialTicks);

            super.drawScreen(mouseX, mouseY, partialTicks);
        }

        public void onGuiClosed() {
            System.out.println(originalGuiScale);
            System.out.println(mc.gameSettings.guiScale);
            mc.gameSettings.guiScale = originalGuiScale;
            originalGuiScale = null;
        }

        protected void keyTyped(char typedChar, int keyCode) throws IOException {
            for (ConfigMenuElement element : configMenuElements) if (element != null) element.keyboard(typedChar, keyCode);
            super.keyTyped(typedChar, keyCode);
        }

        protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
            for (ConfigMenuElement element : configMenuElements) {
                if (element == null) continue;
                if (element.getOnClick() != null) element.getOnClick().run();
                if (element instanceof ConfigMenuCategoryElement && element.isMouseOver(mouseX, mouseY)) {
                    configMenuElements.forEach(theElement -> {
                        ConfigMenuCategoryElement categoryElement = (ConfigMenuCategoryElement) theElement;
                        if (categoryElement.isSelected()) categoryElement.setSelected(false);
                    });
                    ((ConfigMenuCategoryElement) element).setSelected(true);
                }
                element.mouse(mouseX, mouseY, mouseButton);
            }
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }

        private void fixGuiScale() {
            Minecraft mc = Minecraft.getMinecraft();
            if (originalGuiScale == null) originalGuiScale = mc.gameSettings.guiScale;
            mc.gameSettings.guiScale = 2;
        }

    }

}