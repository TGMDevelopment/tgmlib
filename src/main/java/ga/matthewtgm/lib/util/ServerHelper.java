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

package ga.matthewtgm.lib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerHelper {

    private static final Minecraft mc = Minecraft.getMinecraft();

    private static final List<String> SKYBLOCK_IN_ALL_LANGUAGES = new ArrayList<>(Arrays.asList("SKYBLOCK","\u7A7A\u5C9B\u751F\u5B58", "\u7A7A\u5CF6\u751F\u5B58"));

    public static boolean hypixel() {
        final Pattern SERVER_BRAND_PATTERN = Pattern.compile("(.+) <- (?:.+)");
        final String HYPIXEL_SERVER_BRAND = "Hypixel BungeeCord";

        if (!mc.isSingleplayer() && mc.thePlayer != null && mc.thePlayer.getClientBrand() != null) {
            Matcher matcher = SERVER_BRAND_PATTERN.matcher(mc.thePlayer.getClientBrand());

            if (matcher.find()) {
                return matcher.group(1).startsWith(HYPIXEL_SERVER_BRAND);
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

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

    public static boolean isOnServer(String ip) {
        return !mc.isSingleplayer() && mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.equalsIgnoreCase(ip);
    }

}