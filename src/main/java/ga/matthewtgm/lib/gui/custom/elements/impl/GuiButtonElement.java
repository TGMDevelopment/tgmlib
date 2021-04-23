package ga.matthewtgm.lib.gui.custom.elements.impl;

import ga.matthewtgm.lib.gui.custom.elements.GuiElement;
import ga.matthewtgm.lib.util.FontRendererHelper;
import ga.matthewtgm.lib.util.RenderHelper;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

public class GuiButtonElement extends GuiElement {

    @Getter
    @Setter
    private String display;
    @Getter @Setter private GuiButtonElementInteract interaction;
    @Getter @Setter private GuiButtonElementColour colour;

    public GuiButtonElement(String display, int x, int y, int width, int height, GuiButtonElementInteract interaction, GuiButtonElementColour colour) {
        super(x, y, width, height);
        this.display = display;
        this.interaction = interaction;
        this.colour = colour;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        Color backgroundColor;
        Color textColor;
        boolean hovered = isMouseInBounds(mouseX, mouseY);
        if (hovered) {
            backgroundColor = colour.getButtonColourHovered();
            textColor = colour.getButtonTextColourHovered();
        } else {
            backgroundColor = colour.getButtonColour();
            textColor = colour.getButtonTextColour();
        }
        RenderHelper.drawRoundedRect(getX(), getY(), getWidth(), getHeight(), 4, backgroundColor);
        FontRendererHelper.drawCenteredString(fontRenderer, getDisplay(), getX() + this.getWidth() / 2, this.getY() + (this.getHeight() - 8) / 2, textColor.getRGB());
    }

    @Override
    public void mouseClick(int button, int mouseX, int mouseY) {
        display = display;
        switch (button) {
            case 0:
                interaction.onLeftClick(mouseX, mouseY);
                break;
            case 1:
                interaction.onRightClick(mouseX, mouseY);
                break;
            case 2:
                interaction.onMiddleClick(mouseX, mouseY);
        }
    }

    @Override
    public void mouseRelease(int button, int mouseX, int mouseY) {
        switch (button) {
            case 0:
                interaction.onLeftRelease(mouseX, mouseY);
                break;
            case 1:
                interaction.onRightRelease(mouseX, mouseY);
                break;
            case 2:
                interaction.onMiddleRelease(mouseX, mouseY);
        }
    }

    public static abstract class GuiButtonElementInteract {
        public void onLeftClick(int mouseX, int mouseY) {}
        public void onLeftRelease(int mouseX, int mouseY) {}
        public void onRightClick(int mouseX, int mouseY) {}
        public void onRightRelease(int mouseX, int mouseY) {}
        public void onMiddleClick(int mouseX, int mouseY) {}
        public void onMiddleRelease(int mouseX, int mouseY) {}
    }

    public static abstract class GuiButtonElementColour {
        public abstract Color getButtonColour();
        public abstract Color getButtonColourHovered();
        public abstract Color getButtonTextColour();
        public abstract Color getButtonTextColourHovered();

        public static class Default extends GuiButtonElementColour {
            @Override
            public Color getButtonColour() {
                return new Color(0, 80, 219, 128);
            }
            @Override
            public Color getButtonColourHovered() {
                return new Color(0, 134, 236, 128);
            }
            @Override
            public Color getButtonTextColour() {
                return new Color(255, 255, 255);
            }
            @Override
            public Color getButtonTextColourHovered() {
                return new Color(226, 188, 0);
            }
        }
    }

}