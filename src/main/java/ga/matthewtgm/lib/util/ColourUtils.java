package ga.matthewtgm.lib.util;

import java.awt.*;

/**
 * Used to create and get colours easily.
 */
public class ColourUtils {

    /**
     * @return a changing colour based on the users computer time. Simulates a "chroma" colour.
     * @author MatthewTGM
     */
    public static int chroma() {
        final long l = System.currentTimeMillis();
        return Color.HSBtoRGB(l % 2000L / 2000.0F, 0.8F, 0.8F);
    }

}