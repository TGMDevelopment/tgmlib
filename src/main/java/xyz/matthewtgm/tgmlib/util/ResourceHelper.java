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

package xyz.matthewtgm.tgmlib.util;

import java.io.IOException;
import java.io.InputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ResourceHelper {

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

}