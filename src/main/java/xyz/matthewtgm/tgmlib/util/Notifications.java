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

package xyz.matthewtgm.tgmlib.util;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;
import xyz.matthewtgm.tgmlib.data.ColourRGB;

import java.util.ArrayList;
import java.util.List;

public class Notifications {

    private final Minecraft mc = Minecraft.getMinecraft();

    @Getter @Setter private static int width = 200;
    @Getter @Setter private static int paddingWidth = 5;
    @Getter @Setter private static int paddingHeight = 3;
    @Getter @Setter private static int textDistance = 2;

    private static final List<Notification> notifications = new ArrayList<>();

    public static void push(String title, String description, ColourRGB colour, int duration, Notification.NotificationClickRunnable clickRunnable) {
        push(new Notification(title, description, colour, duration, clickRunnable));
    }

    public static void push(String title, String description, ColourRGB colour, Notification.NotificationClickRunnable clickRunnable) {
        push(title, description, colour, -1, clickRunnable);
    }

    public static void push(String title, String description) {
        push(title, description, null, null);
    }

    public static void push(String title, String description, int duration) {
        push(title, description, null, duration, null);
    }

    public static void push(String title, String description, Runnable runnable) {
        push(title, description, notification -> {
            if (runnable != null) {
                runnable.run();
            }
        });
    }

    public static void push(String title, String description, Notification.NotificationClickRunnable clickRunnable) {
        push(title, description, null, clickRunnable);
    }

    public static void push(String title, String description, int duration, Notification.NotificationClickRunnable clickRunnable) {
        push(title, description, null, duration, clickRunnable);
    }

    public static void push(String title, String description, ColourRGB colour) {
        push(title, description, colour, null);
    }

    public static void push(String title, String description, ColourRGB colour, int duration) {
        push(title, description, colour, duration, null);
    }

    public static void push(Notification notification) {
        notifications.add(notification);
    }

    @SubscribeEvent
    protected void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;
        if (notifications.isEmpty())
            return;

        ScaledResolution resolution = ScreenHelper.getResolution();
        Notification current = notifications.get(0);

        float time = current.data.time;
        float opacity = 200;

        if (time <= 1 || time >= 10) {
            float easeTime = Math.min(time, 1);
            opacity = easeTime * 200;
        }

        String boldedTitle = ChatColour.BOLD + current.title;
        List<String> wrappedTitle = EnhancedFontRenderer.wrapTextLines(boldedTitle, mc.fontRendererObj, width, " ");
        List<String> wrappedDescription = EnhancedFontRenderer.wrapTextLines(current.description, mc.fontRendererObj, width, " ");
        int textLines = wrappedTitle.size() + wrappedDescription.size();

        float rectWidth = current.data.width = MathHelper.lerp(current.data.width, width + (paddingWidth * 2), event.renderTickTime / 4);
        if (current.data.closing && current.data.time <= 0.45f)
            rectWidth = current.data.width = Math.max(MathHelper.lerp(current.data.width, -(width + (paddingWidth * 2)), event.renderTickTime / 2), 0);
        float rectHeight = (paddingHeight * 2) + (textLines * mc.fontRendererObj.FONT_HEIGHT) + ((textLines - 1) * textDistance);
        float rectX = resolution.getScaledWidth() - rectWidth + 5;
        float rectY = 3;

        float mouseX = MouseHelper.getMouseX();
        float mouseY = MouseHelper.getMouseY();
        boolean mouseOver = mouseX >= rectX && mouseX <= rectX + rectWidth && mouseY >= rectY && mouseY <= rectY + rectHeight;

        opacity += current.data.mouseOverAdd = MathHelper.lerp(current.data.mouseOverAdd, (mouseOver ? 40 : 0), event.renderTickTime / 4);

        GlStateManager.pushMatrix();

        int clampedOpacity = (int) MathHelper.clamp(opacity, 5, 255);
        ColourRGB colour = current.colour == null ? new ColourRGB(0, 0, 0, clampedOpacity) : current.colour;
        if (colour.equals(current.colour))
            colour.setA(clampedOpacity);
        GlHelper.drawRectangle(rectX, rectY, rectWidth, rectHeight, colour);

        if (current.data.time > 0.1f) {
            ColourRGB textColour = new ColourRGB(255, 255, 255, clampedOpacity);
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GlHelper.totalScissor(rectX, rectY, rectWidth, rectHeight);
            int i = 0;
            for (String line : wrappedTitle) {
                EnhancedFontRenderer.drawText(line, rectX + 2, rectY + paddingHeight + (textDistance * i) + (mc.fontRendererObj.FONT_HEIGHT * i), textColour.getRGBA(), true);
                i++;
            }
            for (String line : wrappedDescription) {
                EnhancedFontRenderer.drawText(line, rectX + 2, rectY + paddingHeight + (textDistance * i) + (mc.fontRendererObj.FONT_HEIGHT * i), textColour.getRGBA(), true);
                i++;
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }

        GlStateManager.popMatrix();

        if (current.data.time >= (current.duration == -1 ? 5 : current.duration))
            current.data.closing = true;
        if (!current.data.clicked && mouseOver && MouseHelper.isMouseDown()) {
            current.data.clicked = true;
            if (current.clickRunnable != null)
                current.clickRunnable.click(current);
            current.data.closing = true;
            if (current.data.time > 1f)
                current.data.time = 1;
        }
        if (!mouseOver)
            current.data.time += (current.data.closing ? -0.02 : 0.02) * (event.renderTickTime * 3);
        if (current.data.closing && current.data.time <= 0)
            notifications.remove(current);
    }

    public static class Notification {
        public String title;
        public String description;
        public ColourRGB colour;
        @Getter private final int duration;
        @Getter private final NotificationClickRunnable clickRunnable;

        private final NotificationData data;

        public Notification(String title, String description, ColourRGB colour, int duration, NotificationClickRunnable clickRunnable) {
            this.title = title;
            this.description = description;
            this.colour = colour;
            this.duration = duration;
            this.clickRunnable = clickRunnable;

            this.data = new NotificationData(0, 0, 0, false, false);
        }

        public Notification(String title, String description, ColourRGB colour, NotificationClickRunnable clickRunnable) {
            this(title, description, colour, -1, clickRunnable);
        }

        public Notification(String title, String description, int duration) {
            this(title, description, null, duration, null);
        }

        public Notification(String title, String description, NotificationClickRunnable clickRunnable) {
            this(title, description, null, clickRunnable);
        }

        public Notification(String title, String description, int duration, NotificationClickRunnable clickRunnable) {
            this(title, description, null, duration, clickRunnable);
        }

        public Notification(String title, String description, ColourRGB colour) {
            this(title, description, colour, null);
        }

        public Notification(String title, String description, ColourRGB colour, int duration) {
            this(title, description, colour, duration, null);
        }

        public Notification(String title, String description) {
            this(title, description, null, null);
        }

        public interface NotificationClickRunnable {
            void click(Notification notification);
        }
    }

    private static class NotificationData {
        float time;
        float width;
        float mouseOverAdd;
        boolean closing;
        boolean clicked;
        NotificationData(float time, float width, float mouseOverAdd, boolean closing, boolean clicked) {
            this.time = time;
            this.width = width;
            this.mouseOverAdd = mouseOverAdd;
            this.closing = closing;
            this.clicked = clicked;
        }
    }

}