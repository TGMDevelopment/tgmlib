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

package xyz.matthewtgm.lib.other;

import net.minecraft.util.ResourceLocation;

public class AnimatedResourceLocation {

    private final int fpt;

    private int currentTick = 0;
    private int currentFrame = 0;

    private final ResourceLocation[] textures;

    public AnimatedResourceLocation(String folder, int frames, int fpt) {
        this.fpt = fpt;
        textures = new ResourceLocation[frames];
        for(int i = 0; i < frames; i++) textures[i] = new ResourceLocation(folder + "/" + i + ".png");
    }

    public ResourceLocation getFrame() {
        return textures[currentFrame];
    }

    public void update() {
        if(currentTick > fpt) {
            currentTick = 0;
            currentFrame++;
            if(currentFrame > textures.length - 1) {
                currentFrame = 0;
            }
        }
        currentTick++;
    }

}