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

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import xyz.matthewtgm.lib.util.ArrayHelper;
import xyz.matthewtgm.lib.util.ImageHelper;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class GifResourceLocation {

    @Getter
    private final InputStream gif;
    private final int fps;
    private final ResourceLocation[] textures;
    private int frames;
    private int currentTick = 0;
    private int currentFrame = 0;

    public GifResourceLocation(InputStream gif, int fpt) {
        this.gif = gif;
        this.fps = fpt;
        BufferedImage[] gifFrames = ImageHelper.getGifFrames(gif);
        this.frames = gifFrames.length;
        ResourceLocation[] newTextures = new ResourceLocation[gifFrames.length];
        for (BufferedImage gifFrame : gifFrames) {
            int index = Arrays.asList(gifFrames).indexOf(gifFrame);
            newTextures[index] = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation(index + ".gif", new DynamicTexture(gifFrame));
        }
        textures = newTextures;
    }

    public GifResourceLocation(ByteArrayInputStream gif) {
        this(gif, 1);
    }

    public ResourceLocation getTexture() {
        return textures[currentFrame];
    }

    public void update() {
        if (currentTick > fps) {
            currentTick = 0;
            currentFrame++;
            if (currentFrame > frames - 1) {
                currentFrame = 0;
            }
        }
        currentTick++;
    }

}