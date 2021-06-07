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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.net.URI;

public class ResourceHelper {

    public static ResourceLocation download(String name, String url) {
        DynamicTexture texture;
        try {
            texture = new DynamicTexture(ImageIO.read(URI.create(url).toURL()));
        } catch (Exception e) {
            e.printStackTrace();
            texture = new DynamicTexture(16, 16);
        }
        return Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(name, texture);
    }

}