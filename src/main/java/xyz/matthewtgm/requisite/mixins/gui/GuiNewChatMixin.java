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

import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.matthewtgm.requisite.events.ChatClearEvent;
import xyz.matthewtgm.requisite.events.PrintChatMessageEvent;

import java.util.List;

@Mixin({GuiNewChat.class})
public class GuiNewChatMixin {

    @Shadow @Final private List<ChatLine> drawnChatLines;
    @Shadow @Final private List<ChatLine> chatLines;
    @Shadow @Final private List<String> sentMessages;

    @Unique private PrintChatMessageEvent printChatMessageEvent;

    @Inject(method = "printChatMessage", at = @At("HEAD"), cancellable = true)
    private void onChatMessagePrinted(IChatComponent chatComponent, CallbackInfo ci) {
        PrintChatMessageEvent event = new PrintChatMessageEvent((GuiNewChat)(Object)this, chatComponent);
        if (MinecraftForge.EVENT_BUS.post(event))
            ci.cancel();
        this.printChatMessageEvent = event;
    }

    @ModifyVariable(method = "printChatMessage", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private IChatComponent printChatMessage_modifyComponent(IChatComponent original) {
        return printChatMessageEvent.component;
    }

    @Inject(method = "clearChatMessages", at = @At("HEAD"), cancellable = true)
    private void onChatMessagesCleared(CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new ChatClearEvent((GuiNewChat)(Object)this, drawnChatLines, chatLines, sentMessages)))
            ci.cancel();
    }

}