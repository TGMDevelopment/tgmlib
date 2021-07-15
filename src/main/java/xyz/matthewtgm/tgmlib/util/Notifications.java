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

import xyz.matthewtgm.tgmlib.data.ColourRGB;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapted from XanderLib under GPLv3
 * https://github.com/isXander/XanderLib/blob/main/LICENSE
 */
public class Notifications {

    private final Minecraft mc = Minecraft.getMinecraft();

    private final float width = 225;

    private static final List<Notification> notifications = new ArrayList<>();

    public static void push(String title, String description, Notification.NotificationColour colour, int duration, Notification.NotificationClickRunnable clickRunnable) {
        push(new Notification(title, description, colour, duration, clickRunnable));
    }

    public static void push(String title, String description, Notification.NotificationColour colour, Notification.NotificationClickRunnable clickRunnable) {
        push(title, description, colour, -1, clickRunnable);
    }

    public static void push(String title, String description) {
        push(title, description, null, -1, null);
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

    public static void push(String title, String description, Notification.NotificationColour colour) {
        push(title, description, colour, null);
    }

    public static void push(String title, String description, Notification.NotificationColour colour, int duration) {
        push(title, description, colour, duration, null);
    }

    public static void push(Notification notification) {
        notifications.add(notification);
    }

    @SubscribeEvent
    protected void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;

        ScaledResolution resolution = ScreenHelper.getResolution();

        float prevHeight = 0;
        int prevTextLines = 0;
        Notification awaitingRemoval = null;
        for (Notification notification : notifications) {
            int index = notifications.indexOf(notification);

            /* Text. */
            String title = ChatColour.BOLD + notification.title;
            List<String> wrappedTitle = EnhancedFontRenderer.wrapTextLines(title, (int) (width), " ");
            List<String> wrappedDescription = EnhancedFontRenderer.wrapTextLines(notification.description, (int) (width), " ");
            int textLines = wrappedTitle.size() + wrappedDescription.size();

            /* Size and positon. */
            float height = 18 + (textLines * EnhancedFontRenderer.getFontHeight());
            float x = resolution.getScaledWidth() - width - 5;
            float y = ((index > 0 ? prevHeight : 5) + 3) * index;

            prevHeight = height;
            prevTextLines = textLines;

            /* Opacity. */
            float opacity = 200;
            if (notification.data.time <= 1 || notification.data.time >= 10)
                opacity = Math.min(notification.data.time, 1) * 200;
            int clampedOpacity = MathHelper.clamp_int((int) opacity, 5, 255);

            /* Mouse handling. */
            float mouseX = MouseHelper.getMouseX();
            float mouseY = MouseHelper.getMouseY();
            boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
            if (hovered && !notification.data.clicked && MouseHelper.isMouseDown()) {
                notification.data.clicked = true;
                if (notification.clickRunnable != null)
                    notification.clickRunnable.click(notification);
                notification.data.closing = true;
            }

            /* Rendering. */
            GlStateManager.pushMatrix();
            ColourRGB backgroundColour = notification.colour == null || notification.colour.backgroundColour == null ? new ColourRGB(0, 0, 0, clampedOpacity) : notification.colour.backgroundColour.setA_builder(clampedOpacity);
            RenderHelper.drawRectEnhanced((int) x, (int) y, (int) width, (int) height, backgroundColour.getRGBA());
            ColourRGB foregroundColour = notification.colour == null || notification.colour.foregroundColour == null ? new ColourRGB(255, 175, 0, clampedOpacity) : notification.colour.foregroundColour.setA_builder(clampedOpacity);
            RenderHelper.drawHollowRect((int) x + 4, (int) y + 4, (int) width - 8, (int) height - 8, foregroundColour.getRGBA());
            if (notification.data.time > 0.1f) {
                ColourRGB textColour = new ColourRGB(255, 255, 255, clampedOpacity);
                GlHelper.startScissorBox(x, y, width, height);
                int i = 0;
                for (String line : wrappedTitle) {
                    EnhancedFontRenderer.drawText(line, x + 8, y + 8 + (i * 2) + (i * EnhancedFontRenderer.getFontHeight()), textColour.getRGBA(), true);
                    i++;
                }
                for (String line : wrappedDescription) {
                    EnhancedFontRenderer.drawText(line, x + 8, y + 8 + (i * 2) + (i * EnhancedFontRenderer.getFontHeight()), textColour.getRGBA(), true);
                    i++;
                }
                GlHelper.endScissorBox();
            }
            GlStateManager.popMatrix();

            /* Other handling things. */
            if (notification.data.time >= (notification.duration == -1 ? 3 : notification.duration))
                notification.data.closing = true;
            if (!hovered)
                notification.data.time += (notification.data.closing ? -0.02 : 0.02) * (event.renderTickTime * 3);
            if (notification.data.closing && notification.data.time <= 0)
                awaitingRemoval = notification;
        }
        if (awaitingRemoval != null)
            notifications.remove(awaitingRemoval);
    }

    public static class Notification {
        public String title;
        public String description;
        public NotificationColour colour;
        @Getter private final int duration;
        @Getter private final NotificationClickRunnable clickRunnable;

        private final NotificationData data;

        public Notification(String title, String description, NotificationColour colour, int duration, NotificationClickRunnable clickRunnable) {
            this.title = title;
            this.description = description;
            this.colour = colour;
            this.duration = duration;
            this.clickRunnable = clickRunnable;

            this.data = new NotificationData(0, false, false);
        }

        public Notification(String title, String description, NotificationColour colour, NotificationClickRunnable clickRunnable) {
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

        public Notification(String title, String description, NotificationColour colour) {
            this(title, description, colour, null);
        }

        public Notification(String title, String description, NotificationColour colour, int duration) {
            this(title, description, colour, duration, null);
        }

        public Notification(String title, String description) {
            this(title, description, null, null);
        }

        public interface NotificationClickRunnable {
            void click(Notification notification);
        }

        public static class NotificationColour {
            public final ColourRGB backgroundColour;
            public final ColourRGB foregroundColour;
            public NotificationColour(ColourRGB backgroundColour, ColourRGB foregroundColour) {
                this.backgroundColour = backgroundColour;
                this.foregroundColour = foregroundColour;
            }
        }
    }

    private static class NotificationData {
        private float time;
        private boolean closing;
        private boolean clicked;
        NotificationData(float time, boolean closing, boolean clicked) {
            this.time = time;
            this.closing = closing;
            this.clicked = clicked;
        }
    }

}