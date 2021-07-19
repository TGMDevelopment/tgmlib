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

package xyz.matthewtgm.tgmlib.players.cosmetics;

import net.minecraft.util.ResourceLocation;
import xyz.matthewtgm.tgmlib.data.GifResourceLocation;

public abstract class BaseAnimatedCloakCosmetic extends BaseCloakCosmetic {

    protected GifResourceLocation cachedGif;
    protected int cachedFps = -1;
    private int tick;

    public BaseAnimatedCloakCosmetic(String name, String id) {
        super(name, id);
    }

    public abstract GifResourceLocation gif();
    public abstract int fps();

    public ResourceLocation texture() {
        cache();
        return cachedGif.getTexture();
    }

    public void tick() {
        cache();
        if (fpsTick()) {
            cachedGif.update();
            tick = 0;
        }
        tick++;
    }

    private boolean fpsTick() {
        cache();
        return tick % cachedFps == 0;
    }

    private void cache() {
        if (cachedFps <= -1) cachedFps = fps();
        if (cachedGif == null) cachedGif = gif();
    }

}