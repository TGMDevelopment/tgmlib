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

package xyz.matthewtgm.requisite.mixins.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.matthewtgm.requisite.events.PotionEffectGainedEvent;

@Mixin({EntityLivingBase.class})
public abstract class EntityLivingBaseMixin {

    @Inject(method = "addPotionEffect", at = @At("HEAD"), cancellable = true)
    private void onPotionEffectAdded(PotionEffect potioneffectIn, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new PotionEffectGainedEvent(potioneffectIn, (EntityLivingBase)(Object)this)))
            ci.cancel();
    }

}