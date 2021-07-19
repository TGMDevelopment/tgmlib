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

package xyz.matthewtgm.tgmlib.core;

import lombok.Getter;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.matthewtgm.json.entities.JsonArray;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.json.util.JsonApiHelper;
import xyz.matthewtgm.tgmconfig.TGMConfig;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.players.PlayerDataManager;
import xyz.matthewtgm.tgmlib.players.cosmetics.CosmeticManager;
import xyz.matthewtgm.tgmlib.data.VersionChecker;
import xyz.matthewtgm.tgmlib.files.ConfigHandler;
import xyz.matthewtgm.tgmlib.files.DataHandler;
import xyz.matthewtgm.tgmlib.files.FileHandler;
import xyz.matthewtgm.tgmlib.gui.menus.GuiTGMLibLogging;
import xyz.matthewtgm.tgmlib.keybinds.KeyBindConfigHandler;
import xyz.matthewtgm.tgmlib.players.indicators.IndicatorManager;
import xyz.matthewtgm.tgmlib.socket.TGMLibSocket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.other.GameClosePacket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.other.GameOpenPacket;
import xyz.matthewtgm.tgmlib.util.ChatHelper;
import xyz.matthewtgm.tgmlib.util.ForgeHelper;
import xyz.matthewtgm.tgmlib.util.Multithreading;
import xyz.matthewtgm.tgmlib.util.Notifications;
import xyz.matthewtgm.tgmlib.util.global.GlobalMinecraft;

import java.io.File;
import java.net.URI;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class TGMLibManager {

    private static final boolean webSocketTest = true;
    @Getter
    private static boolean initialized;
    @Getter
    private static File mcDir, tgmLibDir;

    @Getter
    private VersionChecker versionChecker;
    @Getter
    private FileHandler fileHandler;
    @Getter
    private TGMConfig config;
    @Getter
    private TGMConfig keyBindConfig;
    @Getter
    private TGMConfig data;
    @Getter
    private ConfigHandler configHandler;
    @Getter
    private KeyBindConfigHandler keyBindConfigHandler;
    @Getter
    private DataHandler dataHandler;
    @Getter
    private TGMLibSocket webSocket;
    @Getter
    private PlayerDataManager dataManager;
    @Getter
    private CosmeticManager cosmeticManager;
    @Getter
    private IndicatorManager indicatorManager;

    public void initialize(File mcDir) {
        if (initialized)
            return;
        TGMLibManager.mcDir = mcDir;
        tgmLibDir = new File(mcDir, "TGMLib");
        if (!tgmLibDir.exists() && !tgmLibDir.mkdirs())
            throw new IllegalStateException("Couldn't create TGMLib directory.");
        initialized = true;
    }

    public void start() {
        try {
            versionChecker = new VersionChecker("https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/versions.json", true).setFetchListener((versionChecker, versionObject) -> {
                if (!versionChecker.isLatestVersion("latest", TGMLib.VER) && !ForgeHelper.isDevelopmentEnvironment())
                    ChatHelper.sendMessage("There's a new version of TGMLib! You should probably restart your game.");
            });
            (fileHandler = new FileHandler()).start();
            (config = new TGMConfig("config", fileHandler.getTgmLibDir())).save();
            (keyBindConfig = new TGMConfig("keybinds", fileHandler.getTgmLibDir())).save();
            (data = new TGMConfig("data", fileHandler.getTgmLibDir())).save();
            (configHandler = new ConfigHandler(config)).start();
            (keyBindConfigHandler = new KeyBindConfigHandler(keyBindConfig)).update();
            (dataHandler = new DataHandler(data)).start();
            fixSocket();
            if (!webSocket.isOpen())
                scheduleSocketReconnect();
            if (dataHandler.isMayLogData())
                webSocket.send(new GameOpenPacket(GlobalMinecraft.getSession().getProfile().getId().toString()));
            (dataManager = new PlayerDataManager()).start();
            (cosmeticManager = new CosmeticManager()).start();
            (indicatorManager = new IndicatorManager()).start();

            ForgeHelper.registerEventListeners(this);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (dataHandler.isMayLogData())
                    webSocket.send(new GameClosePacket(GlobalMinecraft.getSession().getProfile().getId().toString()));
                webSocket.close(5, "Game shutdown");
            }, "TGMLib Shutdown"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGuiOpen(GuiScreenEvent.InitGuiEvent event) {
        if (event.gui instanceof GuiMainMenu && !dataHandler.isReceivedPrompt())
            GlobalMinecraft.displayGuiScreen(new GuiTGMLibLogging(event.gui));
    }

    private URI websocketUri() {
        if (webSocketTest)
            return URI.create("ws://localhost:2298");
        JsonObject object = JsonApiHelper.getJsonObject("https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/websocket.json", true);
        String uri = object.get("uri").toString();
        for (int i = 0; i < object.get("loop").getAsInt(); i++)
            uri = new String(Base64.getDecoder().decode(uri));
        return URI.create(uri);
    }

    private TGMLibSocket createWebSocket(TGMLibSocket original) {
        original.setConnectionLostTimeout(120);
        original.setTcpNoDelay(true);
        return original;
    }

    public void fixSocket() {
        try {
            if (!createSocket())
                Notifications.push("Failed to connect to TGMLib WebSocket!", "Click me to attempt a reconnect.", notification -> {
                    try {
                        String originalTitle = notification.title;
                        String originalDescription = notification.description;
                        notification.title = "Reconnecting...";
                        notification.description = "";
                        if (!webSocket.reconnectBlocking()) {
                            notification.title = "Failed to reconnect! D:";

                            Notifications.Notification resubmitted = notification.clone();
                            resubmitted.title = originalTitle;
                            resubmitted.description = originalDescription;
                            Notifications.push(resubmitted);
                        } else
                            notification.title = "Reconnected successfully!";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean createSocket() throws Exception {
        return (webSocket = createWebSocket(new TGMLibSocket(websocketUri()))).connectBlocking(15, TimeUnit.SECONDS);
    }

    public void scheduleSocketReconnect() {
        Multithreading.schedule(new Thread(() -> {
            try {
                webSocket.reconnectBlocking();
                if (!webSocket.isOpen())
                    scheduleSocketReconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "TGMLib WebSocket Reconnect Thread"), 15, TimeUnit.MINUTES);
    }

}