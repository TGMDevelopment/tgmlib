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

package xyz.matthewtgm.requisite.mixins.rendering;

import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.matthewtgm.requisite.events.FontRendererEvent;

@Mixin({FontRenderer.class})
public class FontRendererMixin {

    @Inject(method = "renderString", at = @At("HEAD"), cancellable = true)
    private void onStringRendered(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Integer> cir) {
        FontRendererEvent.RenderEvent event = new FontRendererEvent.RenderEvent(text, x, y, color, dropShadow);
        MinecraftForge.EVENT_BUS.post(event);
        text = event.text;
        x = event.x;
        y = event.y;
        color = event.colour;
        dropShadow = event.dropShadow;
    }

    @Inject(method = "getStringWidth", at = @At("HEAD"), cancellable = true)
    private void onStringWidthGotten(String text, CallbackInfoReturnable<Integer> cir) {
        FontRendererEvent.WidthGottenEvent event = new FontRendererEvent.WidthGottenEvent(text);
        MinecraftForge.EVENT_BUS.post(event);
        text = event.text;
    }

}