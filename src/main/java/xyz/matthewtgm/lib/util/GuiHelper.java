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

package xyz.matthewtgm.lib.util;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Used to enhance bits of code relating to the Minecraft {@link GuiScreen}.
 */
public class GuiHelper {

    private static GuiScreen toOpen;

    public static void fixDisplayString(GuiButton button, String display) {
        if (!button.displayString.equals(display)) button.displayString = display;
    }

    public static boolean isHoveringOverButton(List<GuiButton> buttonList) {
        int mouseX = MouseHelper.getMouseX();
        int mouseY = MouseHelper.getMouseY();
        return buttonList.stream().anyMatch(btn -> (mouseX >= btn.xPosition && mouseX <= btn.xPosition + btn.width) && (mouseY >= btn.yPosition && mouseY <= btn.yPosition + btn.height));
    }

    public static boolean isHoveringOverButton(List<GuiButton> buttonList, int id) {
        int mouseX = MouseHelper.getMouseX();
        int mouseY = MouseHelper.getMouseY();
        return buttonList.stream().anyMatch(btn -> (mouseX >= btn.xPosition && mouseX <= btn.xPosition + btn.width) && (mouseY >= btn.yPosition && mouseY <= btn.yPosition + btn.height) && btn.id == id);
    }

    public static boolean isHoveringOverButton(List<GuiButton> buttonList, GuiButton button) {
        int mouseX = MouseHelper.getMouseX();
        int mouseY = MouseHelper.getMouseY();
        return buttonList.stream().anyMatch(btn -> (mouseX >= btn.xPosition && mouseX <= btn.xPosition + btn.width) && (mouseY >= btn.yPosition && mouseY <= btn.yPosition + btn.height) && btn == button);
    }

    public static void drawTooltip(List<String> textLines, int x, int y) {
        net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(textLines, x, y, ScreenHelper.getScaledWidth(), ScreenHelper.getScaledHeight(), -1, Minecraft.getMinecraft().fontRendererObj);
    }

    public static void forceGuiScale(GuiScreen screen, int scale) {
        CustomScaledResolution res = new CustomScaledResolution(Minecraft.getMinecraft(), scale);
        ScreenHelper.updateOrtho(res);
        int scaledWidth = res.getScaledWidth();
        int scaledHeight = res.getScaledHeight();
        if (screen.width != scaledWidth) screen.width = scaledWidth;
        if (screen.height != scaledHeight) screen.height = scaledHeight;
    }

    public static void forceGuiScale(GuiScreen screen, GuiScale scale) {
        forceGuiScale(screen, scale.getScale());
    }

    /**
     * Opens a {@link GuiScreen}. (will be most commonly used in commands.)
     *
     * @param screen the screen to open.
     */
    public static void open(GuiScreen screen) {
        toOpen = screen;
    }

