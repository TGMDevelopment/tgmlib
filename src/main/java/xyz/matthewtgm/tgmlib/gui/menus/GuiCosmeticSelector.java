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
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.matthewtgm.tgmconfig.ConfigEntry;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.core.TGMLibManager;
import xyz.matthewtgm.tgmlib.cosmetics.BaseCosmetic;
import xyz.matthewtgm.tgmlib.cosmetics.CosmeticManager;
import xyz.matthewtgm.tgmlib.cosmetics.CosmeticType;
import xyz.matthewtgm.tgmlib.cosmetics.PlayerCosmeticsHolder;
import xyz.matthewtgm.tgmlib.data.HitBox;
import xyz.matthewtgm.tgmlib.gui.GuiTransFadingButton;
import xyz.matthewtgm.tgmlib.gui.GuiTransFadingImageButton;
import xyz.matthewtgm.tgmlib.socket.packets.impl.cosmetics.CosmeticsRetrievePacket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.cosmetics.CosmeticsTogglePacket;
import xyz.matthewtgm.tgmlib.util.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiCosmeticSelector extends GuiScreen {

    private final GuiScreen parent;

    private List<GuiButton> cosmeticButtonList = new ArrayList<>();
    private int lastButtonId;

    private boolean refreshing;
    private int refreshTime;

    private CosmeticType currentType = CosmeticType.CLOAK;

    private final List<BaseCosmetic> cachedOwnedCosmetics = new ArrayList<>();
    private final List<BaseCosmetic> cachedEnabledCosmetics = new ArrayList<>();

    private final List<Integer> scrollCache = new ArrayList<>();
    private int scrollAmount;

    public GuiCosmeticSelector(GuiScreen parent) {
        this.parent = parent;
    }

    public void initGui() {
        buttonList.clear();
        cosmeticButtonList = new ArrayList<>();
        HitBox backgroundHitBox = createBackgroundHitBox();
        buttonList.add(new GuiTransFadingImageButton(0, backgroundHitBox.getIntX() + 2, backgroundHitBox.getIntY() + 2, 30, 30, ResourceHelper.get("tgmlib", "gui/icons/exit_icon.png")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY))
                    mc.displayGuiScreen(parent);
                return false;
            }
        });
        buttonList.add(new GuiTransFadingImageButton(1, backgroundHitBox.getIntWidth() - 32, backgroundHitBox.getIntY() + 2, 30, 30, ResourceHelper.get("tgmlib", "gui/icons/refresh_icon.png")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) {
                    TGMLib.getManager().getCosmeticManager().getCosmeticMap().clear();
                    CosmeticManager.getMadeRequestsFor().clear();
                    TGMLib.getManager().getWebSocket().send(new CosmeticsRetrievePacket(mc.getSession().getProfile().getId().toString()));
                    refresh(2);
                }
                return false;
            }
        });
        buttonList.add(new GuiTransFadingButton(2, backgroundHitBox.getIntWidth() - 134, backgroundHitBox.getIntY() + 2, 100, 30, "Show Cosmetics: " + (TGMLib.getManager().getConfigHandler().isShowCosmetics() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) {
                    TGMLibManager manager = TGMLib.getManager();
                    manager.getConfig().add(new ConfigEntry<>("show_cosmetics", !manager.getConfigHandler().isShowCosmetics()));
                    manager.getConfig().save();
                    manager.getConfigHandler().update();
                    refresh(2);
                }
                return false;
            }
        });
        buttonList.add(new GuiTransFadingButton(3, backgroundHitBox.getIntX() + 34, backgroundHitBox.getIntY() + 2, 100, 30, "Override Capes: " + (TGMLib.getManager().getConfigHandler().isOverrideCapes() ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF")) {
            public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                if (super.mousePressed(mc, mouseX, mouseY)) {
                    TGMLibManager manager = TGMLib.getManager();
                    manager.getConfig().add(new ConfigEntry<>("override_capes", !manager.getConfigHandler().isOverrideCapes()));
                    manager.getConfig().save();
                    manager.getConfigHandler().update();
                    refresh(2);
                }
                return false;
            }
        });

        HitBox backgroundOutlineHitBox = createBackgroundOutlineHitBox(backgroundHitBox);
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

        CosmeticManager cosmeticManager = TGMLib.getManager().getCosmeticManager();
        PlayerCosmeticsHolder ownCosmeticsHolder = cosmeticManager.getCosmeticMap().get(mc.getSession().getProfile().getId().toString());
        if (cachedOwnedCosmetics.isEmpty()) cachedOwnedCosmetics.addAll(ownCosmeticsHolder.getOwnedCosmetics());
        if (cachedEnabledCosmetics.isEmpty()) cachedEnabledCosmetics.addAll(ownCosmeticsHolder.getEnabledCosmetics());
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GuiHelper.drawBackground(this, 120);

        /* Background. */
        HitBox backgroundHitBox = createBackgroundHitBox();
        RenderHelper.drawRect(backgroundHitBox.getIntX(), backgroundHitBox.getIntY(), backgroundHitBox.getIntWidth(), backgroundHitBox.getIntHeight(), new Color(87, 87, 87, 189).getRGB());
        HitBox backgroundOutlineHitBox = createBackgroundOutlineHitBox(backgroundHitBox);
        RenderHelper.drawHollowRect(backgroundOutlineHitBox.getIntX(), backgroundOutlineHitBox.getIntY(), backgroundOutlineHitBox.getIntWidth(), backgroundOutlineHitBox.getIntHeight(), new Color(120, 120, 120, 234).getRGB());
        RenderHelper.drawHollowRect(backgroundHitBox.getIntX(), backgroundHitBox.getIntY(), backgroundHitBox.getIntWidth() - backgroundHitBox.getIntX(), backgroundHitBox.getIntY() + 14, new Color(120, 120, 120, 234).getRGB());
        RenderHelper.drawHollowRect(backgroundOutlineHitBox.getIntX(), backgroundHitBox.getIntY() + 34, 127, backgroundOutlineHitBox.getIntHeight() - backgroundOutlineHitBox.getIntX() - 16, new Color(120, 120, 120, 234).getRGB());

        /* Text. */
        EnhancedFontRenderer.drawCenteredStyledScaledText("Cosmetics", 2, width / 2, backgroundOutlineHitBox.getY() + 10, -1);

        cosmeticButtons(mouseX, mouseY, backgroundOutlineHitBox);
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (refreshing) {
            RenderHelper.drawRect(0, 0, width, height, new Color(87, 87, 87, 220).getRGB());
            EnhancedFontRenderer.drawCenteredStyledScaledText("Refreshing...", 4, width / 2, height / 2, -1);
            EnhancedFontRenderer.drawCenteredStyledScaledText("If this takes longer than", 4, width / 2, height / 2 + 35, -1);
            EnhancedFontRenderer.drawCenteredStyledScaledText(refreshTime + " seconds, please restart.", 4, width / 2, height / 2 + 70, -1);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (refreshing) return;
        if (mouseButton == 0) {
            for (int i = 0; i < cosmeticButtonList.size(); ++i) {
                GuiButton guibutton = cosmeticButtonList.get(i);
                if (guibutton.mousePressed(mc, mouseX, mouseY)) {
                    guibutton.playPressSound(mc.getSoundHandler());
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (refreshing) return;
        super.keyTyped(typedChar, keyCode);
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    private HitBox createBackgroundHitBox() {
        return new HitBox(20, 20, width - 30,height - 30);
    }

    private HitBox createBackgroundOutlineHitBox(HitBox backgroundHitBox) {
        return new HitBox(backgroundHitBox.getX(), backgroundHitBox.getY(), backgroundHitBox.getWidth() - backgroundHitBox.getX(), backgroundHitBox.getHeight() - backgroundHitBox.getY());
    }

    private void cosmeticButtons(int mouseX, int mouseY, HitBox backgroundOutlineHitBox) {
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
                            refresh(5);
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

    private void refresh(int refreshTime) {
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

}