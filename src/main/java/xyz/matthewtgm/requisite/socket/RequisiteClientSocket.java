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

package xyz.matthewtgm.requisite.socket;

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
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.listeners.ListenerManager;
import xyz.matthewtgm.requisite.listeners.ListenerType;
import xyz.matthewtgm.requisite.socket.packets.BasePacket;
import xyz.matthewtgm.requisite.socket.packets.impl.announcer.AnnouncementPacket;
import xyz.matthewtgm.requisite.socket.packets.impl.cosmetics.CosmeticsRetrievePacket;
import xyz.matthewtgm.requisite.socket.packets.impl.cosmetics.CosmeticsTogglePacket;
import xyz.matthewtgm.requisite.socket.packets.impl.indication.RetrieveIndicationsPacket;
import xyz.matthewtgm.requisite.socket.packets.impl.other.GameClosePacket;
import xyz.matthewtgm.requisite.socket.packets.impl.other.GameOpenPacket;
import xyz.matthewtgm.requisite.util.ChatHelper;
import xyz.matthewtgm.requisite.util.Multithreading;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RequisiteClientSocket extends WebSocketClient {

    private static final BiMap<Class<? extends BasePacket>, Float> packets = HashBiMap.create();
    private final List<RequisiteClientSocket.OpenRunnable> openListeners = new ArrayList<>();
    private final Logger logger = LogManager.getLogger(Requisite.NAME + " (" + getClass().getSimpleName() + ")");

    public RequisiteClientSocket(URI serverUri) {
        super(serverUri, new Draft_6455(), new HashMap<>());
    }

    public void connect() {
        logger.info("Connecting to socket.");
        for (ListenerManager.ListenerRunnable runnable : ListenerManager.getListeners().get(ListenerType.WEBSOCKET_CONNECT))
            runnable.run(this);
        super.connect();
    }

    public void reconnect() {
        logger.info("Reconnecting to socket.");
        for (ListenerManager.ListenerRunnable runnable : ListenerManager.getListeners().get(ListenerType.WEBSOCKET_RECONNECT))
            runnable.run(this);
        new Thread(super::reconnect).start();
    }

    public void onOpen(ServerHandshake handshake) {
        logger.info("Connected to socket!");
        for (RequisiteClientSocket.OpenRunnable runnable : openListeners) runnable.run(this);
    }

    public void onMessage(String message) {
        handleMessage(message);
    }

    public void onClose(int code, String reason, boolean remote) {
        logger.warn("Connection to socket was closed! ({} | {})", code, reason);
    }

    public void onError(Exception ex) {
        if (ex instanceof WebsocketNotConnectedException) {
            reconnect();
            return;
        }

        if (ex.getMessage().contains("WebSocketClient objects are not reuseable")) {
            Multithreading.schedule(Requisite.getManager()::fixSocket, 3, TimeUnit.SECONDS);
            return;
        }

        logger.error("An unexpected error occurred!", ex);
        ChatHelper.sendMessage(ChatHelper.requisiteChatPrefix, String.format("%s%sAn exception was thrown from the Requisite WebSocket!", EnumChatFormatting.RED, EnumChatFormatting.BOLD));
    }

    public void send(BasePacket packet) {
        try {
            if (!isOpen())
                return;
            for (ListenerManager.ListenerRunnable runnable : ListenerManager.getListeners().get(ListenerType.WEBSOCKET_SEND))
                runnable.run(this);
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

    public RequisiteClientSocket addOpenListener(RequisiteClientSocket.OpenRunnable runnable) {
        openListeners.add(runnable);
        return this;
    }

    public RequisiteClientSocket removeOpenListener(RequisiteClientSocket.OpenRunnable runnable) {
        openListeners.remove(runnable);
        return this;
    }

    static {
        /* Cosmetics. */
        packets.put(CosmeticsRetrievePacket.class, 0f);
        packets.put(CosmeticsTogglePacket.class, 1f);

        /* Announcer. */
        packets.put(AnnouncementPacket.class, 2f);

        /* Game. */
        packets.put(GameOpenPacket.class, 5f);
        packets.put(GameClosePacket.class, 6f);

        /* Indications. */
        packets.put(RetrieveIndicationsPacket.class, 7f);
    }

    public interface OpenRunnable {
        void run(RequisiteClientSocket socket);
    }

}