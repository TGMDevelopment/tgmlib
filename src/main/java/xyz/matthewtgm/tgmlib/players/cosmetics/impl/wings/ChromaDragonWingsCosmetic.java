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

package xyz.matthewtgm.tgmlib.players.cosmetics.impl.wings;

import net.minecraft.util.ResourceLocation;
import xyz.matthewtgm.tgmlib.players.cosmetics.BaseWingsCosmetic;
import xyz.matthewtgm.tgmlib.data.ColourRGB;
import xyz.matthewtgm.tgmlib.util.ColourHelper;
import xyz.matthewtgm.tgmlib.util.ResourceHelper;

public class ChromaDragonWingsCosmetic extends BaseWingsCosmetic {

    public ChromaDragonWingsCosmetic() {
        super("Chroma Dragon Wings", "CHROMA_DRAGON_WINGS");
    }

    public ResourceLocation texture() {
        return ResourceHelper.get("tgmlib", "cosmetics/wings/dragon_wings.png");
    }

    public ColourRGB colour() {
        ColourRGB value = new ColourRGB(ColourHelper.timeBasedChroma());
        value.setA(255);
        return value;

    }

    public void tick() {}

}