/*
 * Requisite - Minecraft library mod
 * Copyright (C) 2021 MatthewTGM
 *
 * Requisite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * Requisite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Requisite. If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.matthewtgm.requisite.util;

import xyz.matthewtgm.requisite.data.ColourRGB;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Notifications {

    private static final List<Notification> notifications = new CopyOnWriteArrayList<>();

    /**
     * @param title The notification title, automatically bolded.
     * @param description The description of the notification.
     * @param colour The notification's colour.
     * @param duration The duration that the notification will be rendered for in seconds.
     * @param clickRunnable The click listener.
     * @author MatthewTGM
     */
    public static void push(String title, String description, Notification.NotificationColour colour, int duration, Notification.NotificationClickRunnable clickRunnable) {
        push(new Notification(title, description, colour, duration, clickRunnable));
    }

    /**
     * @param title The notification title, automatically bolded.
     * @param description The description of the notification.
     * @param colour The notification's colour.
     * @param clickRunnable The click listener.
     */
    public static void push(String title, String description, Notification.NotificationColour colour, Notification.NotificationClickRunnable clickRunnable) {
        push(title, description, colour, -1, clickRunnable);
    }

    /**
     * @param title The notification title, automatically bolded.
     * @param description The description of the notification.
     * @author MatthewTGM
     */
    public static void push(String title, String description) {
        push(title, description, null, -1, null);
    }

    /**
     * @param title The notification title, automatically bolded.
     * @param description The description of the notification.
     * @param runnable The click listener.
     * @author MatthewTGM
     */
    public static void push(String title, String description, Runnable runnable) {
        push(title, description, notification -> {
            if (runnable != null) {
                runnable.run();
            }
        });
    }

    /**
     * @param title The notification title, automatically bolded.
     * @param description The description of the notification.
     * @param clickRunnable The click listener.
     * @author MatthewTGM
     */
    public static void push(String title, String description, Notification.NotificationClickRunnable clickRunnable) {
        push(title, description, null, clickRunnable);
    }

    /**
     * @param title The notification title, automatically bolded.
     * @param description The description of the notification.
     * @param duration The duration that the notification will be rendered for in seconds.
     * @param clickRunnable The click listener.
     * @author MatthewTGM
     */
    public static void push(String title, String description, int duration, Notification.NotificationClickRunnable clickRunnable) {
        push(title, description, null, duration, clickRunnable);
    }

    /**
     * @param title The notification title, automatically bolded.
     * @param description The description of the notification.
     * @param colour The notification's colour.
     * @author MatthewTGM
     */
    public static void push(String title, String description, Notification.NotificationColour colour) {
        push(title, description, colour, null);
    }

    /**
     * @param title The notification title, automatically bolded.
     * @param description The description of the notification.
     * @param colour The notification's colour.
     * @param duration The duration that the notification will be rendered for in seconds.
     * @author MatthewTGM
     */
    public static void push(String title, String description, Notification.NotificationColour colour, int duration) {
        push(title, description, colour, duration, null);
    }

    /**
     * @param notification The notification to push.
     * @author MatthewTGM
     */
    public static void push(Notification notification) {
        notifications.add(notification);
    }

    @SubscribeEvent
    protected void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;

        ScaledResolution resolution = ScreenHelper.getResolution();

        float y = 5;
        for (Notification notification : notifications) {
            if (notifications.indexOf(notification) > 2)
                continue;

            if (notification.data.xPosition < 1)
                notification.data.xPosition = resolution.getScaledWidth();

            int duration = (notification.duration == -1 ? 4 : notification.duration);

            /* Text. */
            String title = ChatColour.BOLD + notification.title;
            float width = 225;
            List<String> wrappedTitle = EnhancedFontRenderer.wrapTextLines(title, (int) (width - 10), " ");
            List<String> wrappedDescription = EnhancedFontRenderer.wrapTextLines(notification.description, (int) (width - 10), " ");
            int textLines = wrappedTitle.size() + wrappedDescription.size();

            /* Size and positon. */
            float height = 18 + (textLines * EnhancedFontRenderer.getFontHeight());
            float x = notification.data.xPosition = MathHelper.lerp(notification.data.xPosition, resolution.getScaledWidth() - width - 5, event.renderTickTime / 4);
            if (notification.data.closing && notification.data.time < 0.75f)
                x = notification.data.xPosition = MathHelper.lerp(notification.data.xPosition, resolution.getScaledWidth() + width, event.renderTickTime / 4);

            /* Mouse handling. */
            float mouseX = MouseHelper.getMouseX();
            float mouseY = MouseHelper.getMouseY();
            boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
            if (hovered && !notification.data.clicked && MouseHelper.isMouseDown()) {
                notification.data.clicked = true;
                notification.click();
                notification.data.closing = true;
            }

            /* Rendering. */
            GlStateManager.pushMatrix();
            ColourRGB backgroundColour = notification.colour == null || notification.colour.backgroundColour == null ? new ColourRGB(0, 0, 0, 200) : notification.colour.backgroundColour.setA_builder(200);
            RenderHelper.drawRectEnhanced((int) x, (int) y, (int) width, (int) height, backgroundColour.getRGBA());
            ColourRGB foregroundColour = notification.colour == null || notification.colour.foregroundColour == null ? new ColourRGB(255, 175, 0, 200) : notification.colour.foregroundColour.setA_builder(200);
            RenderHelper.drawHollowRect((int) x + 4, (int) y + 4, (int) width - 8, (int) height - 8, foregroundColour.getRGBA());

            /* Text. */
            if (notification.data.time > 0.1f) {
                ColourRGB textColour = new ColourRGB(255, 255, 255, 200);
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

            /* Positioning. */
            y += height + 5;

            /* Other handling things. */
            if (notification.data.time >= duration)
                notification.data.closing = true;
            if (!hovered)
                notification.data.time += (notification.data.closing ? -0.02 : 0.02) * (event.renderTickTime * 3);
            if (notification.data.closing && notification.data.time <= 0)
                notifications.remove(notification);
        }
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

        public Notification clone() {
            return new Notification(title, description, colour, duration, clickRunnable);
        }

        public void close() {
            data.closing = true;
        }

        public void click() {
            if (clickRunnable != null)
                clickRunnable.click(this);
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
        private float xPosition;
        private boolean closing;
        private boolean clicked;
        NotificationData(float time, boolean closing, boolean clicked) {
            this.time = time;
            this.closing = closing;
            this.clicked = clicked;
        }
    }

}