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

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import xyz.matthewtgm.lib.TGMLib;
import xyz.matthewtgm.lib.cosmetics.BaseCloakCosmetic;
import xyz.matthewtgm.lib.other.GifResourceLocation;
import xyz.matthewtgm.lib.util.ResourceCaching;

import java.io.File;
import java.util.Arrays;

public class DarkCheeseIglooCloakCosmetic extends BaseCloakCosmetic {

    private final GifResourceLocation texture;

    public DarkCheeseIglooCloakCosmetic() {
        super("DarkCheese's Igloo Cloak", "DARK_CHEESE_IGLOO_CLOAK");
        System.out.println(Arrays.asList(new File(Minecraft.getMinecraft().mcDataDir, "mod_assets/" + TGMLib.NAME + "/cosmetics/cloaks/partners").listFiles()));
        this.texture = new GifResourceLocation(ResourceCaching.getFromCache("TGMLib", "darkcheese_igloo.gif"), new File(Minecraft.getMinecraft().mcDataDir, "mod_assets/" + TGMLib.NAME + "/cosmetics/cloaks/partners"), 1);
    }

    public ResourceLocation texture() {
        return texture.getTexture();
    }

    public void tick() {
        texture.update();
    }

}