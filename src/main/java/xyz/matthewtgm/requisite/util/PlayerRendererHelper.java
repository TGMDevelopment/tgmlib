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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public final class PlayerRendererHelper {

    private PlayerRendererHelper() {}

    /**
     * Adds a new layer to the player class.
     *
     * @param layer the layer to be added.
     * @author MatthewTGM
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