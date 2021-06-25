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

package xyz.matthewtgm.lib.cosmetics.impl.wings;

import net.minecraft.util.ResourceLocation;
import xyz.matthewtgm.lib.cosmetics.BaseWingsCosmetic;
import xyz.matthewtgm.lib.other.ColourRGB;
import xyz.matthewtgm.lib.util.ResourceCaching;

public class WyvestWingsCosmetic extends BaseWingsCosmetic {

    public WyvestWingsCosmetic() {
        super("Wyvest Wings", "WYVEST_WINGS");
    }

    public ResourceLocation texture() {
        return ResourceCaching.getFromCache("TGMLib", "wyvest_wings.png");
    }

    public void tick() {}

    public ColourRGB colour() {
        return new ColourRGB(255, 255, 255);
    }

}