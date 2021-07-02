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

package xyz.matthewtgm.tgmlib.events;

import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

@Cancelable
public class ChatClearEvent extends Event {
    public final GuiNewChat chat;
    public final List<ChatLine> drawnChatLines;
    public final List<ChatLine> chatLines;
    public final List<String> sentMessages;
    public ChatClearEvent(GuiNewChat chat, List<ChatLine> drawnChatLines, List<ChatLine> chatLines, List<String> sentMessages) {
        this.chat = chat;
        this.drawnChatLines = drawnChatLines;
        this.chatLines = chatLines;
        this.sentMessages = sentMessages;
    }
}