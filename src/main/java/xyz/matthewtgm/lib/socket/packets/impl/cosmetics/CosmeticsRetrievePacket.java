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

package xyz.matthewtgm.lib.socket.packets.impl.cosmetics;

import net.minecraft.client.Minecraft;
import xyz.matthewtgm.json.objects.JsonObject;
import xyz.matthewtgm.lib.TGMLib;
import xyz.matthewtgm.lib.cosmetics.BaseCosmetic;
import xyz.matthewtgm.lib.cosmetics.CosmeticManager;
import xyz.matthewtgm.lib.cosmetics.PlayerCosmeticsHolder;
import xyz.matthewtgm.lib.socket.TGMLibSocket;
import xyz.matthewtgm.lib.socket.packets.BasePacket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public void read(TGMLibSocket socket, JsonObject<String, Object> json) {
        CosmeticManager cosmeticManager = TGMLib.getInstance().getCosmeticManager();
        JsonObject<String, Object> jsonData = json.getAsJsonObject("data");
        List<BaseCosmetic> ownedCosmetics = new ArrayList<>();
        List<BaseCosmetic> enabledCosmetics = new ArrayList<>();
        for (Object o : jsonData.getAsJsonArray("cosmetics")) ownedCosmetics.add(cosmeticManager.getCosmeticFromId(o.toString()));
        for (Object o : jsonData.getAsJsonArray("enabled_cosmetics")) enabledCosmetics.add(cosmeticManager.getCosmeticFromId(o.toString()));
        ownedCosmetics.removeIf(cosmetic -> cosmetic == null);
        enabledCosmetics.removeIf(cosmetic -> cosmetic == null);
        cosmeticManager.getCosmeticMap().put(jsonData.getAsString("uuid"), new PlayerCosmeticsHolder(jsonData.getAsString("uuid"), ownedCosmetics, enabledCosmetics));
    }

    public void handle(TGMLibSocket socket) {}

}