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

package xyz.matthewtgm.lib.socket;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;
import xyz.matthewtgm.json.objects.JsonObject;
import xyz.matthewtgm.json.parsing.JsonParser;
import xyz.matthewtgm.json.util.JsonHelper;
import xyz.matthewtgm.lib.TGMLib;
import xyz.matthewtgm.lib.socket.packets.BasePacket;
import xyz.matthewtgm.lib.socket.packets.impl.cosmetics.CosmeticsRetrievePacket;
import xyz.matthewtgm.lib.socket.packets.impl.cosmetics.CosmeticsTogglePacket;
import xyz.matthewtgm.lib.startup.TGMLibCommand;
import xyz.matthewtgm.lib.util.ChatHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TGMLibSocket extends WebSocketClient {

    private static final BiMap<Class<? extends BasePacket>, Float> packets = HashBiMap.create();
    private final List<OpenRunnable> openListeners = new ArrayList<>();
    private final Logger logger = LogManager.getLogger(TGMLib.NAME + " (" + getClass().getSimpleName() + ")");

    public TGMLibSocket(URI serverUri) {
        super(serverUri, new Draft_6455(), new HashMap<>());
    }

    public void connect() {
        logger.info("Connecting to socket.");
        super.connect();
    }

    public void reconnect() {
        logger.info("Reconnecting to socket.");
        super.reconnect();
    }

    public void onOpen(ServerHandshake handshake) {
        logger.info("Connected to socket!");
        for (OpenRunnable runnable : openListeners) runnable.run(this);
    }

    public void onMessage(String message) {
        handleMessage(message);
    }

    public void onClose(int code, String reason, boolean remote) {
        logger.warn("Connection to socket was closed! ({} | {})", code, reason);
        reconnect();
    }

    public void onError(Exception ex) {
        if (ex instanceof WebsocketNotConnectedException) {
            reconnect();
            return;
        }

        logger.error("An unexpected error occurred!", ex);
        ChatHandler.sendMessage(ChatHandler.tgmLibChatPrefix, String.format("%s%sAn exception was thrown from the TGMLib WebSocket!", EnumChatFormatting.RED, EnumChatFormatting.BOLD));
    }

    public void send(BasePacket packet) {
        packet.write(this);
        super.send(packet.toJson().toJson());
    }

    private void handleMessage(String message) {
        if (!JsonHelper.isValidJson(message)) return;
        JsonObject<String, Object> packet = JsonParser.parseObj(message);
        if (TGMLibCommand.logPackets) logger.warn(JsonHelper.makePretty(packet));
        Class<? extends BasePacket> packetClazz = packets.inverse().get(packet.getAsFloat("id"));
        BasePacket thePacket = null;
        try {
            thePacket = packetClazz == null ? null : packetClazz.newInstance();
            if (thePacket == null) return;
            thePacket.handle(this);
            thePacket.read(this, packet);
        } catch (Exception e) {
            logger.error("There was an unexpected error D:", e);
        }
    }

    public void addOpenListener(OpenRunnable runnable) {
        openListeners.add(runnable);
    }

    public void removeOpenListener(OpenRunnable runnable) {
        openListeners.remove(runnable);
    }

    static {
        packets.put(CosmeticsRetrievePacket.class, 0f);
        packets.put(CosmeticsTogglePacket.class, 1f);
    }

    public interface OpenRunnable {
        void run(TGMLibSocket socket);
    }

}