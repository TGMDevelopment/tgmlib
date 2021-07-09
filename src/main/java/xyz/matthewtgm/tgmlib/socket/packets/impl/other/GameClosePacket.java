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

package xyz.matthewtgm.tgmlib.socket.packets.impl.other;

import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.tgmlib.socket.TGMLibSocket;
import xyz.matthewtgm.tgmlib.socket.packets.BasePacket;

public class GameClosePacket extends BasePacket {

    private final String uuid;

    public GameClosePacket(String uuid) {
        super("CLOSE", "GAME", 9f);
        this.uuid = uuid;
    }

    public GameClosePacket() {
        this(null);
    }

    public void write(TGMLibSocket socket) {
        data.add("uuid", uuid);
    }

    public void read(TGMLibSocket socket, JsonObject json) {

    }

    public void handle(TGMLibSocket socket) {}

}