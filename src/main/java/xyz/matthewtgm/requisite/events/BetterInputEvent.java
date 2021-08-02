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

package xyz.matthewtgm.requisite.events;

import lombok.Getter;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class BetterInputEvent extends Event {
    public static class MouseInputEvent extends BetterInputEvent {
        public final GuiScreen currentScreen;
        public final MouseButton button;
        public final int buttonRaw;
        public final int mouseX;
        public final int mouseY;
        public final int rawMouseX;
        public final int rawMouseY;
        public MouseInputEvent(GuiScreen currentScreen, int buttonRaw, int mouseX, int mouseY, int rawMouseX, int rawMouseY) {
            this.currentScreen = currentScreen;
            this.button = MouseButton.fromRaw(buttonRaw);
            this.buttonRaw = buttonRaw;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.rawMouseX = rawMouseX;
            this.rawMouseY = rawMouseY;
        }
        public enum MouseButton {
            LEFT(0),
            MIDDLE(2),
            RIGHT(1),
            SIDE_BACK(3),
            SIDE_FRONT(4);
            @Getter private final int raw;
            MouseButton(int raw) {
                this.raw = raw;
            }
            public static MouseButton fromRaw(int raw) {
                for (MouseButton value : values()) if (value.raw == raw) return value;
                return null;
            }
        }
    }
    @Cancelable
    public static class KeyboardInputEvent extends BetterInputEvent {
        public final int keyCode;
        public final char typedChar;
        public final int keyCount;
        public final boolean repeated;
        public final boolean repeatable;
        public KeyboardInputEvent(int keyCode, char typedChar, int keyCount, boolean repeated, boolean repeatable) {
            this.keyCode = keyCode;
            this.typedChar = typedChar;
            this.keyCount = keyCount;
            this.repeated = repeated;
            this.repeatable = repeatable;
        }
    }
}