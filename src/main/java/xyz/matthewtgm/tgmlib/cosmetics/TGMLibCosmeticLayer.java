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

package xyz.matthewtgm.tgmlib.cosmetics;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.events.TGMLibEvent;
import xyz.matthewtgm.tgmlib.util.ForgeHelper;

import java.util.List;

public class TGMLibCosmeticLayer implements LayerRenderer<AbstractClientPlayer> {

    private final CosmeticManager cosmeticManager;
    private final BaseCosmetic cosmetic;

    public TGMLibCosmeticLayer(CosmeticManager cosmeticManager, BaseCosmetic cosmetic) {
        this.cosmeticManager = cosmeticManager;
        this.cosmetic = cosmetic;
    }

    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float tickAge, float netHeadYaw, float netHeadPitch, float scale) {
        if (!TGMLib.getManager().getConfigHandler().isShowCosmetics())
            return;
        if (cosmeticManager.getCosmeticMap().containsKey(player.getUniqueID().toString())) {
            List<BaseCosmetic> cosmetics = cosmeticManager.getCosmeticMap().get(player.getUniqueID().toString()).getEnabledCosmetics();
            if (cosmetics.contains(cosmetic)) {
                if (ForgeHelper.postEvent(new TGMLibEvent.CosmeticRenderEvent(TGMLib.getInstance(), player, limbSwing, limbSwing, partialTicks, tickAge, netHeadYaw, netHeadPitch, scale, cosmetic, cosmetic.getType())))
                    return;
                cosmetic.render(player, limbSwing, limbSwingAmount, partialTicks, tickAge, netHeadYaw, netHeadPitch, scale);
            }
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }

}