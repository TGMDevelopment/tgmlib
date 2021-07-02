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

package xyz.matthewtgm.tgmlib.socket;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.json.parser.JsonParser;
import xyz.matthewtgm.json.util.JsonHelper;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.socket.packets.BasePacket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.announcer.AnnouncementPacket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.cosmetics.CosmeticsRetrievePacket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.cosmetics.CosmeticsTogglePacket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.profiles.OnlineStatusUpdatePacket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.profiles.PrivateMessagePacket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.profiles.RetrieveProfilePacket;
import xyz.matthewtgm.tgmlib.util.ChatHelper;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TGMLibSocket extends WebSocketClient {

    private static final BiMap<Class<? extends BasePacket>, Float> packets = HashBiMap.create();
    private final List<TGMLibSocket.OpenRunnable> openListeners = new ArrayList<>();
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
        new Thread(super::reconnect).start();
    }

    public void onOpen(ServerHandshake handshake) {
        logger.info("Connected to socket!");
        for (TGMLibSocket.OpenRunnable runnable : openListeners) runnable.run(this);
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
        ChatHelper.sendMessage(ChatHelper.tgmLibChatPrefix, String.format("%s%sAn exception was thrown from the TGMLib WebSocket!", EnumChatFormatting.RED, EnumChatFormatting.BOLD));
    }

    public void send(BasePacket packet) {
        try {
            if (!isOpen()) reconnectBlocking();
            if (!isOpen()) return;
            packet.write(this);
            super.send(packet.toJson().getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(String message) {
        if (!JsonHelper.isValidJson(message)) return;
        JsonObject packet = (JsonObject) JsonParser.parse(message);
        Class<? extends BasePacket> packetClazz = packets.inverse().get(packet.get("id").getAsFloat());
        BasePacket thePacket;
        try {
            thePacket = packetClazz == null ? null : packetClazz.newInstance();
            if (thePacket == null) return;
            thePacket.handle(this);
            thePacket.read(this, packet);
        } catch (Exception e) {
            logger.error("There was an unexpected error D:", e);
        }
    }

    public void addOpenListener(TGMLibSocket.OpenRunnable runnable) {
        openListeners.add(runnable);
    }

    public void removeOpenListener(TGMLibSocket.OpenRunnable runnable) {
        openListeners.remove(runnable);
    }

    static {
        /* Cosmetics. */
        packets.put(CosmeticsRetrievePacket.class, 0f);
        packets.put(CosmeticsTogglePacket.class, 1f);

        /* Announcer. */
        packets.put(AnnouncementPacket.class, 2f);

        /* Profiles. */
        packets.put(RetrieveProfilePacket.class, 3f);
        packets.put(PrivateMessagePacket.class, 4f);
        packets.put(OnlineStatusUpdatePacket.class, 5f);
    }

    public interface OpenRunnable {
        void run(TGMLibSocket socket);
    }

}