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
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.data.HitBox;
import xyz.matthewtgm.tgmlib.gui.GuiTransFadingButton;
import xyz.matthewtgm.tgmlib.gui.GuiTransFadingImageButton;
import xyz.matthewtgm.tgmlib.keybinds.KeyBind;
import xyz.matthewtgm.tgmlib.keybinds.KeyBindManager;
import xyz.matthewtgm.tgmlib.util.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiKeyBinds extends GuiScreen {

    private final GuiScreen parent;
    private final GuiKeyBinds $this = this;

    private List<GuiButton> keyBindButtonList = new ArrayList<>();

    private ListeningButtonHolder listeningButton;
    private KeyBind listeningKeyBind;

    private final List<Integer> scrollCache = new ArrayList<>();
    private int scrollAmount;

    public GuiKeyBinds(GuiScreen parent) {
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
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GuiHelper.drawBackground(this, 120);

        /* Background. */
        HitBox backgroundHitBox = createBackgroundHitBox();
        RenderHelper.drawRect(backgroundHitBox.getIntX(), backgroundHitBox.getIntY(), backgroundHitBox.getIntWidth(), backgroundHitBox.getIntHeight(), new Color(87, 87, 87, 189).getRGB());
        HitBox backgroundOutlineHitBox = createBackgroundOutlineHitBox(backgroundHitBox);
        RenderHelper.drawHollowRect(backgroundOutlineHitBox.getIntX(), backgroundOutlineHitBox.getIntY(), backgroundOutlineHitBox.getIntWidth(), backgroundOutlineHitBox.getIntHeight(), new Color(120, 120, 120, 234).getRGB());
        RenderHelper.drawHollowRect(backgroundHitBox.getIntX(), backgroundHitBox.getIntY(), backgroundHitBox.getIntWidth() - backgroundHitBox.getIntX(), backgroundHitBox.getIntY() + 14, new Color(120, 120, 120, 234).getRGB());

        EnhancedFontRenderer.drawCenteredStyledScaledText("KeyBinds", 2, width / 2, backgroundOutlineHitBox.getY() + 10, -1);
        keyBindButtons(mouseX, mouseY, backgroundHitBox);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            for (int i = 0; i < keyBindButtonList.size(); ++i) {
                GuiButton guibutton = keyBindButtonList.get(i);
                if (guibutton.mousePressed(mc, mouseX, mouseY)) {
                    guibutton.playPressSound(mc.getSoundHandler());
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (listeningKeyBind != null) {
            listeningKeyBind.updateKey(keyCode);
            TGMLib.getManager().getKeyBindConfigHandler().update(listeningKeyBind);

            if (listeningButton != null)
                keyBindButtonList.get(listeningButton.index).displayString = listeningButton.displayString;

            listeningButton = null;
            listeningKeyBind = null;
        }
        super.keyTyped(typedChar, keyCode);
    }

    private HitBox createBackgroundHitBox() {
        return new HitBox(20, 20, width - 30,height - 30);
    }

    private HitBox createBackgroundOutlineHitBox(HitBox backgroundHitBox) {
        return new HitBox(backgroundHitBox.getX(), backgroundHitBox.getY(), backgroundHitBox.getWidth() - backgroundHitBox.getX(), backgroundHitBox.getHeight() - backgroundHitBox.getY());
    }

    private void keyBindButtons(int mouseX, int mouseY, HitBox backgroundHitBox) {
        List<GuiButton> keyBindButtonList = new ArrayList<>();
        AtomicInteger buttonId = new AtomicInteger(1);
        AtomicInteger keyBindY = new AtomicInteger(backgroundHitBox.getIntY() + 40);
        handleScrolling(keyBindY);

        for (KeyBind keyBind : KeyBindManager.getKeyBinds()) {
            keyBindButtonList.add(new GuiTransFadingButton(buttonId.getAndAdd(1), backgroundHitBox.getIntX() + 4, keyBindY.getAndAdd(22), backgroundHitBox.getIntWidth() - 28, 20, "(" + keyBind.category() + ") " + keyBind.name() + " : " + Keyboard.getKeyName(keyBind.getKey())) {
                public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                    if (super.mousePressed(mc, mouseX, mouseY)) {
                        listeningButton = new ListeningButtonHolder($this.keyBindButtonList.indexOf(this), displayString);
                        listeningKeyBind = keyBind;
                    }
                    return false;
                }
            });
        }

        if (listeningButton != null && listeningKeyBind != null)
            keyBindButtonList.get(listeningButton.index).displayString = "(" + listeningKeyBind.category() + ") " + listeningKeyBind.name() + " : " + Keyboard.getKeyName(listeningKeyBind.getKey()) + " : [LISTENING]";

        this.keyBindButtonList = new ArrayList<>(keyBindButtonList);

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GlHelper.totalScissor(backgroundHitBox.getIntX(), backgroundHitBox.getIntY() + 36, backgroundHitBox.getWidth(), backgroundHitBox.getIntHeight() - backgroundHitBox.getIntY() - 36);
        for (int i = 0; i < this.keyBindButtonList.size(); i++) this.keyBindButtonList.get(i).drawButton(mc, mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.popMatrix();
    }

    private void handleScrolling(AtomicInteger value) {
        int scrollWheelScroll = Mouse.getDWheel();
        scrollCache.add(scrollWheelScroll);
        if (scrollCache.size() > 10)
            scrollCache.remove(0);
        scrollAmount = (int) (scrollAmount + ArrayHelper.averageInts(scrollCache) / 10);
        value.set(value.get() - scrollAmount);
    }

    private static class ListeningButtonHolder {
        public final int index;
        public final String displayString;
        private ListeningButtonHolder(int index, String displayString) {
            this.index = index;
            this.displayString = displayString;}
    }

}