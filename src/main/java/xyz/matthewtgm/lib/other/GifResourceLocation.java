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
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.asm.FMLSanityChecker;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;

public class GifResourceLocation {

    @Getter
    private final ResourceLocation gifLocation;
    @Getter
    private final File location;
    private final int fps;
    private int frames;

    private int currentTick = 0;
    private int currentFrame = 0;

    @Getter private int width;
    @Getter private int height;

    private final ResourceLocation[] textures;

    public GifResourceLocation(ResourceLocation gifLocation, File location, int fpt) {
        this.gifLocation = gifLocation;
        this.location = location;
        this.fps = fpt;
        ResourceLocation[] newTextures;
        try {
            InputStream stream = open(gifLocation);
            ImageInputStream imageStream = ImageIO.createImageInputStream(stream);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageStream);
            if (!readers.hasNext()) throw new IOException("No suitable reader found for image" + gifLocation);
            ImageReader reader = readers.next();
            reader.setInput(imageStream);
            int frames = reader.getNumImages(true);
            this.frames = frames;
            System.out.println(frames);
            BufferedImage[] images = new BufferedImage[frames];
            System.out.println(Arrays.asList(images));
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

    private InputStream open(ResourceLocation loc) throws IOException {
        IResourcePack mcPack = getMcPack();
        IResourcePack miscPack = getMiscPack();
        IResourcePack fmlPack = getFmlPack();
        IResourcePack locationPack = getLocationPack();
        if (miscPack.resourceExists(loc)) return miscPack.getInputStream(loc);
        else if (fmlPack.resourceExists(loc)) return fmlPack.getInputStream(loc);
        else if (locationPack.resourceExists(loc)) return locationPack.getInputStream(loc);
        return mcPack.getInputStream(loc);
    }

    private IResourcePack getMcPack() {
        return Minecraft.getMinecraft().mcDefaultResourcePack;
    }

    private IResourcePack getMiscPack() {
        return createResourcePack(new File(Minecraft.getMinecraft().mcDataDir, "resources"));
    }

    private IResourcePack getFmlPack() {
        return createResourcePack(FMLSanityChecker.fmlLocation);
    }

    private IResourcePack getLocationPack() {
        return createResourcePack(location);
    }

    private IResourcePack createResourcePack(File file) {
        if (file.isDirectory()) return new FolderResourcePack(file);
        else return new FileResourcePack(file);
    }

}