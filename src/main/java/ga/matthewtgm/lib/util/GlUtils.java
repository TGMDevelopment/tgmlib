package ga.matthewtgm.lib.util;

import org.lwjgl.opengl.GL11;

public class GlUtils {

    /**
     * @param x      The x coordinate to start the box.
     * @param y      The y coordinate to start the box.
     * @param width  The width of the box.
     * @param height The height of the box.
     * @author MatthewTGM
     */
    public static void startScissorBox(int x, int y, int width, int height) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(x, y, width, height);
    }

    /**
     * @author MatthewTGM
     */
    public static void endScissorBox() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

}