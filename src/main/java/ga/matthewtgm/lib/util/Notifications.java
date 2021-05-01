/*
 * Copyright (C) MatthewTGM
 * This file is part of TGMLib <https://github.com/TGMDevelopment/TGMLib>.
 *
 * TGMLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TGMLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TGMLib. If not, see <http://www.gnu.org/licenses/>.
 */

package ga.matthewtgm.lib.util;

import ga.matthewtgm.lib.other.ColourRGB;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Adapted from XanderLib under GPLv3
 * https://github.com/isXander/XanderLib/blob/main/LICENSE
 */
public class Notifications {

    private final Minecraft mc = Minecraft.getMinecraft();

    @Getter @Setter private static int width = 200;
    @Getter @Setter private static int paddingWidth = 5;
    @Getter @Setter private static int paddingHeight = 3;
    @Getter @Setter private static int textDistance = 2;

    private static final List<Notification> current = new ArrayList<>();

    public static void push(String title, String description) {
        push(title, description, null);
    }

    public static void push(String title, String description, Runnable runnable) {
        current.add(new Notification(title, description, runnable));
    }

    @SubscribeEvent
    protected void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;
        ScaledResolution res = new ScaledResolution(mc);
        if (current.size() == 0)
            return;
        Notification notification = current.get(0);
        float time = notification.time;
        float opacity = 200;
        if (time <= 1 || time >= 10) {
            float easeTime = Math.min(time, 1);
            opacity = easeTime * 200;
        }
        List<String> wrappedTitle = wrapTextLines(EnumChatFormatting.BOLD + notification.title, mc.fontRendererObj, width, " ");
        List<String> wrappedText = wrapTextLines(notification.description, mc.fontRendererObj, width, " ");
        int textLines = wrappedText.size() + wrappedTitle.size();
        float rectWidth = notification.width = MathHelper.lerp(notification.width, width + (paddingWidth * 2), event.renderTickTime / 4f);
        float rectHeight = (paddingHeight * 2) + (textLines * mc.fontRendererObj.FONT_HEIGHT) + ((textLines - 1) * textDistance);
        float rectX = res.getScaledWidth() / 2f - (rectWidth / 2f);
        float rectY = 5;
        float mouseX = MouseHelper.getMouseX();
        float mouseY = MouseHelper.getMouseY();
        boolean mouseOver = mouseX >= rectX && mouseX <= rectX + rectWidth && mouseY >= rectY && mouseY <= rectY + rectHeight;
        opacity += notification.mouseOverAdd = MathHelper.lerp(notification.mouseOverAdd, (mouseOver ? 40 : 0), event.renderTickTime / 4f);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GlUtils.drawRectangle(rectX, rectY, rectWidth, rectHeight, new ColourRGB(0, 0, 0, (int)MathHelper.clamp(opacity, 5, 255)));
        if (notification.time > 0.1f) {
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GlUtils.totalScissor(rectX, rectY, rectWidth, rectHeight);
            int color = new Color(255, 255, 255, (int)MathHelper.clamp(opacity, 2, 255)).getRGB();
            int i = 0;
            for (String line : wrappedTitle) {
                mc.fontRendererObj.drawString(EnumChatFormatting.BOLD + line, res.getScaledWidth() / 2f - (mc.fontRendererObj.getStringWidth(line) / 2f), rectY + paddingHeight + (textDistance * i) + (mc.fontRendererObj.FONT_HEIGHT * i), color, true);
                i++;
            }
            for (String line : wrappedText) {
                mc.fontRendererObj.drawString(line, res.getScaledWidth() / 2f - (mc.fontRendererObj.getStringWidth(line) / 2f), rectY + paddingHeight + (textDistance * i) + (mc.fontRendererObj.FONT_HEIGHT * i), color, false);
                i++;
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
        GlStateManager.popMatrix();
        if (notification.time >= 3f)
            notification.closing = true;
        if (!notification.clicked && mouseOver && MouseHelper.isMouseDown()) {
            notification.clicked = true;
            if (notification.runnable != null)
                notification.runnable.run();
            notification.closing = true;
            if (notification.time > 1f)
                notification.time = 1f;
        }
        if (!((mouseOver && notification.clicked) && notification.time > 1f))
            notification.time += (notification.closing ? -0.02f : 0.02f) * (event.renderTickTime * 3f);
        if (notification.closing && notification.time <= 0)
            current.remove(notification);
    }

    private List<String> wrapTextLines(String text, FontRenderer fontRenderer, int lineWidth, String split) {
        String wrapped = wrapText(text, fontRenderer, lineWidth, split);
        if (wrapped.equals(""))
            return new ArrayList<>();
        return Arrays.asList(wrapped.split("\n"));
    }

    private String wrapText(String text, FontRenderer fontRenderer, int lineWidth, String split) {
        String[] words = text.split("(" + split + "|\n)");
        int lineLength = 0;
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (i != words.length - 1)
                word += split;
            int wordLength = fontRenderer.getStringWidth(word);
            if (lineLength + wordLength <= lineWidth) {
                output.append(word);
                lineLength += wordLength;
            } else if (wordLength <= lineWidth) {
                output.append("\n").append(word);
                lineLength = wordLength;
            } else
                output.append(wrapText(word, fontRenderer, lineWidth, "")).append(split);
        }
        return output.toString();
    }

    private static class Notification {
        String title;
        String description;
        Runnable runnable;

        float time;
        float width;
        float mouseOverAdd;
        boolean closing;
        boolean clicked;

        Notification(String title, String description, Runnable runnable) {
            this.title = title;
            this.description = description;
            this.runnable = runnable;

            this.time = 0;
            this.width = 0;
            this.mouseOverAdd = 0;
            this.closing = false;
            this.clicked = false;
        }
    }

}