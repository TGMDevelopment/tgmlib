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

package xyz.matthewtgm.requisite.networking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.networking.packets.BasePacket;
import xyz.matthewtgm.requisite.util.ChatColour;
import xyz.matthewtgm.requisite.util.ChatHelper;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Center of all Requisite networking.
 */
public final class RequisiteClientSocket extends WebSocketClient {

    private final Logger logger = LogManager.getLogger(Requisite.NAME + " (" + getClass().getSimpleName() + ")");
    private final RequisitePacketHandler packetHandler;

    public RequisiteClientSocket(URI serverUri) {
        super(serverUri, new Draft_6455());
        this.packetHandler = new RequisitePacketHandler(this);
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
    }

    /**
     * This is required for some reason..?
     */
    public void onMessage(String message) {
        packetHandler.handle(logger, StandardCharsets.UTF_8.encode(message));
    }

    /**
     * Invoked when a message from the server is sent. The parameter value is parsed using the {@link RequisitePacketHandler}
     */
    public void onMessage(ByteBuffer bytes) {
        packetHandler.handle(logger, bytes);
    }

    public void onClose(int code, String reason, boolean remote) {
        logger.warn("Connection to socket was closed! ({} | {})", code, reason);
    }

    public void onError(Exception ex) {
        if (packetHandler.exception(ex)) {
            logger.error("An unexpected error occurred!", ex);
            ChatHelper.sendMessage(ChatHelper.requisiteChatPrefix, String.format("%s%sAn exception was thrown from the Requisite WebSocket! %s()", ChatColour.RED, ChatColour.BOLD, ChatColour.DARK_RED));
        }
    }

    public void send(BasePacket packet) {
        packetHandler.send(packet);
    }

}