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

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class GifResourceLocation {

    @Getter
    private final File gif;
    private final int fps;
    private int frames;

    private int currentTick = 0;
    private int currentFrame = 0;

    @Getter private int width;
    @Getter private int height;

    private final ResourceLocation[] textures;

    public GifResourceLocation(File gif, int fpt) {
        this.gif = gif;
        this.fps = fpt;
        ResourceLocation[] newTextures;
        try {
            InputStream stream = new FileInputStream(gif);
            ImageInputStream imageStream = ImageIO.createImageInputStream(stream);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageStream);
            if (!readers.hasNext()) throw new IOException("No suitable reader found for image" + gif);
            ImageReader reader = readers.next();
            reader.setInput(imageStream);
            int frames = reader.getNumImages(true);
            this.frames = frames;
            BufferedImage[] images = new BufferedImage[frames];
            for(int i = 0; i < frames; i++) images[i] = reader.read(i);
            BufferedImage first = images[0];
            this.width = first.getWidth();
            this.height = first.getHeight();
            reader.dispose();
            newTextures = new ResourceLocation[frames];
            for (int i = 0; i < images.length; i++) newTextures[i] = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation(frames + ".png", new DynamicTexture(images[i]));
        } catch (Exception e) {
            e.printStackTrace();
            newTextures = new ResourceLocation[256];
        }
        textures = newTextures;
    }

    public ResourceLocation getTexture() {
        return textures[currentFrame];
    }

    public void update() {
        if(currentTick > fps) {
            currentTick = 0;
            currentFrame++;
            if(currentFrame > frames - 1) {
                currentFrame = 0;
            }
        }
        currentTick++;
    }

}