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

package xyz.matthewtgm.tgmlib.gui;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.data.HitBox;
import xyz.matthewtgm.tgmlib.util.*;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public abstract class GuiTGMLibBase extends GuiScreen {

    private String title;
    private int titleColour = -1;
    @Getter private GuiScreen parent;

    protected HitBox backgroundHitBox;
    protected HitBox backgroundOutlineHitBox;

    /* Settings. */
    @Getter private boolean refreshing;
    @Getter private int refreshTime;
    public boolean allowRefreshing() {
        return false;
    }

    public GuiTGMLibBase(String title, int titleColour, GuiScreen parent) {
        this.title = title;
        this.titleColour = titleColour;
        this.parent = parent;
    }

    public GuiTGMLibBase(String title, GuiScreen parent) {
        this(title, -1, parent);
    }

    public GuiTGMLibBase(String title, int titleColour) {
        this(title, titleColour, null);
    }

    public GuiTGMLibBase(String title) {
        this(title, -1, null);
    }

    private GuiTGMLibBase() {}

    public abstract void initialize();
    public abstract void draw(int mouseX, int mouseY, float partialTicks);
    public void postComponents(int mouseX, int mouseY, float partialTicks) {}

    public void initGui() {
        buttonList.clear();
        backgroundHitBox = createBackgroundHitBox();
        backgroundOutlineHitBox = createBackgroundOutlineHitBox(backgroundHitBox);
        buttonList.add(new GuiTransFadingImageButton(0, backgroundHitBox.getIntX() + 2, backgroundHitBox.getIntY() + 2, 30, 30, ResourceHelper.get("tgmlib", "gui/icons/exit_icon.png")) {
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

        if (backgroundHitBox == null || backgroundOutlineHitBox == null)
            return;

        /* Background. */
        RenderHelper.drawRect(backgroundHitBox.getIntX(), backgroundHitBox.getIntY(), backgroundHitBox.getIntWidth(), backgroundHitBox.getIntHeight(), backgroundColour());
        RenderHelper.drawHollowRect(backgroundOutlineHitBox.getIntX(), backgroundOutlineHitBox.getIntY(), backgroundOutlineHitBox.getIntWidth(), backgroundOutlineHitBox.getIntHeight(), backgroundOutlineColour());
        RenderHelper.drawHollowRect(backgroundHitBox.getIntX(), backgroundHitBox.getIntY(), backgroundHitBox.getIntWidth() - backgroundHitBox.getIntX(), backgroundHitBox.getIntY() + 14, backgroundOutlineColour());

        /* Text. */
        EnhancedFontRenderer.drawCenteredStyledScaledText(title, 2, width / 2, backgroundOutlineHitBox.getY() + 10, titleColour);

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
        for (GuiButton button : buttonList)
            if (button != null)
                button.drawButton(mc, mouseX, mouseY);

        for (GuiLabel label : labelList)
            if (label != null)
                label.drawLabel(mc, mouseX, mouseY);
    }

    private HitBox createBackgroundHitBox() {
        return new HitBox(20, 20, width - 30,height - 30);
    }

    private HitBox createBackgroundOutlineHitBox(HitBox backgroundHitBox) {
        return new HitBox(backgroundHitBox.getX(), backgroundHitBox.getY(), backgroundHitBox.getWidth() - backgroundHitBox.getX(), backgroundHitBox.getHeight() - backgroundHitBox.getY());
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    public void refresh(int refreshTime) {
        if (!allowRefreshing()) throw new IllegalStateException("Refreshing hasn't been explicitly allowed!");
        refreshing = true;
        this.refreshTime = refreshTime;
        Multithreading.schedule(() -> {
            try {
                initGui();
                refreshing = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, refreshTime, TimeUnit.SECONDS);
    }

    private int backgroundColour() {
        return TGMLib.getManager().getConfigHandler().isLightMode() ? new Color(213, 213, 213, 189).getRGB() : new Color(87, 87, 87, 189).getRGB();
    }

    private int backgroundOutlineColour() {
        return TGMLib.getManager().getConfigHandler().isLightMode() ? new Color(246, 246, 246, 234).getRGB() : new Color(120, 120, 120, 234).getRGB();
    }

}