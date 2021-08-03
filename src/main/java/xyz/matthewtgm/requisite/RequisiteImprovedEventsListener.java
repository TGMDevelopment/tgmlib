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

package xyz.matthewtgm.requisite;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.matthewtgm.requisite.events.ActionBarEvent;
import xyz.matthewtgm.requisite.events.ChatMessageReceivedEvent;

/**
 * Permits custom Requisite "improved" events to be run inside of Forge events, a good example of this would be splitting the {@link ClientChatReceivedEvent}.
 */
public final class RequisiteImprovedEventsListener {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (event.type == 0 || event.type == 1) {
            ChatMessageReceivedEvent chatMessageReceivedEvent = new ChatMessageReceivedEvent(event.message, event.type);
            event.message = chatMessageReceivedEvent.component;
            event.setCanceled(chatMessageReceivedEvent.isCanceled());
        }

        if (event.type == 2) {
            ActionBarEvent.ReceiveEvent actionBarEvent = new ActionBarEvent.ReceiveEvent(event.message);
            event.message = actionBarEvent.component;
            event.setCanceled(actionBarEvent.isCanceled());
        }
    }

}