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

package xyz.matthewtgm.requisite.mixins.player;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.files.ConfigHandler;
import xyz.matthewtgm.requisite.players.PlayerCosmeticData;

@Mixin({AbstractClientPlayer.class})
public abstract class AbstractClientPlayerMixin extends EntityPlayer {

    public AbstractClientPlayerMixin(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }

    @Inject(method = "getLocationCape", at = @At("HEAD"), cancellable = true)
    private void onCapeLocationGotten(CallbackInfoReturnable<ResourceLocation> cir) {
        ConfigHandler configHandler = Requisite.getManager().getConfigHandler();
        if (configHandler.isOverrideCapes() && configHandler.isShowCosmetics()) {
            if (!Requisite.getManager().getDataManager().getDataMap().containsKey(getUniqueID().toString()))
                return;
            PlayerCosmeticData cosmeticsHolder = Requisite.getManager().getDataManager().getDataMap().get(getUniqueID().toString()).getCosmeticData();
            if (cosmeticsHolder != null && !cosmeticsHolder.getEnabledCloakCosmetics().isEmpty()) {
                cir.setReturnValue(null);
            }
        }
    }

}