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

import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.lib.TGMLib;
import xyz.matthewtgm.lib.cosmetics.BaseCosmetic;
import xyz.matthewtgm.lib.cosmetics.CosmeticManager;
import xyz.matthewtgm.lib.cosmetics.PlayerCosmeticsHolder;
import xyz.matthewtgm.lib.socket.TGMLibSocket;
import xyz.matthewtgm.lib.socket.packets.BasePacket;

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

    public void write(TGMLibSocket socket) {
        data.add("uuid", uuid);
        data.add("cosmetic", cosmeticId);
    }

    public void read(TGMLibSocket socket, JsonObject json) {
        JsonObject jsonData = json.get("data").getAsJsonObject();
        CosmeticManager cosmeticManager = TGMLib.getInstance().getCosmeticManager();
        BaseCosmetic cosmetic = cosmeticManager.getCosmeticFromId(jsonData.get("cosmetic").getAsString());
        PlayerCosmeticsHolder holder = cosmeticManager.getCosmeticMap().get(jsonData.get("uuid").getAsString());
        if (jsonData.get("toggled").getAsBoolean()) holder.getEnabledCosmetics().add(cosmetic);
        else holder.getEnabledCosmetics().remove(cosmetic);
    }

    public void handle(TGMLibSocket socket) {}

}