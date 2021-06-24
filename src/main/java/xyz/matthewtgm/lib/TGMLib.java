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

package xyz.matthewtgm.lib;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import xyz.matthewtgm.lib.cosmetics.CosmeticManager;
import xyz.matthewtgm.lib.gui.menus.GuiCosmeticSelector;
import xyz.matthewtgm.lib.other.HitBox;
import xyz.matthewtgm.lib.socket.TGMLibSocket;
import xyz.matthewtgm.lib.startup.StartupRegistry;
import xyz.matthewtgm.lib.util.*;
import xyz.matthewtgm.lib.util.betterkeybinds.KeyBindManager;

import java.net.URI;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Mod(name = TGMLib.NAME, version = TGMLib.VERSION, modid = TGMLib.ID)
public class TGMLib {

    public static final String NAME = "TGMLib", VERSION = "@VER@", ID = "tgmlib";
    @Mod.Instance
    private static TGMLib INSTANCE;
    private final URI webSocketUri;
    @Getter
    private TGMLibSocket webSocket;
    @Getter
    private final CosmeticManager cosmeticManager;
    private final Logger logger = LogManager.getLogger("TGMLib");

    public TGMLib() {
        webSocketUri = URI.create(new String(Base64.getDecoder().decode(new String(Base64.getDecoder().decode(new String(Base64.getDecoder().decode(new String(Base64.getDecoder().decode("V2tST1RrNXJlRFZQU0doUFZrZGtNVlJ0Y0hKa1ZURlZVMWh3VFdGclZYcFVibkIyWlZVeGNXRjZVVDA9")))))))));
        webSocket = new TGMLibSocket(webSocketUri);
        addSocketSettings(webSocket);
        cosmeticManager = new CosmeticManager();
        webSocket.addOpenListener(socket -> cosmeticManager.start());
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        StartupRegistry startupRegistry = new StartupRegistry();
        startupRegistry.init(logger);

        logger.info("Registering listeners...");
        ForgeUtils.registerEventListeners(startupRegistry, new KeyBindManager(), new ScreenHelper(), new GuiHelper(), new GuiHelper.Editor(), new HypixelHelper(), new TitleHandler(), new Notifications(), new MessageQueue());
        logger.info("Listeners registered!");

        GuiHelper.Editor.addEdit(GuiIngameMenu.class, new GuiHelper.Editor.GuiEditRunnable() {
            public void init(GuiScreen screen, List<GuiButton> buttonList) {
                HitBox cosmeticsButtonHitBox = generateCosmeticsButtonHitBox(screen);
                buttonList.add(new GuiButton(763454237, (int) cosmeticsButtonHitBox.getX(), (int) cosmeticsButtonHitBox.getY(), (int) cosmeticsButtonHitBox.getWidth(), (int) cosmeticsButtonHitBox.getHeight(), "TGMLib Cosmetics") {
                    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                        if (super.mousePressed(mc, mouseX, mouseY)) mc.displayGuiScreen(new GuiCosmeticSelector(screen));
                        return false;
                    }
                });
            }
            public void draw(GuiScreen screen, int mouseX, int mouseY, float partialTicks) {
                HitBox cosmeticsButtonHitBox = generateCosmeticsButtonHitBox(screen);
                if (cosmeticsButtonHitBox.isInBounds(mouseX, mouseY))
                    GuiHelper.drawTooltip(Arrays.asList("Click to edit your cosmetics in TGMLib!"), mouseX, mouseY);
            }
            private HitBox generateCosmeticsButtonHitBox(GuiScreen screen) {
                return new HitBox(screen.width / 2 - 50, screen.height - 22, 100, 20);
            }
        });
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        ProgressManager.ProgressBar progressBar = ProgressManager.push("TGMLib - Initialization", 1);
        progressBar.step("Downloading/loading resources");
        logger.info("Downloading/loading resources...");
        ResourceCaching.download("TGMLib", "button_light.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/button_light.png");
        ResourceCaching.download("TGMLib", "button_dark.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/button_dark.png");
        ResourceCaching.download("TGMLib", "switch_on.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/config_framework/switch_on.png");
        ResourceCaching.download("TGMLib", "switch_off.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/config_framework/switch_off.png");

        /* Cosmetics. */
        ResourceCaching.download("TGMLib", "cosmetics/cloaks", "developer_cloak.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/cosmetics/cloaks/developer_cloak.png");
        ResourceCaching.download("TGMLib", "cosmetics/cloaks/exclusive", "johnny_jth_cloak.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/cosmetics/cloaks/exclusive/johnny_jth_cloak.png");
        ResourceCaching.download("TGMLib", "cosmetics/cloaks", "minecoin_cloak.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/cosmetics/cloaks/minecoin_cloak.png");
        ResourceCaching.download("TGMLib", "cosmetics/cloaks", "partner_cloak.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/cosmetics/cloaks/partner_cloak.png");
        ResourceCaching.download("TGMLib", "cosmetics/cloaks/exclusive", "wyvest_cloak.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/cosmetics/cloaks/exclusive/wyvest_cloak.png");

        ResourceCaching.download("TGMLib", "cosmetics/wings", "dragon_wings.png", "https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/resources/cosmetics/wings/dragon_wings.png");
        logger.info("Resources downloaded!");
        ProgressManager.pop(progressBar);

        webSocket.connect();
    }

    public void addSocketSettings(WebSocketClient socketClient) {
        socketClient.setConnectionLostTimeout(Integer.MAX_VALUE);
        socketClient.setTcpNoDelay(true);
    }

    public static TGMLib getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TGMLib();
        return INSTANCE;
    }

}