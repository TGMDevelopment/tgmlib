/*
 * Requisite - Minecraft library mod
 * Copyright (C) 2021 MatthewTGM
 *
 * Requisite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * Requisite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Requisite. If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.matthewtgm.requisite.gui;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.data.HitBox;
import xyz.matthewtgm.requisite.util.*;

import java.awt.*;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public abstract class GuiRequisiteBase extends GuiScreen {

    private String title;
    private int titleColour = -1;
    @Getter private GuiScreen parent;

    protected HitBox backgroundHitBox;

    /* Settings. */
    @Getter private boolean refreshing;
    @Getter private int refreshTime;
    public boolean allowRefreshing() {
        return false;
    }

    public GuiRequisiteBase(String title, int titleColour, GuiScreen parent) {
        this.title = title;
        this.titleColour = titleColour;
        this.parent = parent;
    }

    public GuiRequisiteBase(String title, GuiScreen parent) {
        this(title, -1, parent);
    }

    public GuiRequisiteBase(String title, int titleColour) {
        this(title, titleColour, null);
    }

    public GuiRequisiteBase(String title) {
        this(title, -1, null);
    }

    private GuiRequisiteBase() {}

    public abstract void initialize();
    public abstract void draw(int mouseX, int mouseY, float partialTicks);
    public void postComponents(int mouseX, int mouseY, float partialTicks) {}

    public void initGui() {
        if (!(buttonList instanceof CopyOnWriteArrayList))
            buttonList = new CopyOnWriteArrayList<>();
        if (!(labelList instanceof CopyOnWriteArrayList))
            labelList = new CopyOnWriteArrayList<>();

        buttonList.clear();

        backgroundHitBox = createBackgroundHitBox();
        buttonList.add(new GuiTransFadingImageButton(0, backgroundHitBox.getIntX() + 2, backgroundHitBox.getIntY() + 2, 30, 30, ResourceHelper.get("requisite", "gui/icons/exit_icon.png")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY))
                    mc.displayGuiScreen(parent);
                return false;
            }
        });

        initialize();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GuiHelper.drawBackground(this, 120);

        if (backgroundHitBox == null)
            return;

        /* Background. */
        RenderHelper.drawRect(backgroundHitBox.getIntX(), backgroundHitBox.getIntY(), backgroundHitBox.getIntWidth(), backgroundHitBox.getIntHeight(), backgroundColour());
        RenderHelper.drawHorizontalLine(backgroundHitBox.getIntX(), backgroundHitBox.getIntWidth() - backgroundHitBox.getIntX(), backgroundHitBox.getIntY() + EnhancedFontRenderer.getFontHeight() * 4, foregroundDividerColour());

        /* Text. */
        EnhancedFontRenderer.drawCenteredStyledScaledText(title, 2, width / 2, backgroundHitBox.getY() + EnhancedFontRenderer.getFontHeight(), titleColour);

        /* Default. */
        draw(mouseX, mouseY, partialTicks);
        drawComponents(mouseX, mouseY);
        postComponents(mouseX, mouseY, partialTicks);
        if (refreshing) {
            RenderHelper.drawRect(0, 0, width, height, new Color(87, 87, 87, 220).getRGB());
            EnhancedFontRenderer.drawCenteredStyledScaledText("Refreshing...", 4, width / 2, height / 2, -1);
            EnhancedFontRenderer.drawCenteredStyledScaledText("If this takes longer than", 4, width / 2, height / 2 + 35, -1);
            EnhancedFontRenderer.drawCenteredStyledScaledText(refreshTime + " seconds, please restart.", 4, width / 2, height / 2 + 70, -1);
        }
    }

    private void drawComponents(int mouseX, int mouseY) {
        try {
            for (GuiButton button : buttonList)
                if (button != null)
                    button.drawButton(mc, mouseX, mouseY);

            for (GuiLabel label : labelList)
                if (label != null)
                    label.drawLabel(mc, mouseX, mouseY);
        } catch (Exception e) {
            if (e instanceof ConcurrentModificationException)
                return;

            e.printStackTrace();
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (refreshing)
            return;
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (refreshing)
            return;
        super.keyTyped(typedChar, keyCode);
    }

    private HitBox createBackgroundHitBox() {
        return new HitBox(0, 0, width,height);
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    public void refresh(int refreshTime, Runnable listener) {
        if (!allowRefreshing())
            throw new IllegalStateException("Refreshing hasn't been explicitly allowed!");
        refreshing = true;
        this.refreshTime = refreshTime;
        Multithreading.schedule(() -> {
            try {
                listener.run();
                initGui();
                refreshing = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, refreshTime, TimeUnit.SECONDS);
    }

    public void refresh(int refreshTime) {
        refresh(refreshTime, () -> {});
    }

    public void showParent() {
        mc.displayGuiScreen(parent);
    }

    private int backgroundColour() {
        return Requisite.getManager().getConfigHandler().isLightMode() ? new Color(213, 213, 213, 189).getRGB() : new Color(87, 87, 87, 189).getRGB();
    }

    private int foregroundDividerColour() {
        return Requisite.getManager().getConfigHandler().isLightMode() ? new Color(246, 246, 246, 234).getRGB() : new Color(120, 120, 120, 234).getRGB();
    }

}