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
import net.minecraft.util.EnumChatFormatting;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.cosmetics.BaseCosmetic;
import xyz.matthewtgm.tgmlib.cosmetics.CosmeticManager;
import xyz.matthewtgm.tgmlib.cosmetics.PlayerCosmeticsHolder;
import xyz.matthewtgm.tgmlib.gui.GuiTransButton;
import xyz.matthewtgm.tgmlib.socket.TGMLibSocket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.cosmetics.CosmeticsRetrievePacket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.cosmetics.CosmeticsTogglePacket;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiCosmeticSelector extends GuiScreen {

    private final GuiScreen parent;

    public GuiCosmeticSelector(GuiScreen parent) {
        this.parent = parent;
    }

    public GuiCosmeticSelector() {
        this(null);
    }

    public void initGui() {
        buttonList.add(new GuiTransButton(0, width / 2 - 50, height - 22, 100, 20, parent == null ? "Close" : "Back") {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) mc.displayGuiScreen(parent);
                return false;
            }
        });
        buttonList.add(new GuiTransButton(1, width - 102, height - 22, 100, 20, "Refresh") {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) {
                    TGMLib.getManager().getCosmeticManager().getCosmeticMap().clear();
                    CosmeticManager.getMadeRequestsFor().clear();
                    TGMLib.getManager().getWebSocket().send(new CosmeticsRetrievePacket(mc.getSession().getProfile().getId().toString()));
                }
                return false;
            }
        });
       addCosmeticButtons();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    private void addCosmeticButtons() {
        CosmeticManager cosmeticManager = TGMLib.getManager().getCosmeticManager();
        PlayerCosmeticsHolder cosmeticsHolder = cosmeticManager.getCosmeticMap().get(mc.getSession().getProfile().getId().toString());
        TGMLibSocket libSocket = TGMLib.getManager().getWebSocket();
        AtomicInteger buttonId = new AtomicInteger(2);
        AtomicInteger xOff = new AtomicInteger(5);
        AtomicInteger yOff = new AtomicInteger(5);

        for (BaseCosmetic ownedCosmetic : cosmeticsHolder.getOwnedCosmetics()) {
            boolean toggled = cosmeticsHolder.getEnabledCosmetics().contains(ownedCosmetic);
            int xPos = xOff.get();
            int yPos = yOff.get();
            GuiTransButton button = new GuiTransButton(buttonId.getAndAdd(1), xPos, yPos, 150, 20, String.format("%s (%s%s%s)", ownedCosmetic.getName(), toggled ? EnumChatFormatting.GREEN : EnumChatFormatting.RED, toggled ? "ON" : "OFF", EnumChatFormatting.RESET)) {
                public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                    if (super.mousePressed(mc, mouseX, mouseY)) {
                        for (BaseCosmetic enabledCosmetic : cosmeticsHolder.getEnabledCosmetics()) if (!enabledCosmetic.getId().equalsIgnoreCase(ownedCosmetic.getId()) && enabledCosmetic.getType().equals(ownedCosmetic.getType())) libSocket.send(new CosmeticsTogglePacket(mc.getSession().getProfile().getId().toString(), enabledCosmetic.getId()));
                        libSocket.send(new CosmeticsTogglePacket(mc.getSession().getProfile().getId().toString(), ownedCosmetic.getId()));
                        new Thread(() -> {
                            try {
                                Thread.sleep(500);
                                boolean toggled = cosmeticsHolder.getEnabledCosmetics().contains(ownedCosmetic);
                                displayString = String.format("%s (%s%s%s)", ownedCosmetic.getName(), toggled ? EnumChatFormatting.GREEN : EnumChatFormatting.RED, toggled ? "ON" : "OFF", EnumChatFormatting.RESET);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                    return false;
                }
            };
            buttonList.add(button);
            xOff.set(xPos + button.getButtonWidth() + 5);
            if (xPos > width - button.getButtonWidth() * 2) {
                xOff.set(5);
                yOff.set(yPos + button.height + 5);
            }
        }
    }

}