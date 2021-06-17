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

package xyz.matthewtgm.lib.util.channels;

import io.netty.channel.ChannelPipeline;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.matthewtgm.lib.util.channels.handlers.ICustomChannelHandler;

import java.util.ArrayList;
import java.util.List;

public class CustomChannelPipeline {

    private final Minecraft mc = Minecraft.getMinecraft();
    private static final List<ICustomChannelHandler> handlers = new ArrayList<>();
    private static final List<String> handlersToRemove = new ArrayList<>();

    public static void addHandler(ICustomChannelHandler handler) {
        handlers.add(handler);
    }

    public static void removeHandler(ICustomChannelHandler handler) {
        handlers.remove(handler);
        handlersToRemove.add(handler.name());
    }

    @SubscribeEvent
    private void onTick(TickEvent.ClientTickEvent event) {
        if (mc.getNetHandler() == null) return;
        NetHandlerPlayClient netHandler = mc.getNetHandler();
        ChannelPipeline pipeline = netHandler.getNetworkManager().channel().pipeline();
        for (ICustomChannelHandler handler : handlers) {
            if (pipeline.get(handler.name()) == null) {
                boolean meetsReqs = true;
                for (String requirement : handler.requires()) if (pipeline.get(requirement) == null) meetsReqs = false;
                if (meetsReqs) {
                    try {
                        if (handler.addBefore() != null) pipeline.addBefore(handler.addBefore(), handler.name(), handler.handler());
                        else if (handler.addAfter() != null) pipeline.addAfter(handler.addAfter(), handler.name(), handler.handler());
                        else {
                            if (handler.first()) pipeline.addFirst(handler.name(), handler.handler());
                            else pipeline.addLast(handler.name(), handler.handler());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for (String name : handlersToRemove) {
            if (pipeline.get(name) != null) {
                try {
                    pipeline.remove(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        handlersToRemove.clear();
    }

}