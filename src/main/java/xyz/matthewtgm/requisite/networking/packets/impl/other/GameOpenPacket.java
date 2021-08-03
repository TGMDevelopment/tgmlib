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

package xyz.matthewtgm.requisite.networking.packets.impl.other;

import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.requisite.networking.RequisiteClientSocket;
import xyz.matthewtgm.requisite.networking.packets.BasePacket;

public class GameOpenPacket extends BasePacket {

    private final String uuid;

    public GameOpenPacket(String uuid) {
        super("OPEN", "GAME", 0f);
        this.uuid = uuid;
    }

    public GameOpenPacket() {
        this(null);
    }

    public void write(RequisiteClientSocket socket) {
        data.add("uuid", uuid);
    }

    public void read(RequisiteClientSocket socket, JsonObject object, JsonObject data) {}

    public void handle(RequisiteClientSocket socket) {}

}