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

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueue {

    private static final Minecraft mc = Minecraft.getMinecraft();

    private static final Queue<QueueEntry> queue = new ConcurrentLinkedQueue<>();
    private static long tickDelay = 25;
    private static long tickCounter;

    public static void queue(String msg, Runnable runnable) {
        queue.add(new QueueEntry(msg, runnable));
    }

    public static void queue(String msg, Runnable runnable, long delay) {
        queue.add(new QueueEntry(msg, runnable, delay));
    }

    public static void queue(String msg) {
        queue(msg, () -> {});
    }

    public static void queue(String msg, long delay) {
        queue(msg, () -> {}, delay);
    }

    @SubscribeEvent
    protected void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            tickCounter++;

            if (!queue.isEmpty()) {
                QueueEntry current = queue.element();
                if (current != null)
                    runEntry(current, current.getDelay() == null ? tickDelay : current.getDelay());
            }
        }
    }

    private void runEntry(QueueEntry entry, long delay) {
        if (tickCounter % delay == 0) {
            if (entry == null || entry.getMsg() == null)
                return;

            tickCounter = 0;

            if (mc.thePlayer != null)
                mc.thePlayer.sendChatMessage(entry.getMsg());
            entry.getRunnable().run();
            queue.remove(entry);
        }
    }

    private static class QueueEntry {
        @Getter private final String msg;
        @Getter private final Runnable runnable;
        @Getter private final Long delay;
        public QueueEntry(String msg, Runnable runnable) {
            this.msg = msg;
            this.runnable = runnable;
            this.delay = null;
        }
        public QueueEntry(String msg, Runnable runnable, Long delay) {
            this.msg = msg;
            this.runnable = runnable;
            this.delay = delay;
        }
    }

}