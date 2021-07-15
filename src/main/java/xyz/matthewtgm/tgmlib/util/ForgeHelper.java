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
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ForgeHelper {

    @Getter private static final List<Object> registeredListeners = new CopyOnWriteArrayList<>();

    public static boolean postEvent(Event event) {
        return MinecraftForge.EVENT_BUS.post(event);
    }

    public static void registerEventListener(Object listener) {
        if (!registeredListeners.contains(listener)) {
            MinecraftForge.EVENT_BUS.register(listener);
            registeredListeners.add(listener);
        }
    }

    public static void unregisterEventListener(Object listener) {
        MinecraftForge.EVENT_BUS.unregister(listener);
        registeredListeners.remove(listener);
    }

    /**
     * @param listeners All listeners to register.
     * @author MatthewTGM
     */
    public static void registerEventListeners(Object... listeners) {
        for (Object listener : listeners)
            registerEventListener(listener);
    }

    public static void unregisterEventListeners(Object... listeners) {
        for (Object listener : listeners)
            unregisterEventListener(listener);
    }

    public static boolean isModLoaded(String id) {
        return isModLoaded(id, null);
    }

    public static boolean isModLoaded(String id, String version) {
        boolean loaded = Loader.isModLoaded(id);
        if (loaded && version != null) {
            for (ModContainer container : Loader.instance().getModList())
                if (container.getModId().equalsIgnoreCase(id) && container.getVersion().equalsIgnoreCase(version))
                    return true;
            return false;
        }
        return loaded;
    }

    public static boolean isDevelopmentEnvironment() {
        Object gotten = Launch.blackboard.get("fml.deobfuscatedEnvironment");
        return gotten != null && (boolean) gotten;
    }

}