    public static void drawBackground(GuiScreen screen, int alpha) {
        if (Minecraft.getMinecraft().theWorld == null) {
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.optionsBackground);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            float f = 32.0F;
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos(0.0D, screen.height, 0.0D).tex(0.0D, ((float)screen.height / 32.0F + (float)0)).color(64, 64, 64, 255).endVertex();
            worldrenderer.pos(screen.width, screen.height, 0.0D).tex(((float)screen.width / 32.0F), ((float)screen.height / 32.0F + (float)0)).color(64, 64, 64, 255).endVertex();
            worldrenderer.pos(screen.width, 0.0D, 0.0D).tex(((float)screen.width / 32.0F), 0).color(64, 64, 64, 255).endVertex();
            worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0).color(64, 64, 64, 255).endVertex();
            tessellator.draw();
        } else Gui.drawRect(0, 0, screen.width, screen.height, new Color(0, 0, 0, alpha).getRGB());
    }

    @SubscribeEvent
    protected void onTick(TickEvent event) {
        if (toOpen != null) {
            Minecraft.getMinecraft().displayGuiScreen(toOpen);
            toOpen = null;
        }
    }

    public static class Editor {

        @Getter private static final Map<Class<? extends GuiScreen>, List<GuiEditRunnable>> editMap = new ConcurrentHashMap<>();

        public static void addEdit(Class<? extends GuiScreen> screenClz, GuiEditRunnable edit) {
            if (screenClz == null) return;
            if (edit == null) return;
            editMap.putIfAbsent(screenClz, new ArrayList<>());
            editMap.get(screenClz).add(edit);
        }

        public static void addEdits(Class<? extends GuiScreen> screenClz, GuiEditRunnable... edits) {
            if (screenClz == null) return;
            if (edits == null) return;
            for (GuiEditRunnable runnable : edits)
                addEdit(screenClz, runnable);
        }

        public static void removeEdit(Class<? extends GuiScreen> screenClz, GuiEditRunnable edit) {
            if (screenClz == null) return;
            if (edit == null) return;
            if (editMap.containsKey(screenClz)) {
                List<GuiEditRunnable> edits = editMap.get(screenClz);
                if (edits != null && !edits.isEmpty())
                    edits.remove(edit);
            }
        }

        public static void removeEdits(Class<? extends GuiScreen> screenClz, GuiEditRunnable... edits) {
            if (screenClz == null) return;
            if (edits == null) return;
            for (GuiEditRunnable runnable : edits)
                removeEdit(screenClz, runnable);
        }

        @SubscribeEvent
        protected void onGuiInit(GuiScreenEvent.InitGuiEvent event) {
            List<GuiEditRunnable> edits = editMap.get(event.gui.getClass());
            if (edits != null && !edits.isEmpty())
                for (GuiEditRunnable runnable : edits)
                    runnable.init(event.gui, event.buttonList);
        }

        @SubscribeEvent
        protected void onGuiActionPerformed(GuiScreenEvent.ActionPerformedEvent event) {
            List<GuiEditRunnable> edits = editMap.get(event.gui.getClass());
            if (edits != null && !edits.isEmpty())
                for (GuiEditRunnable runnable : edits)
                    runnable.actionPerformed(event.gui, event.buttonList, event.button);
        }

        @SubscribeEvent
        protected void onGuiDrawn(GuiScreenEvent.DrawScreenEvent.Post event) {
            List<GuiEditRunnable> edits = editMap.get(event.gui.getClass());
            if (edits != null && !edits.isEmpty())
                for (GuiEditRunnable runnable : edits)
                    runnable.draw(event.gui, event.mouseX, event.mouseY, event.renderPartialTicks);
        }

        @SubscribeEvent
        protected void onGuiInit(GuiScreenEvent.KeyboardInputEvent event) {
            List<GuiEditRunnable> edits = editMap.get(event.gui.getClass());
            if (edits != null && !edits.isEmpty())
                for (GuiEditRunnable runnable : edits)
                    runnable.keyTyped(event.gui, Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }

        @SubscribeEvent
        protected void onGuiInit(GuiScreenEvent.MouseInputEvent event) {
            List<GuiEditRunnable> edits = editMap.get(event.gui.getClass());
            if (edits != null && !edits.isEmpty())
                for (GuiEditRunnable runnable : edits)
                    runnable.mouseClicked(event.gui, Mouse.getEventButton(), MouseHelper.getMouseX(), MouseHelper.getMouseY(), Mouse.getEventDWheel());
        }

        public interface GuiEditRunnable {
            Minecraft mc = Minecraft.getMinecraft();
            void init(GuiScreen screen, List<GuiButton> buttonList);
            default void actionPerformed(GuiScreen screen, List<GuiButton> buttonList, GuiButton clicked) {};
            void draw(GuiScreen screen, int mouseX, int mouseY, float partialTicks);
            default void keyTyped(GuiScreen screen, char typedChar, int keyCode) {};
            default void mouseClicked(GuiScreen screen, int button, int mouseX, int mouseY, int wheel) {};
        }

    }

    public enum GuiScale {
        AUTO(0),
        SMALL(1),
        NORMAL(2),
        LARGE(3);

        @Getter
        private final int scale;
        GuiScale(int scale) {
            this.scale = scale;
        }
    }

}