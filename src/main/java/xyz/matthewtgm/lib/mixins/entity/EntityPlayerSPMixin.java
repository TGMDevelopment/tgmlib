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

package xyz.matthewtgm.lib.mixins.entity;

import xyz.matthewtgm.lib.events.DropItemEvent;
import xyz.matthewtgm.lib.events.SendChatMessageEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityPlayerSP.class})
public class EntityPlayerSPMixin {

    @Inject(method = "dropOneItem", at = @At("HEAD"), cancellable = true)
    protected void onDropOneItem(boolean dropAll, CallbackInfoReturnable<EntityItem> cir) {
        DropItemEvent.Pre event = new DropItemEvent.Pre(Minecraft.getMinecraft().thePlayer.getHeldItem(), dropAll);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            cir.cancel();
    }

    @Inject(method = "dropOneItem", at = @At("TAIL"), cancellable = true)
    protected void onDropOneItem_post(boolean dropAll, CallbackInfoReturnable<EntityItem> cir) {
        DropItemEvent.Post event = new DropItemEvent.Post(dropAll);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            cir.cancel();
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    protected void onChatMessageSent(String message, CallbackInfo ci) {
        SendChatMessageEvent.Pre event = new SendChatMessageEvent.Pre(message, false);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            ci.cancel();
    }

}