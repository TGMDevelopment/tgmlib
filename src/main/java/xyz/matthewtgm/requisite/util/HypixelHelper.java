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

package xyz.matthewtgm.requisite.util;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.json.parser.JsonParser;
import xyz.matthewtgm.json.serialization.JsonSerializer;
import xyz.matthewtgm.json.serialization.annotations.JsonSerializeExcluded;
import xyz.matthewtgm.json.serialization.annotations.JsonSerializeName;
import xyz.matthewtgm.json.util.JsonApiHelper;
import xyz.matthewtgm.requisite.events.LocrawReceivedEvent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public final class HypixelHelper {

    private static int tickTimer = 0;

    @Getter private static HypixelLocraw locraw;
    private static boolean allowLocrawCancel;
    private static final AtomicInteger limboLoop = new AtomicInteger(0);
    private static boolean checked;

    @Getter private static final Pattern rankAndUsernamePattern = Pattern.compile("(?<rank>\\[.+\\]) (?<username>\b\\w{3,16}\b)");

    @SubscribeEvent
    protected void onClientTick(TickEvent.ClientTickEvent event) {
        tickTimer++;
        if (tickTimer % 20 == 0 && ServerHelper.hypixel() && !allowLocrawCancel && !checked) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/locraw");
            tickTimer = 0;
            allowLocrawCancel = true;
            checked = true;
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    protected void onWorldLoad(WorldEvent.Load event) {
        checked = false;
        locraw = null;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    protected void onChatMessageReceived(ClientChatReceivedEvent event) {
        if (event.type == 0 || event.type == 1) {
            String stripped = StringUtils.stripControlCodes(event.message.getUnformattedText());
            if (stripped.startsWith("{") && stripped.contains("server") && stripped.endsWith("}") && allowLocrawCancel) {
                if (stripped.contains("limbo")) {
                    if (limboLoop.get() > 3)
                        return;
                    allowLocrawCancel = false;
                    checked = false;
                    limboLoop.set(limboLoop.get() + 1);
                    return;
                }
                JsonObject strippedAsJson = JsonParser.parse(stripped).getAsJsonObject();
                locraw = new HypixelLocraw(strippedAsJson.get("server"), strippedAsJson.get("mode"), strippedAsJson.get("map"), strippedAsJson.get("gametype"));
                ForgeHelper.postEvent(new LocrawReceivedEvent(locraw));
                allowLocrawCancel = false;
                limboLoop.set(0);
                event.setCanceled(true);
            }
        }
    }

    @Getter
    public static class HypixelLocraw {
        @JsonSerializeName("server")
        private final String serverId;
        @JsonSerializeName("mode")
        private final String gameMode;
        @JsonSerializeName("map")
        private final String mapName;
        @JsonSerializeName("gametype")
        private final String rawGameType;
        @JsonSerializeExcluded
        private final GameType gameType;

        public JsonObject toJson() {
            return JsonSerializer.create(this);
        }

        public String toString() {
            return toJson().getAsString();
        }

        public HypixelLocraw(Object serverId, Object gameMode, Object mapName, Object rawGameType) {
            this.serverId = serverId == null ? "" : serverId.toString();
            this.gameMode = gameMode == null ? "" : gameMode.toString();
            this.mapName = mapName == null ? "" : mapName.toString();
            this.rawGameType = rawGameType == null ? "" : rawGameType.toString();
            this.gameType = GameType.getFromLocraw(this.rawGameType);
        }

        public enum GameType {
            UNKNOWN(""),
            BEDWARS("BEDWARS"),
            SKYWARS("SKYWARS"),
            PROTOTYPE("PROTOTYPE"),
            SKYBLOCK("SKYBLOCK"),
            MAIN("MAIN"),
            MURDER_MYSTERY("MURDER_MYSTERY"),
            HOUSING("HOUSING"),
            ARCADE_GAMES("ARCADE"),
            BUILD_BATTLE("BUILD_BATTLE"),
            DUELS("DUELS"),
            PIT("PIT"),
            UHC_CHAMPIONS("UHC"),
            SPEED_UHC("SPEED_UHC"),
            TNT_GAMES("TNTGAMES"),
            CLASSIC_GAMES("LEGACY"),
            COPS_AND_CRIMS("MCGO"),
            BLITZ_SG("SURVIVAL_GAMES"),
            MEGA_WALLS("WALLS3"),
            SMASH_HEROES("SUPER_SMASH"),
            WARLORDS("BATTLEGROUND");

            @Getter
            private final String serverName;
            GameType(String serverName) {
                this.serverName = serverName;
            }

            public static GameType getFromLocraw(String gameType) {
                for (GameType value : values())
                    if (value.serverName.equals(gameType))
                        return value;
                return UNKNOWN;
            }
        }
    }

    public static class HypixelAPI {

        public static boolean isValidKey(String apiKey) {
            JsonObject json = JsonApiHelper.getJsonObject("https://api.hypixel.net/key?key=" + apiKey);
            return json.hasKey("success") && json.get("success").getAsBoolean();
        }

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