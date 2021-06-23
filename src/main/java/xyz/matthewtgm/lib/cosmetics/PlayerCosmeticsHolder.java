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

package xyz.matthewtgm.lib.cosmetics;

import lombok.Getter;
import lombok.Setter;
import xyz.matthewtgm.json.objects.JsonObject;
import xyz.matthewtgm.lib.TGMLib;
import xyz.matthewtgm.lib.socket.packets.impl.cosmetics.CosmeticsRetrievePacket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerCosmeticsHolder {

    @Getter
    private final String uuid;
    @Getter @Setter
    private List<BaseCosmetic> ownedCosmetics = new ArrayList<>();
    @Getter @Setter
    private List<BaseCosmetic> enabledCosmetics = new ArrayList<>();

    public PlayerCosmeticsHolder(String uuid) {
        this.uuid = uuid;
    }

    public PlayerCosmeticsHolder(String uuid, List<BaseCosmetic> ownedCosmetics, List<BaseCosmetic> enabledCosmetics) {
        this.uuid = uuid;
        this.ownedCosmetics.addAll(ownedCosmetics);
        this.enabledCosmetics.addAll(enabledCosmetics);
    }

    public void get() {
        TGMLib.getInstance().getWebSocket().send(new CosmeticsRetrievePacket(uuid));
    }

    public JsonObject<String, Object> toJson() {
        return new JsonObject<>().add("uuid", uuid).add("owned", ownedCosmetics).add("enabled", enabledCosmetics);
    }

    public String toString() {
        return toJson().toJson();
    }

}