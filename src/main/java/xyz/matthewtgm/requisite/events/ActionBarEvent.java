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

import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ActionBarEvent extends Event {
    @Cancelable
    public static class RenderEvent extends ActionBarEvent {
        public String text;
        public final int width;
        public final int height;
        public final float partialTicks;
        public RenderEvent(String text, int width, int height, float partialTicks) {
            this.text = text;
            this.width = width;
            this.height = height;
            this.partialTicks = partialTicks;
        }
    }
    @Cancelable
    public static class ReceiveEvent extends ActionBarEvent {
        public IChatComponent component;
        public ReceiveEvent(IChatComponent component) {
            this.component = component;
        }
    }
}
