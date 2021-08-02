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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ServerHelper {

    private static final Minecraft mc = Minecraft.getMinecraft();

    private static final List<String> SKYBLOCK_IN_ALL_LANGUAGES = new ArrayList<>(Arrays.asList("SKYBLOCK","\u7A7A\u5C9B\u751F\u5B58", "\u7A7A\u5CF6\u751F\u5B58"));
    private static final Pattern SERVER_BRAND_PATTERN = Pattern.compile("(.+) <- (?:.+)");

    private ServerHelper() {}

    /**
     * @return Whether or not the client is connected to Hypixel.
     * @author Biscuit
     */
    public static boolean hypixel() {
        String HYPIXEL_SERVER_BRAND = "Hypixel BungeeCord";

        if (!mc.isSingleplayer() && mc.thePlayer != null && mc.thePlayer.getClientBrand() != null) {
            Matcher matcher = SERVER_BRAND_PATTERN.matcher(mc.thePlayer.getClientBrand());

            if (matcher.find())
                return matcher.group(1).startsWith(HYPIXEL_SERVER_BRAND);
            else
                return false;
        } else
            return false;
    }

    /**
     * @return Whether or not the client is connected to Hypixel SkyBlock.
     * @author MatthewTGM
     */
    public static boolean hypixelSkyBlock() {
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer() && hypixel()) {
            ScoreObjective sidebarObjective = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (sidebarObjective != null) {
                String objectiveName = StringUtils.stripControlCodes(sidebarObjective.getDisplayName());
                for (String skyblock : SKYBLOCK_IN_ALL_LANGUAGES) {
                    if (objectiveName.startsWith(skyblock)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return Whether or not the client is connected to Hypixel BedWars.
     * @author MatthewTGM
     */
    public static boolean hypixelBedwars() {
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer() && hypixel()) {
            ScoreObjective sidebarObjective = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (sidebarObjective != null) {
                String objectiveName = StringUtils.stripControlCodes(sidebarObjective.getDisplayName());
                return objectiveName.contains("BED WARS");
            }
        }
        return false;
    }

    // TODO: 2021/07/16 : Javadoc below!

    public static boolean isOnServer(String ip) {
        return !mc.isSingleplayer() && mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.equalsIgnoreCase(ip);
    }

    public static boolean doesScoreboardNameContain(String input) {
        if (mc.theWorld != null) {
            ScoreObjective sidebarObjective = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (sidebarObjective != null) {
                String objectiveName = StringUtils.stripControlCodes(sidebarObjective.getDisplayName());
                return objectiveName.contains(input);
            }
        }
        return false;
    }

    public static boolean doesScoreboardContentContain(String input) {
        if (mc.theWorld != null) {
            Scoreboard scoreboard = mc.theWorld.getScoreboard();
            if (scoreboard != null) {
                ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
                if (sidebarObjective != null) {
                    Collection<Score> scores = scoreboard.getSortedScores(sidebarObjective);
                    for (Score score : scores) {
                        return StringUtils.stripControlCodes(ScorePlayerTeam.formatPlayerName(scoreboard.getPlayersTeam(score.getPlayerName()), score.getPlayerName())).trim().contains(input);
                    }
                }
            }
        }
        return false;
    }

    public static boolean doesScoreboardContain(String input) {
        return doesScoreboardNameContain(input) || doesScoreboardContentContain(input);
    }

    public static void joinServer(GuiScreen parent, String ip, int port) {
        GuiHelper.open(new GuiConnecting(parent, mc, ip, port));
    }

    public static void joinServer(String ip, int port) {
        joinServer(null, ip, port);
    }

    public static void joinServer(GuiScreen parent, ServerData data) {
        GuiHelper.open(new GuiConnecting(parent, mc, data));
    }

    public static void joinServer(ServerData data) {
        joinServer(null, data);
    }

}