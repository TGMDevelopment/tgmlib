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

package xyz.matthewtgm.tgmlib.socket.packets;

import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.tgmlib.socket.TGMLibSocket;

public abstract class BasePacket {

    public final String name;
    public final String type;
    public final JsonObject data;
    public final float id;

    public BasePacket(String name, String type, JsonObject data, float id) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.id = id;
    }

    public BasePacket(String name, String type, float id) {
        this(name, type, new JsonObject(), id);
    }

    public abstract void write(TGMLibSocket socket);
    public abstract void read(TGMLibSocket socket, JsonObject json);
    public abstract void handle(TGMLibSocket socket);

    public void error(String name, String reason) {
        data.add("ERROR", new JsonObject()
                .add("name", name)
                .add("reason", reason));
    }

    public JsonObject toJson() {
        JsonObject value = new JsonObject();
        value.add("name", name);
        value.add("type", type);
        value.add("data", data);
        value.add("id", id);
        return value;
    }

}