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

package xyz.matthewtgm.requisite.players;

import lombok.Getter;
import lombok.Setter;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.requisite.players.cosmetics.BaseCosmetic;
import xyz.matthewtgm.requisite.players.cosmetics.CosmeticType;

import java.util.ArrayList;
import java.util.List;

public class PlayerCosmeticData {

    @Getter
    private final String uuid;
    @Getter @Setter
    private List<BaseCosmetic> ownedCosmetics = new ArrayList<>();
    @Getter @Setter
    private List<BaseCosmetic> enabledCosmetics = new ArrayList<>();
    @Getter
    private final List<BaseCosmetic> enabledCloakCosmetics = new ArrayList<>();

    public PlayerCosmeticData(String uuid, List<BaseCosmetic> ownedCosmetics, List<BaseCosmetic> enabledCosmetics) {
        this.uuid = uuid;
        this.ownedCosmetics.addAll(ownedCosmetics);
        this.enabledCosmetics.addAll(enabledCosmetics);
        for (BaseCosmetic enabledCosmetic : enabledCosmetics)
            if (enabledCosmetic.getType().equals(CosmeticType.CLOAK))
                enabledCloakCosmetics.add(enabledCosmetic);
    }

    public JsonObject toJson() {
        return new JsonObject().add("uuid", uuid).add("owned", ownedCosmetics).add("enabled", enabledCosmetics);
    }

    public String toString() {
        return toJson().getAsString();
    }

}