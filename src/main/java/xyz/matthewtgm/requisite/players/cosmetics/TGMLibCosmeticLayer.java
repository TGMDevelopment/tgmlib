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

package xyz.matthewtgm.requisite.players.cosmetics;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.events.RequisiteEvent;
import xyz.matthewtgm.requisite.players.PlayerData;
import xyz.matthewtgm.requisite.util.ForgeHelper;

import java.util.List;

public class TGMLibCosmeticLayer implements LayerRenderer<AbstractClientPlayer> {

    private final BaseCosmetic cosmetic;

    public TGMLibCosmeticLayer(BaseCosmetic cosmetic) {
        this.cosmetic = cosmetic;
    }

    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float tickAge, float netHeadYaw, float netHeadPitch, float scale) {
        if (!Requisite.getManager().getConfigHandler().isShowCosmetics())
            return;
        if (Requisite.getManager().getDataManager().getDataMap().containsKey(player.getUniqueID().toString())) {
            PlayerData playerData = Requisite.getManager().getDataManager().getDataMap().get(player.getUniqueID().toString());
            if (playerData != null && playerData.getCosmeticData() != null && (playerData.getCosmeticData().getEnabledCosmetics() != null && !playerData.getCosmeticData().getEnabledCosmetics().isEmpty())) {
                List<BaseCosmetic> cosmetics = Requisite.getManager().getDataManager().getDataMap().get(player.getUniqueID().toString()).getCosmeticData().getEnabledCosmetics();
                if (cosmetics.contains(cosmetic)) {
                    if (ForgeHelper.postEvent(new RequisiteEvent.CosmeticRenderEvent(Requisite.getInstance(), player, limbSwing, limbSwing, partialTicks, tickAge, netHeadYaw, netHeadPitch, scale, cosmetic, cosmetic.getType())))
                        return;
                    cosmetic.render(player, limbSwing, limbSwingAmount, partialTicks, tickAge, netHeadYaw, netHeadPitch, scale);
                }
            }
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }

}