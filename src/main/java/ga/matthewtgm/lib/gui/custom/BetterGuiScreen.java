package ga.matthewtgm.lib.gui.custom;

import ga.matthewtgm.lib.gui.custom.elements.GuiElement;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public abstract class BetterGuiScreen extends GuiScreen {

    @Getter
    private List<GuiElement> elements = new ArrayList<>();

    private int eventButton;
    private long lastMouseEvent;

    public BetterGuiScreen() {
        elements.clear();
    }

    @Override
    public abstract void initGui();

    @Override
    public abstract boolean doesGuiPauseGame();

    protected void onMouseInput(int button, int mouseX, int mouseY) {}
    protected void onMouseClicked(int button, int mouseX, int mouseY) {}
    protected void onMouseReleased(int button, int mouseX, int mouseY) {}
    protected void onMouseDragged(int button, int mouseX, int mouseY) {}

    @Override
    public void handleMouseInput() {
        try {
            int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
            int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
            int button = Mouse.getEventButton();
            onMouseInput(button, mouseX, mouseY);
            if (Mouse.getEventButtonState()) {
                this.eventButton = button;
                this.lastMouseEvent = Minecraft.getSystemTime();

                onMouseClicked(button, mouseX, mouseY);

                for (GuiElement element : elements)
                    if (element.isMouseInBounds(mouseX, mouseY))
                        element.mouseClick(button, mouseX, mouseY);
            } else if (button != -1) {
                this.eventButton = -1;

                onMouseReleased(button, mouseX, mouseY);

                for (GuiElement element : elements)
                    if (element.isMouseInBounds(mouseX, mouseY))
                        element.mouseRelease(button, mouseX, mouseY);
            } else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
                long l = Minecraft.getSystemTime() - this.lastMouseEvent;

                onMouseDragged(button, mouseX, mouseY);

                for (GuiElement element : elements)
                    if (element.isMouseInBounds(mouseX, mouseY))
                        element.mouseDrag(button, mouseX, mouseY);
            }
        } catch (Exception e) {
            if (e instanceof ConcurrentModificationException)
                return;
            e.printStackTrace();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        try {
            for (GuiElement element : elements)
                element.keyTyped(typedChar, keyCode);

            super.keyTyped(typedChar, keyCode);
        } catch (Exception e) {
            if (e instanceof ConcurrentModificationException)
                return;
            e.printStackTrace();
        }
    }

    protected void onScreenDrawn(int mouseX, int mouseY, float partialTicks) {}
    protected boolean drawScreenCanceled;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        try {
            drawDefaultBackground();
            onScreenDrawn(mouseX, mouseY, partialTicks);
                if (drawScreenCanceled)
                    return;
            for (GuiElement element : elements)
                element.render(mouseX, mouseY, partialTicks);
        } catch (Exception e) {
            if (e instanceof ConcurrentModificationException)
                return;
            e.printStackTrace();
        }
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        elements.clear();
        super.setWorldAndResolution(mc, width, height);
    }

}