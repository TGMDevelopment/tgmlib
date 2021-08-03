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

package xyz.matthewtgm.requisite.networking.packets.impl.cosmetics;

import xyz.matthewtgm.json.entities.JsonElement;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.players.PlayerCosmeticData;
import xyz.matthewtgm.requisite.players.PlayerData;
import xyz.matthewtgm.requisite.players.cosmetics.BaseCosmetic;
import xyz.matthewtgm.requisite.players.cosmetics.CosmeticManager;
import xyz.matthewtgm.requisite.networking.RequisiteClientSocket;
import xyz.matthewtgm.requisite.networking.packets.BasePacket;

import java.util.ArrayList;
import java.util.List;

public class CosmeticsRetrievePacket extends BasePacket {

    private String uuid;

    public CosmeticsRetrievePacket(String uuid) {
        super("RETRIEVE", "COSMETICS", 6f);
        this.uuid = uuid;
    }

    public CosmeticsRetrievePacket() {
        this(null);
    }

    public void write(RequisiteClientSocket socket) {
        data.add("uuid", uuid);
    }

    public void read(RequisiteClientSocket socket, JsonObject object, JsonObject data) {
        CosmeticManager cosmeticManager = Requisite.getManager().getCosmeticManager();
        List<BaseCosmetic> ownedCosmetics = new ArrayList<>();
        List<BaseCosmetic> enabledCosmetics = new ArrayList<>();
        for (JsonElement element : data.get("cosmetics").getAsJsonArray())
            ownedCosmetics.add(cosmeticManager.getCosmeticFromId(element.getAsString()));
        for (JsonElement element : data.get("enabled_cosmetics").getAsJsonArray())
            enabledCosmetics.add(cosmeticManager.getCosmeticFromId(element.getAsString()));
        ownedCosmetics.removeIf(cosmetic -> cosmetic == null);
        enabledCosmetics.removeIf(cosmetic -> cosmetic == null);
        if (!Requisite.getManager().getDataManager().getDataMap().containsKey(data.get("uuid").getAsString()))
            Requisite.getManager().getDataManager().getDataMap().put(data.get("uuid").getAsString(), new PlayerData());
        Requisite.getManager().getDataManager().getDataMap().get(data.get("uuid").getAsString()).setCosmeticData(new PlayerCosmeticData(data.get("uuid").getAsString(), ownedCosmetics, enabledCosmetics));
    }

    public void handle(RequisiteClientSocket socket) {}

}