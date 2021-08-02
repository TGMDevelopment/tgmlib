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

import xyz.matthewtgm.requisite.data.GifResourceLocation;

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