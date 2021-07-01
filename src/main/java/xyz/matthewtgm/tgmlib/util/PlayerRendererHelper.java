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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

/**
 * Makes methods regarding the client player and other players easier to access.
 */
public class PlayerRendererHelper {

    /**
     * Adds a new layer to the player class.
     *
     * @param layer the layer to be added.
     */
    public static void addLayer(LayerRenderer<?> layer) {
        try {
            RenderPlayer renderPlayerDefault = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default");
            RenderPlayer renderPlayerSlim = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim");

            renderPlayerDefault.addLayer(layer);
            renderPlayerSlim.addLayer(layer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}