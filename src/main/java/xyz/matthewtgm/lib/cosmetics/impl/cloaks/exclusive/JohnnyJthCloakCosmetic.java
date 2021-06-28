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

package xyz.matthewtgm.lib.cosmetics.impl.cloaks.exclusive;

import net.minecraft.util.ResourceLocation;
import xyz.matthewtgm.lib.Constants;
import xyz.matthewtgm.lib.cosmetics.BaseCloakCosmetic;

public class JohnnyJthCloakCosmetic extends BaseCloakCosmetic {

    public JohnnyJthCloakCosmetic() {
        super("Johnny_JTH Cloak", "JOHNNY_JTH_CLOAK");
    }

    public ResourceLocation texture() {
        return Constants.generateCosmeticLocation("cloaks", "exclusive/johnny_jth_cloak.png");
    }

    public void tick() {}

}