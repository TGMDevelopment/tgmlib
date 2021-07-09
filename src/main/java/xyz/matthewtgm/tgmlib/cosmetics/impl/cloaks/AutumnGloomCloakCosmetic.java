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

package xyz.matthewtgm.tgmlib.cosmetics.impl.cloaks;

import xyz.matthewtgm.tgmlib.cosmetics.BaseAnimatedCloakCosmetic;
import xyz.matthewtgm.tgmlib.data.GifResourceLocation;
import xyz.matthewtgm.tgmlib.util.ResourceHelper;

public class AutumnGloomCloakCosmetic extends BaseAnimatedCloakCosmetic {

    public AutumnGloomCloakCosmetic() {
        super("Autumn Gloom Cloak", "AUTUMN_GLOOM_CLOAK");
    }

    public GifResourceLocation gif() {
        return new GifResourceLocation(ResourceHelper.get("tgmlib", "cosmetics/cloaks/autumn_gloom_cloak.gif"));
    }

    public int fps() {
        return 1;
    }

}