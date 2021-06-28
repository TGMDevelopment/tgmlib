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

package xyz.matthewtgm.lib.cosmetics.impl.cloaks.partners;

import org.apache.commons.compress.utils.IOUtils;
import xyz.matthewtgm.lib.Constants;
import xyz.matthewtgm.lib.cosmetics.BaseAnimatedCloakCosmetic;
import xyz.matthewtgm.lib.other.GifResourceLocation;
import xyz.matthewtgm.lib.util.ResourceHelper;

import java.io.ByteArrayInputStream;

public class DarkCheeseIglooCloakCosmetic extends BaseAnimatedCloakCosmetic {

    public DarkCheeseIglooCloakCosmetic() {
        super("DarkCheese's Igloo Cloak", "DARK_CHEESE_IGLOO_CLOAK");
    }

    public GifResourceLocation gif() {
        return new GifResourceLocation(new ByteArrayInputStream(ResourceHelper.toInputStream(Constants.generateCosmeticLocation("cloaks", "partners/darkcheese_igloo.gif")).toString().getBytes()));
    }

    public int fps() {
        return 3;
    }

}