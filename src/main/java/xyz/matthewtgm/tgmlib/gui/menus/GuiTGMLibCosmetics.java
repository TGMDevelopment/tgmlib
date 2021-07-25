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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.matthewtgm.tgmconfig.ConfigEntry;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.core.TGMLibManager;
import xyz.matthewtgm.tgmlib.players.PlayerCosmeticData;
import xyz.matthewtgm.tgmlib.players.cosmetics.BaseCosmetic;
import xyz.matthewtgm.tgmlib.players.cosmetics.CosmeticManager;
import xyz.matthewtgm.tgmlib.players.cosmetics.CosmeticType;
import xyz.matthewtgm.tgmlib.gui.GuiTGMLibBase;
import xyz.matthewtgm.tgmlib.gui.GuiTransFadingButton;
import xyz.matthewtgm.tgmlib.gui.GuiTransFadingImageButton;
import xyz.matthewtgm.tgmlib.socket.packets.impl.cosmetics.CosmeticsTogglePacket;
import xyz.matthewtgm.tgmlib.util.*;
import xyz.matthewtgm.tgmlib.util.global.GlobalMinecraft;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiTGMLibCosmetics extends GuiTGMLibBase {

    private List<GuiButton> cosmeticButtonList = new ArrayList<>();
    private int lastButtonId;

    private CosmeticType currentType = CosmeticType.CLOAK;

    private final List<BaseCosmetic> cachedOwnedCosmetics = new ArrayList<>();
    private final List<BaseCosmetic> cachedEnabledCosmetics = new ArrayList<>();

    private final List<Integer> scrollCache = new ArrayList<>();
    private int scrollAmount;

    public GuiTGMLibCosmetics(GuiScreen parent) {
        super("Cosmetics", new Color(0, 179, 0).getRGB(), parent);
    }

    public void initialize() {
        cachedOwnedCosmetics.clear();
        cachedEnabledCosmetics.clear();
        cosmeticButtonList.clear();

        buttonList.add(new GuiTransFadingImageButton(1, backgroundHitBox.getIntWidth() - 32, backgroundHitBox.getIntY() + 2, 30, 30, ResourceHelper.get("tgmlib", "gui/icons/refresh_icon.png")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) {
                    TGMLib.getManager().getDataManager().getDataMap().clear();
                    List<String> cachedRequests = new ArrayList<>(CosmeticManager.getMadeRequestsFor());
                    CosmeticManager.getMadeRequestsFor().clear();
                    if (GlobalMinecraft.getWorld() != null) {
                        for (EntityPlayer playerEntity : GlobalMinecraft.getWorld().playerEntities) {
                            if (cachedRequests.contains(playerEntity.getUniqueID().toString())) {
                                TGMLib.getManager().getCosmeticManager().get(playerEntity.getUniqueID().toString());
                            }
                        }
                    }
                    TGMLib.getManager().getCosmeticManager().get(mc.getSession().getProfile().getId().toString());
                    refresh(3, () -> {
                        cachedOwnedCosmetics.clear();
                        cachedEnabledCosmetics.clear();
                    });
                }
                return false;
            }
        });
        buttonList.add(new GuiTransFadingButton(2, backgroundHitBox.getIntWidth() - 134, backgroundHitBox.getIntY() + 2, 100, 30, "Show Cosmetics: " + (TGMLib.getManager().getConfigHandler().getShowCosmetics().get() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) {
                    TGMLibManager manager = TGMLib.getManager();
                    manager.getConfig().add(new ConfigEntry<>("show_cosmetics", !manager.getConfigHandler().getShowCosmetics().get()));
                    manager.getConfig().save();
                    manager.getConfigHandler().update();
                    refresh(3);
                }
                return false;
            }
        });
        buttonList.add(new GuiTransFadingButton(3, backgroundHitBox.getIntX() + 34, backgroundHitBox.getIntY() + 2, 100, 30, "Override Capes: " + (TGMLib.getManager().getConfigHandler().getOverrideCapes().get() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) {
                    TGMLibManager manager = TGMLib.getManager();
                    manager.getConfig().add(new ConfigEntry<>("override_capes", !manager.getConfigHandler().getOverrideCapes().get()));
                    manager.getConfig().save();
                    manager.getConfigHandler().update();
                    refresh(3);
                }
                return false;
            }
        });

        AtomicInteger buttonId = new AtomicInteger(4);
        AtomicInteger typeY = new AtomicInteger(backgroundOutlineHitBox.getIntY() + 40);
        for (CosmeticType type : CosmeticType.values()) {
            buttonList.add(new GuiTransFadingButton(buttonId.getAndAdd(1), backgroundOutlineHitBox.getIntX() + 4, typeY.getAndAdd(22), 120, 20, type.getName()) {
                public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                    if (super.mousePressed(mc, mouseX, mouseY)) {
                        currentType = type;
                        initGui();
                    }
                    return false;
                }
            });
        }
        lastButtonId = buttonId.get();

        PlayerCosmeticData ownCosmeticsHolder = TGMLib.getManager().getDataManager().getDataMap().get(mc.getSession().getProfile().getId().toString()).getCosmeticData();
        if (ownCosmeticsHolder == null) {
            GuiHelper.open(getParent());
            return;
        }

        cachedOwnedCosmetics.addAll(ownCosmeticsHolder.getOwnedCosmetics());
        cachedEnabledCosmetics.addAll(ownCosmeticsHolder.getEnabledCosmetics());
        cachedOwnedCosmetics.sort(Comparator.comparing(BaseCosmetic::getName));
        cachedEnabledCosmetics.sort(Comparator.comparing(BaseCosmetic::getName));
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {
        cosmeticButtons(mouseX, mouseY);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isRefreshing()) return;
        if (mouseButton == 0) {
            for (GuiButton guibutton : cosmeticButtonList) {
                if (guibutton.mousePressed(mc, mouseX, mouseY)) {
                    guibutton.playPressSound(mc.getSoundHandler());
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (isRefreshing()) return;
        super.keyTyped(typedChar, keyCode);
    }

    public boolean allowRefreshing() {
        return true;
    }

    private void cosmeticButtons(int mouseX, int mouseY) {
        List<GuiButton> cosmeticButtonList = new ArrayList<>();
        AtomicInteger buttonId = new AtomicInteger(lastButtonId);
        AtomicInteger cosmeticY = new AtomicInteger(backgroundOutlineHitBox.getIntY() + 40);
        handleScrolling(cosmeticY);
        for (BaseCosmetic owned : cachedOwnedCosmetics) {
            if (owned.getType().equals(currentType)) {
                cosmeticButtonList.add(new GuiTransFadingButton(buttonId.getAndAdd(1), backgroundOutlineHitBox.getIntX() + 132, cosmeticY.getAndAdd(22), width - 186, 20, owned.getName() + ": " + (cachedEnabledCosmetics.contains(owned) ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")) {
                    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                        if (super.mousePressed(mc, mouseX, mouseY)) {
                            for (BaseCosmetic enabled : cachedEnabledCosmetics) if (!enabled.getId().equalsIgnoreCase(owned.getId()) && enabled.getType().equals(currentType)) TGMLib.getManager().getWebSocket().send(new CosmeticsTogglePacket(mc.getSession().getProfile().getId().toString(), enabled.getId()));
                            TGMLib.getManager().getWebSocket().send(new CosmeticsTogglePacket(mc.getSession().getProfile().getId().toString(), owned.getId()));
                            refresh(3);
                        }
                        return false;
                    }
                });
            }
        }
        this.cosmeticButtonList = new ArrayList<>(cosmeticButtonList);

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GlHelper.totalScissor(backgroundOutlineHitBox.getIntX() + 132, backgroundOutlineHitBox.getIntY() + 40, width - 186, backgroundOutlineHitBox.getIntHeight() - backgroundOutlineHitBox.getIntY() - 26);
        for (int i = 0; i < this.cosmeticButtonList.size(); i++) this.cosmeticButtonList.get(i).drawButton(mc, mouseX, mouseY);
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

}