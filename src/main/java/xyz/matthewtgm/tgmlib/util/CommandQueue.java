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

public class CommandQueue extends MessageQueue {

    /**
     * @param msg The command to queue
     * @param runnable The command listener.
     * @author MatthewTGM
     */
    public static void queue(String msg, Runnable runnable) {
        if (!msg.startsWith("/")) msg = "/" + msg;
        queue.add(new QueueEntry(msg, runnable));
    }

    /**
     * @param msg The command to queue.
     * @param runnable The command listener.
     * @param delay The delay after the list command to run this one.
     * @author MatthewTGM
     */
    public static void queue(String msg, Runnable runnable, long delay) {
        if (!msg.startsWith("/")) msg = "/" + msg;
        queue.add(new QueueEntry(msg, runnable, delay));
    }

    /**
     * @param msg The command to queue.
     * @author MatthewTGM
     */
    public static void queue(String msg) {
        if (!msg.startsWith("/")) msg = "/" + msg;
        queue(msg, () -> {});
    }

    /**
     * @param msg The command to queue.
     * @param delay The delay after the list command to run this one.
     * @author MatthewTGM
     */
    public static void queue(String msg, long delay) {
        if (!msg.startsWith("/")) msg = "/" + msg;
        queue(msg, () -> {}, delay);
    }

}