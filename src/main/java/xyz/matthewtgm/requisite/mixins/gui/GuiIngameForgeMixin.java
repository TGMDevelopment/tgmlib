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

package xyz.matthewtgm.requisite.mixins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.matthewtgm.requisite.events.ActionBarEvent;
import xyz.matthewtgm.requisite.events.BossBarEvent;
import xyz.matthewtgm.requisite.events.TitleEvent;

@Mixin({GuiIngameForge.class})
public abstract class GuiIngameForgeMixin extends GuiIngame {

    public GuiIngameForgeMixin(Minecraft mcIn) {
        super(mcIn);
    }

    @Inject(method = "renderRecordOverlay", at = @At("HEAD"), cancellable = true, remap = false)
    private void onRecordOverlayRendered(int width, int height, float partialTicks, CallbackInfo ci) {
        ActionBarEvent.RenderEvent event = new ActionBarEvent.RenderEvent(recordPlaying, width, height, partialTicks);
        MinecraftForge.EVENT_BUS.post(event);
        recordPlaying = event.text;
    }

    @Inject(method = "renderTitle", at = @At("HEAD"), cancellable = true, remap = false)
    private void onTitleRendered(int width, int height, float partialTicks, CallbackInfo ci) {
        TitleEvent event = new TitleEvent(displayedTitle, displayedSubTitle);
        if (MinecraftForge.EVENT_BUS.post(event))
            ci.cancel();
        displayedTitle = event.title;
        displayedSubTitle = event.subTitle;
    }

}