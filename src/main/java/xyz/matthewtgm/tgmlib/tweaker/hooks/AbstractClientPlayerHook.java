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

package xyz.matthewtgm.tgmlib.tweaker.hooks;

import net.minecraft.client.entity.AbstractClientPlayer;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.cosmetics.CosmeticManager;
import xyz.matthewtgm.tgmlib.cosmetics.PlayerCosmeticsHolder;
import xyz.matthewtgm.tgmlib.files.ConfigHandler;

public class AbstractClientPlayerHook {

    public static boolean returnValue(AbstractClientPlayer player) {
        CosmeticManager cosmeticManager = TGMLib.getManager().getCosmeticManager();
        ConfigHandler configHandler = TGMLib.getManager().getConfigHandler();
        if (configHandler.isOverrideCapes() && configHandler.isShowCosmetics()) {
            if (!cosmeticManager.getCosmeticMap().containsKey(player.getUniqueID().toString())) return false;
            PlayerCosmeticsHolder cosmeticsHolder = cosmeticManager.getCosmeticMap().get(player.getUniqueID().toString());
            return !cosmeticsHolder.getEnabledCosmetics().isEmpty();
        }
        return false;
    }

}