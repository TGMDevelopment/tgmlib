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

package xyz.matthewtgm.requisite.networking.packets.impl.indication;

import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.networking.RequisiteClientSocket;
import xyz.matthewtgm.requisite.networking.packets.BasePacket;

public class RetrieveIndicationsPacket extends BasePacket {

    public RetrieveIndicationsPacket() {
        super("RETRIEVE", "INDICATIONS", 3f);
    }

    public void write(RequisiteClientSocket socket) {}

    public void read(RequisiteClientSocket socket, JsonObject object, JsonObject data) {
        Requisite.getManager().getIndicatorManager().getIndicatorArray().clear();
        Requisite.getManager().getIndicatorManager().getIndicatorArray().addAll(data.getAsArray("indications"));
    }

    public void handle(RequisiteClientSocket socket) {}

}