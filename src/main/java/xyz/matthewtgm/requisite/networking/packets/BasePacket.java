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

package xyz.matthewtgm.requisite.networking.packets;

import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.requisite.networking.RequisiteClientSocket;

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

    public abstract void write(RequisiteClientSocket socket);
    public abstract void read(RequisiteClientSocket socket, JsonObject object, JsonObject data);
    public abstract void handle(RequisiteClientSocket socket);

    public void error(String name, String reason) {
        data.add("ERROR", new JsonObject()
                .add("name", name)
                .add("reason", reason));
    }

    public JsonObject jsonify() {
        JsonObject value = new JsonObject();
        value.add("name", name);
        value.add("type", type);
        value.add("data", data);
        value.add("id", id);
        return value;
    }

}