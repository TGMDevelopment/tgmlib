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
import net.minecraft.util.EnumChatFormatting;
import xyz.matthewtgm.lib.config.gui.ConfigMenuElement;
import xyz.matthewtgm.lib.config.gui.ConfigMenuTextFieldElement;
import xyz.matthewtgm.lib.config.gui.ConfigMenuToggleElement;
import xyz.matthewtgm.lib.util.*;

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
        private final List<ConfigCategory> categories = new ArrayList<>();

        public final ConfigMenu menu;
        public final List<ConfigMenu.ConfigOptionHolder> options;

        private String selectedCategory;

        GuiConfigScreen(ConfigMenu menu, List<ConfigMenu.ConfigOptionHolder> options) {
            this.menu = menu;
            this.options = options;
        }

        public void initGui() {
            categories.clear();
            configMenuElements.clear();

            CustomScaledResolution res = new CustomScaledResolution(Minecraft.getMinecraft(), 2);
            int width = res.getScaledWidth();
            @SuppressWarnings("unused") int height = res.getScaledHeight();

            AtomicInteger yCategoryOffset = new AtomicInteger(0);
            int yOff = 0;
            for (ConfigMenu.ConfigOptionHolder optionHolder : options) {
                /* Categories. */
                ConfigCategory category = new ConfigCategory(optionHolder.option.category(), 2, yOff + 7);
                if (!categories.contains(category)) {
                    categories.add(category);
                    yOff = yCategoryOffset.getAndAdd(EnhancedFontRenderer.getFontHeight() + 2);
                }

                /* Elements. */
                System.out.println(optionHolder.field.getType().getSimpleName());
                if (configMenuElements.stream().noneMatch(element -> element != null && element.getOptionHolder() != null && element.getOptionHolder() == optionHolder)) {
                    switch (optionHolder.field.getType().getSimpleName()) {
                        case "Boolean":
                        case "boolean":
                            try {
                                configMenuElements.add(new ConfigMenuToggleElement(menu, optionHolder, (boolean) optionHolder.field.get(optionHolder.instance), width - 20, 20, () -> {}));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case "String":
                        case "CharSequence":
                            switch (optionHolder.option.type()) {
                                case TEXT:
                                    try {
                                        configMenuElements.add(new ConfigMenuTextFieldElement(menu, optionHolder, width - 20, 20, (CharSequence) optionHolder.field.get(optionHolder.instance), () -> {}));
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                //case DROPDOWN:

                                    //break;
                                default:
                                    throw new IllegalStateException("The option type needs to be either TEXT or DROPDOWN.");
                            }
                            break;
                        case "Integer":
                        case "int":
                        case "Float":
                        case "float":
                        case "Double":
                        case "double":
                        case "Byte":
                        case "byte":
                            switch (optionHolder.option.type()) {
                                //case DROPDOWN:
                                    //break;
                                case TEXT:
                                    break;
                            }
                        case "Color":
                        case "ColourRGB":
                            break;
                    }
                }
            }
            System.out.println(configMenuElements);
            System.out.println(categories);
            ConfigCategory firstCategory = null;
            for (ConfigCategory category : categories) {
                if (category != null) {
                    firstCategory = category;
                    break;
                }
            }
            if (!categories.isEmpty() && firstCategory != null) selectedCategory = firstCategory.category;
        }

        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            GuiHelper.forceGuiScale(this, 2);
            GuiHelper.drawBackground(this, 60);
            RenderHelper.drawHollowRect(10, 10, width - 20, height - 20, new Color(255, 255, 255, 255).getRGB());
            int categoryDividerX = 150;
            RenderHelper.drawRect(categoryDividerX, 10, categoryDividerX + 1, height - 10, new Color(255, 255, 255, 255).getRGB());
            GlStateManager.pushMatrix();
            int titleScale = 3;
            GlStateManager.scale(titleScale, titleScale, 0);
            drawCenteredString(fontRendererObj, menu.title(), (width / 2) / titleScale + (categoryDividerX / 2 / 2 / 2), 20 / titleScale, -1);
            GlStateManager.popMatrix();
            RenderHelper.drawRect(categoryDividerX, (20 / titleScale) * fontRendererObj.FONT_HEIGHT, width - 10, (20 / titleScale) * fontRendererObj.FONT_HEIGHT + 1, -1);

            AtomicInteger yElementOffset = new AtomicInteger(0);
            for (ConfigMenuElement element : configMenuElements) if (element != null && isApplicableToCategory(element)) element.onRender(categoryDividerX + 2, yElementOffset.getAndAdd(fontRendererObj.FONT_HEIGHT + 15) + 13, mouseX, mouseY, partialTicks);

            GlStateManager.pushMatrix();
            int categoryScale = 2;
            GlStateManager.scale(categoryScale, categoryScale, 0);
            for (ConfigCategory category : categories) if (category != null) category.draw(selectedCategory);
            GlStateManager.popMatrix();

            super.drawScreen(mouseX, mouseY, partialTicks);
        }

        @Override
        public void onGuiClosed() {
            menu.save();
        }

        protected void keyTyped(char typedChar, int keyCode) throws IOException {
            for (ConfigMenuElement element : configMenuElements) if (element != null && isApplicableToCategory(element)) element.keyboard(typedChar, keyCode);
            super.keyTyped(typedChar, keyCode);
        }

        protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
            for (ConfigMenuElement element : configMenuElements) {
                if (element == null) continue;
                if (element.getOnClick() != null) element.getOnClick().run();
                if (!isApplicableToCategory(element)) continue;
                element.mouse(mouseX, mouseY, mouseButton);
            }
            for (ConfigCategory category : categories) if (category != null && category.inBounds(mouseX, mouseY)) selectedCategory = category.category;
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }

        private boolean isApplicableToCategory(ConfigMenuElement element) {
            return element.getOptionHolder().option.category().equals(selectedCategory);
        }

        private static class ConfigCategory {
            public final String category;
            public final int scale;
            public final int yPos;
            public final int xPos;
            public int height = EnhancedFontRenderer.getFontHeight();
            public int width;
            public ConfigCategory(String category, int scale, int yPos) {
                this.category = category;
                this.scale = scale;
                this.xPos = 6;
                this.yPos = yPos;
            }
            public void draw(String selected) {
                String toDraw = category;
                if (category.equalsIgnoreCase(selected)) toDraw = EnumChatFormatting.UNDERLINE + category;
                width = EnhancedFontRenderer.getWidth(toDraw);
                EnhancedFontRenderer.drawText(toDraw, xPos, yPos, -1, true);
            }
            public boolean inBounds(int mouseX, int mouseY) {
                return (mouseX / scale >= xPos / scale && mouseX / scale <= xPos / scale + width) && (mouseY / scale >= yPos && mouseY / scale <= yPos + height);
            }
            public String toString() {
                return "ConfigCategory{" + "category='" + category + '\'' + ", scale=" + scale + ", yPos=" + yPos + ", xPos=" + xPos + ", height=" + height + ", width=" + width + '}';
            }
        }

    }

}