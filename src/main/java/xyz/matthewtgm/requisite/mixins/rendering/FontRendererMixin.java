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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.matthewtgm.requisite.events.FontRendererEvent;

@Mixin({FontRenderer.class})
public class FontRendererMixin {

    @Unique private FontRendererEvent.RenderEvent renderEvent;
    @Unique private FontRendererEvent.WidthGottenEvent widthGottenEvent;

    @Inject(method = "renderString", at = @At("HEAD"), cancellable = true)
    private void onStringRendered(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Integer> cir) {
        FontRendererEvent.RenderEvent event = new FontRendererEvent.RenderEvent(text, x, y, color, dropShadow);
        MinecraftForge.EVENT_BUS.post(event);
        this.renderEvent = event;
    }

    @ModifyVariable(method = "renderString", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    public String renderString_modifyText(String original) {
        return renderEvent.text;
    }

    @ModifyVariable(method = "renderString", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    public float renderString_modifyX(float original) {
        return renderEvent.x;
    }

    @ModifyVariable(method = "renderString", at = @At("HEAD"), argsOnly = true, ordinal = 1)
    public float renderString_modifyY(float original) {
        return renderEvent.y;
    }

    @ModifyVariable(method = "renderString", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    public int renderString_modifyColor(int original) {
        return renderEvent.colour;
    }

    @ModifyVariable(method = "renderString", at = @At("HEAD"), argsOnly = true, ordinal = 1)
    public boolean renderString_modifyDropShadow(boolean original) {
        return renderEvent.dropShadow;
    }

    @Inject(method = "getStringWidth", at = @At("HEAD"), cancellable = true)
    private void onStringWidthGotten(String text, CallbackInfoReturnable<Integer> cir) {
        FontRendererEvent.WidthGottenEvent event = new FontRendererEvent.WidthGottenEvent(text);
        MinecraftForge.EVENT_BUS.post(event);
        this.widthGottenEvent = event;
    }

    @ModifyVariable(method = "getStringWidth", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private String getStringWidth_modifyString(String original) {
        return widthGottenEvent.text;
    }

}