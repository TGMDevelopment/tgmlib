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

package xyz.matthewtgm.tgmlib.listeners;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;

public class ListenerManager {

    @Getter private static final Multimap<ListenerType, ListenerRunnable> listeners = ArrayListMultimap.create();

    public static void register(ListenerType type, ListenerRunnable runnable) {
        listeners.put(type, runnable);
    }

    public static void unregister(ListenerType type, ListenerRunnable runnable) {
        listeners.remove(type, runnable);
    }

    public interface ListenerRunnable {
        <R> void run(R object);
    }

}