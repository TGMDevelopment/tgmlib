package ga.matthewtgm.lib.util;

import net.minecraft.client.gui.FontRenderer;

/**
 * Used along with {@link FontRenderer} to make things regarding font rendering just a little nicer.
 * @author MatthewTGM
 */
public class FontRendererHelper {

    /**
     * Draws a string centered on the X and Y coordinates given.
     *
     * @param fontRenderer Minecraft font renderer instance.
     * @param text The text to render.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param color The colour of the text.
     */
    public static void drawCenteredString(FontRenderer fontRenderer, String text, float x, float y, int color) {
        fontRenderer.drawStringWithShadow(text, (x - fontRenderer.getStringWidth(text) / 2), y, color);
    }

    /**
     * Draws a string centered on the X and Y coordinates given.
     *
     * @param fontRenderer Minecraft font renderer instance.
     * @param text The text to render.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param color The colour of the text.
     */
    public static void drawCenteredString(FontRenderer fontRenderer, String text, double x, double y, int color) {
        fontRenderer.drawStringWithShadow(text, (float) (x - fontRenderer.getStringWidth(text) / 2), (float) y, color);
    }

}