package ga.matthewtgm.lib.util;

import net.minecraftforge.common.MinecraftForge;

public class ForgeUtils {

    public static void registerEventListeners(Object... eventListenerClasses) {
        for (Object eventListenerClass : eventListenerClasses)
            MinecraftForge.EVENT_BUS.register(eventListenerClass);
    }

}