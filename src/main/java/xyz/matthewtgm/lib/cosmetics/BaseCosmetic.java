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
import net.minecraft.client.entity.AbstractClientPlayer;
import xyz.matthewtgm.json.objects.JsonObject;

public abstract class BaseCosmetic {

    @Getter
    private final String name, id;
    @Getter
    private final CosmeticType type;
    @Getter
    private JsonObject<String, Object> data;

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