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

package xyz.matthewtgm.requisite.util;

import java.io.IOException;
import java.io.InputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import xyz.matthewtgm.requisite.util.global.GlobalMinecraft;

public final class ResourceHelper {

    private ResourceHelper() {}

    /**
     * @param domain The domain of the resource.
     * @param path The path of the resource.
     * @return The generated resourcelocation.
     * @author MatthewTGM
     */
    public static ResourceLocation get(String domain, String path) {
        return new ResourceLocation(domain, path);
    }

    /**
     * @param path The path of the resource.
     * @return The generated resourcelocation.
     * @author MatthewTGM
     */
    public static ResourceLocation get(String path) {
        return new ResourceLocation(path);
    }

    /**
     * @param rl The resourcelocation to convert.
     * @return The converted input stream.
     * @author MatthewTGM
     */
    public static InputStream toInputStream(ResourceLocation rl) {
        try {
            return Minecraft.getMinecraft().getResourceManager().getResource(rl).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param resourceLocation The resource location to check for.
     * @param url The url to download from.
     * @return The ThreadDownloadImageData object.
     * @author Minecraft/Mojang (see {@link net.minecraft.client.entity.AbstractClientPlayer#getDownloadImageSkin(ResourceLocation, String)})
     */
    public static ThreadDownloadImageData downloadImage(ResourceLocation resourceLocation, String url) {
        TextureManager textureManager = GlobalMinecraft.getTextureManager();
        ITextureObject textureObject = textureManager.getTexture(resourceLocation);
        if (textureObject == null) {
            textureObject = new ThreadDownloadImageData(null, url, resourceLocation, new ImageBufferDownload());
            textureManager.loadTexture(resourceLocation, textureObject);
        }
        return (ThreadDownloadImageData) textureObject;
    }

}