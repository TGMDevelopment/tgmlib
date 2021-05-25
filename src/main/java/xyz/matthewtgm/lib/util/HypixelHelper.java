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

package xyz.matthewtgm.lib.util;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class HypixelHelper {

    private static int tickTimer = 0;

    @Getter private static String locraw;
    private static boolean allowLocrawCancel;
    private static final AtomicInteger limboLoop = new AtomicInteger(0);
    private static boolean checked;

    @SubscribeEvent
    protected void onClientTick(TickEvent.ClientTickEvent event) {
        tickTimer++;
        if (tickTimer % 20 == 0 && ServerHelper.hypixel() && !allowLocrawCancel && !checked) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/locraw");
            allowLocrawCancel = true;
            checked = true;
        }
    }

    @SubscribeEvent
    protected void onWorldLoad(WorldEvent.Load event) {
        checked = false;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    protected void onChatMessageReceived(ClientChatReceivedEvent event) {
        if (event.type == 0 || event.type == 1) {
            String stripped = StringUtils.stripControlCodes(event.message.getUnformattedText());
            if (stripped.startsWith("{") && stripped.contains("server") && stripped.endsWith("}") && allowLocrawCancel) {
                if (stripped.contains("limbo")) {
                    if (limboLoop.get() > 10)
                        return;
                    allowLocrawCancel = false;
                    checked = false;
                    limboLoop.set(limboLoop.get() + 1);
                    return;
                }
                locraw = stripped;
                allowLocrawCancel = false;
                limboLoop.set(0);
                event.setCanceled(true);
            }
        }
    }

    public static class HypixelAPI {

        public static String getPlayer(String apiKey, String uuid) {
            return ApiHelper.getJsonOnline(String.format("https://api.hypixel.net/player?uuid=%s&key=%s", uuid, apiKey));
        }

        public static String getStatus(String apiKey, String uuid) {
            return ApiHelper.getJsonOnline(String.format("https://api.hypixel.net/status?uuid=%s&key=%s", uuid, apiKey));
        }

        public static String getGuild(String apiKey, String uuid) {
            return ApiHelper.getJsonOnline(String.format("https://api.hypixel.net/guild?player=%s&key=%s", uuid, apiKey));
        }

    }

}