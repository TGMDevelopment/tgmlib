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

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.apache.logging.log4j.Logger;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import xyz.matthewtgm.json.entities.JsonElement;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.json.parser.JsonParser;
import xyz.matthewtgm.json.util.JsonHelper;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.networking.packets.BasePacket;
import xyz.matthewtgm.requisite.networking.packets.impl.announcer.AnnouncementPacket;
import xyz.matthewtgm.requisite.networking.packets.impl.cosmetics.CosmeticsRetrievePacket;
import xyz.matthewtgm.requisite.networking.packets.impl.cosmetics.CosmeticsTogglePacket;
import xyz.matthewtgm.requisite.networking.packets.impl.indication.RetrieveIndicationsPacket;
import xyz.matthewtgm.requisite.networking.packets.impl.other.GameClosePacket;
import xyz.matthewtgm.requisite.networking.packets.impl.other.GameOpenPacket;
import xyz.matthewtgm.requisite.util.Multithreading;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public final class RequisitePacketHandler {

    private static final BiMap<Class<? extends BasePacket>, Float> packets = HashBiMap.create();
    private final RequisiteClientSocket client;

    public RequisitePacketHandler(RequisiteClientSocket client) {
        this.client = client;
    }

    public void send(BasePacket packet) {
        try {
            if (!client.isOpen())
                return;
            packet.handle(client);
            packet.write(client);
            client.send(compress(packet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handle(Logger logger, ByteBuffer buffer) {
        String parsed = StandardCharsets.UTF_8.decode(buffer).toString();
        if (!JsonHelper.isValidJson(parsed))
            return;
        JsonObject object = JsonParser.parse(parsed).getAsJsonObject();
        if (!object.hasKey("id") || !object.hasKey("data"))
            return;
        Class<? extends BasePacket> found = packets.inverse().get(object.getAsFloat("id"));
        if (found == null)
            return;
        try {
            BasePacket packet = found.newInstance();
            packet.handle(client);
            packet.read(client, object, object.getAsObject("data"));
        } catch (Exception e) {
            logger.error("An unexpected exception occurred in the Requisite packet handler.", e);
        }
    }

    public boolean exception(Throwable throwable) {
        if (throwable instanceof WebsocketNotConnectedException) {
            client.reconnect();
            return false;
        }

        if (throwable.getMessage().contains("WebSocketClient objects are not reuseable")) {
            Multithreading.schedule(Requisite.getManager()::fixSocket, 3, TimeUnit.SECONDS);
            return false;
        }

        return true;
    }

    public byte[] compress(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }

    private byte[] compress(JsonElement element) {
        return compress(element.getAsString());
    }

    public byte[] compress(BasePacket packet) {
        return compress(packet.jsonify());
    }

    static {
        packets.put(GameOpenPacket.class, 0f);
        packets.put(GameClosePacket.class, 1f);

        packets.put(AnnouncementPacket.class, 2f);

        packets.put(RetrieveIndicationsPacket.class, 3f);

        packets.put(CosmeticsRetrievePacket.class, 6f);
        packets.put(CosmeticsTogglePacket.class, 7f);
    }

}