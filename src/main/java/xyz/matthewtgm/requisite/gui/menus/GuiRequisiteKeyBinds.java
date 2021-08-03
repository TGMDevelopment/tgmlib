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

package xyz.matthewtgm.requisite.gui.menus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.gui.GuiRequisiteBase;
import xyz.matthewtgm.requisite.gui.GuiTransFadingButton;
import xyz.matthewtgm.requisite.keybinds.KeyBind;
import xyz.matthewtgm.requisite.keybinds.KeyBindManager;
import xyz.matthewtgm.requisite.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiRequisiteKeyBinds extends GuiRequisiteBase {

    private final GuiRequisiteKeyBinds $this = this;

    private List<GuiButton> keyBindButtonList = new ArrayList<>();

    private ListeningButtonHolder listeningButton;
    private KeyBind listeningKeyBind;

    private final List<Integer> scrollCache = new ArrayList<>();
    private int scrollAmount;

    public GuiRequisiteKeyBinds(GuiScreen parent) {
        super(Requisite.NAME + " - KeyBinds", parent);
    }

    public void initialize() {}

    public void draw(int mouseX, int mouseY, float partialTicks) {
        keyBindButtons(mouseX, mouseY);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            for (GuiButton guibutton : keyBindButtonList) {
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
            Requisite.getManager().getKeyBindConfigHandler().update(listeningKeyBind);

            if (listeningButton != null)
                keyBindButtonList.get(listeningButton.index).displayString = listeningButton.displayString;

            listeningButton = null;
            listeningKeyBind = null;
        }
        super.keyTyped(typedChar, keyCode);
    }

    private void keyBindButtons(int mouseX, int mouseY) {
        List<GuiButton> keyBindButtonList = new ArrayList<>();
        AtomicInteger buttonId = new AtomicInteger(1);
        AtomicInteger keyBindY = new AtomicInteger(backgroundHitBox.getIntY() + 40);
        handleScrolling(keyBindY);

        for (KeyBind keyBind : Requisite.getManager().getKeyBindManager().getKeyBinds()) {
            keyBindButtonList.add(new GuiTransFadingButton(buttonId.getAndAdd(1), backgroundHitBox.getIntX() + 4, keyBindY.getAndAdd(22), backgroundHitBox.getIntWidth() - 8, 20, "(" + keyBind.category() + ") " + keyBind.name() + " : " + Keyboard.getKeyName(keyBind.getKey())) {
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
        GlHelper.totalScissor(backgroundHitBox.getIntX() + 4, backgroundHitBox.getIntY() + 40, width - 4, backgroundHitBox.getIntHeight() - backgroundHitBox.getIntY() - 26);
        for (GuiButton button : this.keyBindButtonList)
            button.drawButton(mc, mouseX, mouseY);
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