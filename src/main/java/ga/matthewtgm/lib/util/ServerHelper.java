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

}