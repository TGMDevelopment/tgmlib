package ga.matthewtgm.lib.gui.custom.elements;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

public abstract class GuiElement {

    @Getter
    protected Minecraft mc = Minecraft.getMinecraft();

    @Getter @Setter
    private int x, y, width, height;

    public GuiElement(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void render(int mouseX, int mouseY, float partialTicks);

    public void mouseClick(int button, int mouseX, int mouseY) {}
    public void mouseRelease(int button, int mouseX, int mouseY) {}
    public void mouseDrag(int button, int mouseX, int mouseY) {}
    public void keyTyped(char typedChar, int keyCode) {}


    public boolean isMouseInBounds(int mouseX, int mouseY) {
        return ((mouseX >= x && mouseX <= x + width) && (mouseY >= y && mouseY <= y + height));
    }

}