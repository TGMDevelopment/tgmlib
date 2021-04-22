package ga.matthewtgm.lib.gui.vanilla;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

/**
 * A transparent-ish version of {@link GuiButton}.
 * @author MatthewTGM
 */
public class GuiTransButton extends GuiButton {

    private Color buttonColour = new Color(0, 0, 0, 55);

    public GuiTransButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public GuiTransButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            FontRenderer fontrenderer = mc.fontRendererObj;
            drawRect(this.xPosition, yPosition, xPosition + width, yPosition + height, buttonColour.getRGB());
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            hovered = mouseX >= this.xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;
            if (!enabled) {
                j = 10526880;
            } else if (hovered) {
                j = 16777120;
            }
            drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, j);
        }
    }

    /**
     * Sets the xPosition of the button.
     *
     * @param x new xPosition.
     * @author MatthewTGM
     */
    public void setX(int x) {
        this.xPosition = x;
    }

    /**
     * Sets the yPosition of the button.
     *
     * @param y new yPosition.
     * @author MatthewTGM
     */
    public void setY(int y) {
        this.yPosition = y;
    }

    /**
     * Sets the height of the button.
     *
     * @param height new height.
     * @author MatthewTGM
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @author MatthewTGM
     * @return the colour of the button.
     */
    public Color getButtonColour() {
        return buttonColour;
    }

    /**
     * Sets the colour of the button.
     * @param buttonColour new button colour.
     * @author MatthewTGM
     */
    public void setButtonColour(Color buttonColour) {
        this.buttonColour = buttonColour;
    }

}