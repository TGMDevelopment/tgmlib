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

import xyz.matthewtgm.tgmlib.data.GifResourceLocation;

public class AnimatedCloakCosmetic extends CloakCosmetic {

    protected GifResourceLocation cachedGif;
    protected int cachedFps;
    private int tick;

    public AnimatedCloakCosmetic(String name, String id, int fps, GifResourceLocation gif) {
        super(name, id, gif.getTexture());
        this.cachedGif = gif;
        this.cachedFps = fps;
    }

    public void tick() {
        if (fpsTick()) {
            this.texture = cachedGif.update();
            tick = 0;
        }
        tick++;
    }

    private boolean fpsTick() {
        return tick % cachedFps == 0;
    }

}