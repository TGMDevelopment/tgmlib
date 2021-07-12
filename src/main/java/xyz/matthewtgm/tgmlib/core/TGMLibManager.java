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
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import xyz.matthewtgm.json.entities.JsonArray;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.json.util.JsonApiHelper;
import xyz.matthewtgm.tgmconfig.TGMConfig;
import xyz.matthewtgm.tgmlib.cosmetics.CosmeticManager;
import xyz.matthewtgm.tgmlib.files.ConfigHandler;
import xyz.matthewtgm.tgmlib.files.DataHandler;
import xyz.matthewtgm.tgmlib.files.FileHandler;
import xyz.matthewtgm.tgmlib.keybinds.KeyBindConfigHandler;
import xyz.matthewtgm.tgmlib.profiles.ProfileManager;
import xyz.matthewtgm.tgmlib.socket.TGMLibSocket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.other.GameClosePacket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.other.GameOpenPacket;
import xyz.matthewtgm.tgmlib.util.Multithreading;
import xyz.matthewtgm.tgmlib.util.global.GlobalMinecraft;

import java.io.File;
import java.net.URI;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class TGMLibManager {

    private static final boolean webSocketTest = false;
    @Getter
    private static boolean initialized;
    @Getter
    private static File mcDir, tgmLibDir;

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
    private CosmeticManager cosmeticManager;
    @Getter
    private ProfileManager profileManager;

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
            (cosmeticManager = new CosmeticManager()).start();
            //(profileManager = new ProfileManager()).start();

            if (dataHandler.isMayLogData()) {
                JsonArray modList = new JsonArray();
                for (ModContainer modContainer : Loader.instance().getActiveModList())
                    modList.add(modContainer.getName() == null || modContainer.getName().isEmpty() ? modContainer.getModId() : modContainer.getName());
                webSocket.send(new GameOpenPacket(GlobalMinecraft.getSession().getProfile().getId().toString(), modList));
            }
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (dataHandler.isMayLogData())
                    webSocket.send(new GameClosePacket(GlobalMinecraft.getSession().getProfile().getId().toString()));
                webSocket.close(5, "Game shutdown");
            }, "TGMLib Shutdown"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private URI websocketUri() {
        if (webSocketTest)
            return URI.create("ws://localhost:INSERT-PORT");
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
            (webSocket = createWebSocket(new TGMLibSocket(websocketUri()))).connectBlocking(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
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