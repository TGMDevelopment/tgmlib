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

package xyz.matthewtgm.requisite.socket.packets.impl.cosmetics;

import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.players.PlayerCosmeticData;
import xyz.matthewtgm.requisite.players.cosmetics.BaseCosmetic;
import xyz.matthewtgm.requisite.players.cosmetics.CosmeticManager;
import xyz.matthewtgm.requisite.socket.RequisiteClientSocket;
import xyz.matthewtgm.requisite.socket.packets.BasePacket;

public class CosmeticsTogglePacket extends BasePacket {

    private final String uuid;
    private final String cosmeticId;

    public CosmeticsTogglePacket(String uuid, String cosmeticId) {
        super("TOGGLE", "COSMETICS", 1f);
        this.uuid = uuid;
        this.cosmeticId = cosmeticId;
    }

    public CosmeticsTogglePacket() {
        this(null, null);
    }

    public void write(RequisiteClientSocket socket) {
        data.add("uuid", uuid);
        data.add("cosmetic", cosmeticId);
    }

    public void read(RequisiteClientSocket socket, JsonObject json) {
        JsonObject jsonData = json.get("data").getAsJsonObject();
        CosmeticManager cosmeticManager = Requisite.getManager().getCosmeticManager();
        BaseCosmetic cosmetic = cosmeticManager.getCosmeticFromId(jsonData.get("cosmetic").toString());
        PlayerCosmeticData data = Requisite.getManager().getDataManager().getDataMap().get(jsonData.get("uuid").toString()).getCosmeticData();
        if (jsonData.get("toggled").getAsBoolean())
            data.getEnabledCosmetics().add(cosmetic);
        else
            data.getEnabledCosmetics().remove(cosmetic);
    }

    public void handle(RequisiteClientSocket socket) {}

}