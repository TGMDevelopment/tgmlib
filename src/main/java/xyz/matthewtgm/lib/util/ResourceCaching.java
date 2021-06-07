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

package xyz.matthewtgm.lib.util;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResourceCaching {

    @Getter
    private static final Map<String, Map<String, ResourceLocation>> cache = new HashMap<>();

    public static ResourceLocation download(String modName, String resourceName, String url) {
        cache.putIfAbsent(modName, new HashMap<>());
        File modAssets = new File(Minecraft.getMinecraft().mcDataDir, "mod_assets");
        if (!modAssets.exists()) if (!modAssets.mkdirs()) throw new IllegalStateException("Failed to create mod_assets directory in the Minecraft data directory.");
        File theModsAssets = new File(modAssets, modName);
        if (!theModsAssets.exists()) if (!theModsAssets.mkdirs()) throw new IllegalStateException("Failed to create " + modName + "'s assets directory.");

        File resourceFile = new File(theModsAssets, resourceName);
        if (resourceFile.exists()) {
            try {
                return cache.get(modName).putIfAbsent(resourceName, Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(resourceName, new DynamicTexture(ImageIO.read(resourceFile))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (cache.get(modName).containsKey(resourceName)) return cache.get(modName).get(resourceName);

        DynamicTexture texture = downloadResourceToFile(resourceFile, url);
        return cache.get(modName).putIfAbsent(resourceName, Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(resourceName, texture));
    }

    public static ResourceLocation getFromCache(String modName, String resourceName) {
        return cache.get(modName).get(resourceName);
    }

    private static DynamicTexture downloadResourceToFile(File resourceFile, String url) {
        try {
            return createFile(resourceFile, url);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to download resource to file. (" + resourceFile + " | " + url + ")");
        }
    }

    private static DynamicTexture createFile(File file, String url) {
        try {
            BufferedImage image = ImageIO.read(new URL(url));
            ImageIO.write(image, "png", file);
            return new DynamicTexture(image);
        } catch (Exception e) {
            e.printStackTrace();
            return new DynamicTexture(16, 16);
        }
    }

}