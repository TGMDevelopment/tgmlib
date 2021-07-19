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

package xyz.matthewtgm.tgmlib;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.matthewtgm.tgmlib.events.ActionBarEvent;
import xyz.matthewtgm.tgmlib.events.ChatMessageReceivedEvent;

public final class TGMLibBetterEventsListener {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (event.type == 0 || event.type == 1) {
            ChatMessageReceivedEvent chatMessageReceivedEvent = new ChatMessageReceivedEvent(event.message, event.type);
            event.message = chatMessageReceivedEvent.component;
            event.setCanceled(chatMessageReceivedEvent.isCanceled());
        }

        if (event.type == 2) {
            ActionBarEvent actionBarEvent = new ActionBarEvent(event.message);
            event.message = actionBarEvent.component;
            event.setCanceled(actionBarEvent.isCanceled());
        }
    }

}