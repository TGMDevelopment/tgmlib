package ga.matthewtgm.lib.util;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class HypixelHelper {

    private static int tickTimer = 0;

    @Getter private static String locraw;
    private static boolean allowLocrawCancel;
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
                    allowLocrawCancel = false;
                    checked = false;
                    return;
                }
                locraw = stripped;
                allowLocrawCancel = false;
                event.setCanceled(true);
            }
        }
    }

}