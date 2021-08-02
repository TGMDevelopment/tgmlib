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

package xyz.matthewtgm.requisite.tweaker.hooks;

import net.minecraft.client.entity.AbstractClientPlayer;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.players.PlayerCosmeticData;
import xyz.matthewtgm.requisite.files.ConfigHandler;

public class AbstractClientPlayerHook {

    public static boolean returnValue(AbstractClientPlayer player) {
        ConfigHandler configHandler = Requisite.getManager().getConfigHandler();
        if (configHandler.isOverrideCapes() && configHandler.isShowCosmetics()) {
            if (!Requisite.getManager().getDataManager().getDataMap().containsKey(player.getUniqueID().toString()))
                return false;
            PlayerCosmeticData cosmeticsHolder = Requisite.getManager().getDataManager().getDataMap().get(player.getUniqueID().toString()).getCosmeticData();
            if (cosmeticsHolder == null || cosmeticsHolder.getEnabledCloakCosmetics() == null)
                return false;
            return !cosmeticsHolder.getEnabledCloakCosmetics().isEmpty();
        }
        return false;
    }

}