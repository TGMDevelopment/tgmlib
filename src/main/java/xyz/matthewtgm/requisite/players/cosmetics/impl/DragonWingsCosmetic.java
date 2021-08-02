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

package xyz.matthewtgm.requisite.players.cosmetics.impl;

import net.minecraft.util.ResourceLocation;
import xyz.matthewtgm.requisite.data.ColourRGB;
import xyz.matthewtgm.requisite.players.cosmetics.BaseWingsCosmetic;

public class DragonWingsCosmetic extends BaseWingsCosmetic {

    protected ResourceLocation texture;
    protected ColourRGB colour;

    public DragonWingsCosmetic(String name, String id, ColourRGB colour, ResourceLocation texture) {
        super(name, id);
        this.texture = texture;
        this.colour = colour;
    }

    public void tick() {}

    public ResourceLocation texture() {
        return texture;
    }

    public ColourRGB colour() {
        return colour;
    }

}