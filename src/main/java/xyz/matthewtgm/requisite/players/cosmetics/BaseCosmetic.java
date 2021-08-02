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

package xyz.matthewtgm.requisite.players.cosmetics;

import lombok.Getter;
import net.minecraft.client.entity.AbstractClientPlayer;

public abstract class BaseCosmetic {

    @Getter
    private final String name, id;
    @Getter
    private final CosmeticType type;

    public BaseCosmetic(String name, String id, CosmeticType type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public BaseCosmetic(String name, String id, int typeId) {
        this(name, id, CosmeticType.fromId(typeId));
    }

    public abstract void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float tickAge, float netHeadYaw, float netHeadPitch, float scale);
    public abstract void tick();

}