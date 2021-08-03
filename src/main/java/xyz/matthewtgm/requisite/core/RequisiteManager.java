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

package xyz.matthewtgm.requisite.core;

import lombok.Getter;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.framing.CloseFrame;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.json.util.JsonApiHelper;
import xyz.matthewtgm.json.util.JsonHelper;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.gui.menus.GuiRequisiteLogging;
import xyz.matthewtgm.requisite.keybinds.KeyBindManager;
import xyz.matthewtgm.requisite.players.PlayerDataManager;
import xyz.matthewtgm.requisite.players.cosmetics.CosmeticManager;
import xyz.matthewtgm.requisite.data.VersionChecker;
import xyz.matthewtgm.requisite.files.ConfigHandler;
import xyz.matthewtgm.requisite.files.DataHandler;
import xyz.matthewtgm.requisite.files.FileHandler;
import xyz.matthewtgm.requisite.keybinds.KeyBindConfigHandler;
import xyz.matthewtgm.requisite.players.indicators.IndicatorManager;
import xyz.matthewtgm.requisite.networking.RequisiteClientSocket;
import xyz.matthewtgm.requisite.networking.packets.impl.other.GameClosePacket;
import xyz.matthewtgm.requisite.networking.packets.impl.other.GameOpenPacket;
import xyz.matthewtgm.requisite.util.*;
import xyz.matthewtgm.requisite.util.global.GlobalMinecraft;

import java.net.URI;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class RequisiteManager {

    private static boolean webSocketTest = false;
    private static boolean webSocketDebug = false;

    private static final Logger logger = LogManager.getLogger(Requisite.NAME + " (Manager)");

    @Getter private VersionChecker versionChecker;
    @Getter private FileHandler fileHandler;
    @Getter private ConfigHandler configHandler;
    @Getter private KeyBindConfigHandler keyBindConfigHandler;
    @Getter private DataHandler dataHandler;
    @Getter private RequisiteClientSocket webSocket;
    @Getter private PlayerDataManager dataManager;
    @Getter private CosmeticManager cosmeticManager;
    @Getter private IndicatorManager indicatorManager;
    @Getter private final KeyBindManager keyBindManager = new KeyBindManager();

    public void initialize() {
        checkJvmProperties();
    }

    private void checkJvmProperties() {
        try {
            if (!webSocketTest)
                webSocketTest = Boolean.parseBoolean(System.getProperty("requisiteWebSocketTest", "false"));
            if (!webSocketDebug)
                webSocketDebug = Boolean.parseBoolean(System.getProperty("requisiteWebSocketDebug", "false"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            versionChecker = new VersionChecker("https://raw.githubusercontent.com/TGMDevelopment/RequisiteData/main/versions.json", true).setFetchListener((versionChecker, versionObject) -> {
                if (!versionChecker.isLatestVersion("latest", Requisite.VER) && !ForgeHelper.isDevelopmentEnvironment())
                    ChatHelper.sendMessage(ChatHelper.requisiteChatPrefix, ChatColour.RED + "There's a new version of Requisite! You should probably restart your game.");
            });
            (fileHandler = new FileHandler()).start();
            (configHandler = new ConfigHandler("config", fileHandler.getRequisiteDir())).start();
            (keyBindConfigHandler = new KeyBindConfigHandler("keybinds", fileHandler.getRequisiteDir())).update();
            (dataHandler = new DataHandler("data", fileHandler.getRequisiteDir())).start();
            fixSocket();
            if (!webSocket.isOpen())
                scheduleSocketReconnect();
            if (dataHandler.isMayLogData())
                webSocket.send(new GameOpenPacket(GlobalMinecraft.getSession().getProfile().getId().toString()));
            (dataManager = new PlayerDataManager()).start();
            (cosmeticManager = new CosmeticManager()).start();
            (indicatorManager = new IndicatorManager()).start();

            ForgeHelper.registerEventListener(this);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (dataHandler.isMayLogData())
                    webSocket.send(new GameClosePacket(GlobalMinecraft.getSession().getProfile().getId().toString()));
                webSocket.close(CloseFrame.NORMAL, "Game shutdown");
            }, Requisite.NAME + " Shutdown"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private URI websocketUri() {
        if (webSocketTest)
            return URI.create("ws://localhost:8080");
        JsonObject object = JsonApiHelper.getJsonObject("https://raw.githubusercontent.com/TGMDevelopment/RequisiteData/main/websocket.json", true);
        String uri = object.get("uri").getAsString();
        if (webSocketDebug) {
            logger.info(JsonHelper.makePretty(object));
            logger.info("Pre: " + uri);
        }
        for (int i = 0; i < object.get("loop").getAsInt(); i++)
            uri = new String(Base64.getDecoder().decode(uri));
        if (webSocketDebug) {
            logger.info("Post: " + uri);
        }
        return URI.create(uri);
    }

    private RequisiteClientSocket createWebSocket(RequisiteClientSocket original) {
        original.setConnectionLostTimeout(120);
        original.setTcpNoDelay(true);
        return original;
    }

    public void fixSocket() {
        try {
            if (!createSocket()) {
                Notifications.push("Failed to connect to " + Requisite.NAME + " WebSocket!", "Click me to attempt a reconnect.", notification -> {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean createSocket() throws Exception {
        return (webSocket = createWebSocket(new RequisiteClientSocket(websocketUri()))).connectBlocking(15, TimeUnit.SECONDS);
    }

    public void scheduleSocketReconnect() {
        Multithreading.schedule(new Thread(() -> {
            try {
                if (!webSocket.reconnectBlocking())
                    scheduleSocketReconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Requisite WebSocket Reconnect Thread"), 15, TimeUnit.MINUTES);
    }

    /**
     * Permits Requisite to open it's data logging menu on initial run.
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGuiOpen(GuiScreenEvent.InitGuiEvent event) {
        if (event.gui instanceof GuiMainMenu && !dataHandler.isReceivedPrompt())
            GlobalMinecraft.displayGuiScreen(new GuiRequisiteLogging(event.gui));
    }

}