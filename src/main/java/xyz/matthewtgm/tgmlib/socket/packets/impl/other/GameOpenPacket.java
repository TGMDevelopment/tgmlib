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

import xyz.matthewtgm.json.entities.JsonArray;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.tgmlib.socket.TGMLibSocket;
import xyz.matthewtgm.tgmlib.socket.packets.BasePacket;

public class GameOpenPacket extends BasePacket {

    private final String uuid;
    private final JsonArray modList;

    public GameOpenPacket(String uuid, JsonArray modList) {
        super("OPEN", "GAME", 8f);
        this.uuid = uuid;
        this.modList = modList;
    }

    public GameOpenPacket() {
        this(null, null);
    }

    public void write(TGMLibSocket socket) {
        data.add("uuid", uuid);
        data.add("mod_list", modList);
    }

    public void read(TGMLibSocket socket, JsonObject json) {

    }

    public void handle(TGMLibSocket socket) {}

}