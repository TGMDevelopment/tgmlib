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

package xyz.matthewtgm.tgmlib.socket.packets.impl.cosmetics;

import xyz.matthewtgm.json.entities.JsonArray;
import xyz.matthewtgm.json.entities.JsonElement;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.cosmetics.BaseCosmetic;
import xyz.matthewtgm.tgmlib.cosmetics.CosmeticManager;
import xyz.matthewtgm.tgmlib.cosmetics.PlayerCosmeticsHolder;
import xyz.matthewtgm.tgmlib.socket.TGMLibSocket;
import xyz.matthewtgm.tgmlib.socket.packets.BasePacket;

import java.util.ArrayList;
import java.util.List;

public class CosmeticsRetrievePacket extends BasePacket {

    private String uuid;

    public CosmeticsRetrievePacket(String uuid) {
        super("RETRIEVE", "COSMETICS", 0f);
        this.uuid = uuid;
    }

    public CosmeticsRetrievePacket() {
        this(null);
    }

    public void write(TGMLibSocket socket) {
        data.add("uuid", uuid);
    }

    public void read(TGMLibSocket socket, JsonObject json) {
        CosmeticManager cosmeticManager = TGMLib.getManager().getCosmeticManager();
        JsonObject jsonData = json.get("data").getAsJsonObject();
        List<BaseCosmetic> ownedCosmetics = new ArrayList<>();
        List<BaseCosmetic> enabledCosmetics = new ArrayList<>();
        JsonArray array = new JsonArray();
        for (JsonElement element : jsonData.get("cosmetics").getAsJsonArray()) ownedCosmetics.add(cosmeticManager.getCosmeticFromId(element.toString()));
        for (JsonElement element : jsonData.get("enabled_cosmetics").getAsJsonArray()) enabledCosmetics.add(cosmeticManager.getCosmeticFromId(element.toString()));
        ownedCosmetics.removeIf(cosmetic -> cosmetic == null);
        enabledCosmetics.removeIf(cosmetic -> cosmetic == null);
        cosmeticManager.getCosmeticMap().put(jsonData.get("uuid").toString(), new PlayerCosmeticsHolder(jsonData.get("uuid").toString(), ownedCosmetics, enabledCosmetics));
    }

    public void handle(TGMLibSocket socket) {}

}