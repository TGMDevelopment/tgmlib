package ga.matthewtgm.lib.util;

import net.minecraftforge.common.MinecraftForge;

public class ForgeUtils {

    /**
     * @param listeners All listeners to register.
     * @author MatthewTGM
     */
    public static void registerEventListeners(Object... listeners) {
        for (Object listener : listeners)
            MinecraftForge.EVENT_BUS.register(listener);
    }

}