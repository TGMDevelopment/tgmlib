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

package xyz.matthewtgm.tgmlib.gui.menus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import xyz.matthewtgm.tgmlib.cosmetics.CosmeticManager;
import xyz.matthewtgm.tgmlib.data.HitBox;
import xyz.matthewtgm.tgmlib.gui.GuiTransFadingImageButton;
import xyz.matthewtgm.tgmlib.profiles.ProfileManager;
import xyz.matthewtgm.tgmlib.util.EnhancedFontRenderer;
import xyz.matthewtgm.tgmlib.util.GuiHelper;
import xyz.matthewtgm.tgmlib.util.RenderHelper;
import xyz.matthewtgm.tgmlib.util.ResourceHelper;

import java.awt.*;

public class GuiMod extends GuiScreen {

    private final GuiScreen parent;
    private final GuiMod $this = this;

    public GuiMod(GuiScreen parent) {
        this.parent = parent;
    }

    public void initGui() {
        HitBox backgroundHitBox = createBackgroundHitBox();
        buttonList.add(new GuiTransFadingImageButton(0, backgroundHitBox.getIntX() + 2, backgroundHitBox.getIntY() + 2, 30, 30, ResourceHelper.get("tgmlib", "gui/icons/exit_icon.png")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY))
                mc.displayGuiScreen(parent);
                return false;
            }
        });

        if (CosmeticManager.isLoaded()) {
            buttonList.add(new GuiTransFadingImageButton(1, width / 2 - 25, height / 2 - 40, 50, 50, ResourceHelper.get("tgmlib", "gui/icons/cosmetics_icon.png")) {
                public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                    if (super.mousePressed(mc, mouseX, mouseY))
                        mc.displayGuiScreen(new GuiCosmeticSelector($this));
                    return false;
                }
            });
        }

        if (ProfileManager.isLoaded()) {
            // TODO: 2021/07/02
            // x: width / 2 - 25 | y: height / 2 + 20
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GuiHelper.drawBackground(this, 120);

        /* Background. */
        HitBox backgroundHitBox = createBackgroundHitBox();
        RenderHelper.drawRect(backgroundHitBox.getIntX(), backgroundHitBox.getIntY(), backgroundHitBox.getIntWidth(), backgroundHitBox.getIntHeight(), new Color(87, 87, 87, 189).getRGB());
        HitBox backgroundOutlineHitBox = createBackgroundOutlineHitBox(backgroundHitBox);
        RenderHelper.drawHollowRect(backgroundOutlineHitBox.getIntX(), backgroundOutlineHitBox.getIntY(), backgroundOutlineHitBox.getIntWidth(), backgroundOutlineHitBox.getIntHeight(), new Color(120, 120, 120, 234).getRGB());

        /* Clickables. */
        // TODO: 2021/07/02

        /* Text. */
        EnhancedFontRenderer.drawCenteredStyledScaledText("TGMLib", 3, width / 2, 30, new Color(255, 175, 0).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private HitBox createBackgroundHitBox() {
        return new HitBox(20, 20, width - 30,height - 30);
    }

    private HitBox createBackgroundOutlineHitBox(HitBox backgroundHitBox) {
        return new HitBox(backgroundHitBox.getX(), backgroundHitBox.getY(), backgroundHitBox.getWidth() - backgroundHitBox.getX(), backgroundHitBox.getHeight() - backgroundHitBox.getY());
    }

}