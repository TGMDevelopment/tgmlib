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
import net.minecraft.util.Timer;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.json.util.JsonApiHelper;
import xyz.matthewtgm.tgmconfig.TGMConfig;
import xyz.matthewtgm.tgmlib.cosmetics.CosmeticManager;
import xyz.matthewtgm.tgmlib.files.ConfigHandler;
import xyz.matthewtgm.tgmlib.files.FileHandler;
import xyz.matthewtgm.tgmlib.keybinds.KeyBindConfigHandler;
import xyz.matthewtgm.tgmlib.profiles.ProfileManager;
import xyz.matthewtgm.tgmlib.socket.TGMLibSocket;

import java.io.File;
import java.net.URI;
import java.util.Base64;

public class TGMLibManager {

    private static final boolean webSocketTest = false;
    @Getter private static boolean initialized;
    @Getter private static File mcDir, tgmLibDir;

    @Getter private FileHandler fileHandler;
    @Getter private TGMConfig config;
    @Getter private TGMConfig keyBindConfig;
    @Getter private ConfigHandler configHandler;
    @Getter private KeyBindConfigHandler keyBindConfigHandler;
    @Getter private TGMLibSocket webSocket;
    @Getter private CosmeticManager cosmeticManager;
    @Getter private ProfileManager profileManager;

    public void initialize(File mcDir) {
        if (initialized) return;
        TGMLibManager.mcDir = mcDir;
        tgmLibDir = new File(mcDir, "TGMLib");
        if (!tgmLibDir.exists() && !tgmLibDir.mkdirs()) throw new IllegalStateException("Couldn't create TGMLib directory.");
        initialized = true;
    }

    public void start() {
        try {
            (fileHandler = new FileHandler()).start();
            (config = new TGMConfig("config", fileHandler.getTgmLibDir())).save();
            (keyBindConfig = new TGMConfig("keybinds", fileHandler.getTgmLibDir())).save();
            (configHandler = new ConfigHandler(config)).start();
            (keyBindConfigHandler = new KeyBindConfigHandler(keyBindConfig)).update();
            (webSocket = createWebSocket(new TGMLibSocket(websocketUri()))).connectBlocking();
            (cosmeticManager = new CosmeticManager()).start();
            //(profileManager = new ProfileManager()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private URI websocketUri() {
        if (webSocketTest) return URI.create("ws://localhost:");
        JsonObject object = JsonApiHelper.getJsonObject("https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/websocket.json", true);
        String uri = object.get("uri").getAsString();
        for (int i = 0; i < object.get("loop").getAsInt(); i++) uri = new String(Base64.getDecoder().decode(uri));
        return URI.create(uri);
    }

    private TGMLibSocket createWebSocket(TGMLibSocket original) {
        original.setConnectionLostTimeout(Integer.MAX_VALUE / 2);
        original.setTcpNoDelay(true);
        return original;
    }

}