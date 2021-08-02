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

import lombok.Getter;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ForgeHelper {

    @Getter private static final List<Object> registeredListeners = new CopyOnWriteArrayList<>();

    private ForgeHelper() {}

    /**
     * @param event The event to post.
     * @return Whether or not the event was cancelled.
     * @author MatthewTGM
     */
    public static boolean postEvent(Event event) {
        return MinecraftForge.EVENT_BUS.post(event);
    }

    /**
     * @param listener The listener to register.
     * @author MatthewTGM
     */
    public static void registerEventListener(Object listener) {
        if (!registeredListeners.contains(listener)) {
            MinecraftForge.EVENT_BUS.register(listener);
            registeredListeners.add(listener);
        }
    }

    /**
     * @param listener The listener to unregister.
     * @author MatthewTGM
     */
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

    /**
     * @param listeners All listeners to unregister.
     * @author MatthewTGM
     */
    public static void unregisterEventListeners(Object... listeners) {
        for (Object listener : listeners)
            unregisterEventListener(listener);
    }

    /**
     * @param id The mod id.
     * @return Whether or not the mod is currently loaded.
     * @author Biscuit
     */
    public static boolean isModLoaded(String id) {
        return isModLoaded(id, null);
    }

    /**
     * @param id The mod id.
     * @param version The mod version.
     * @return Whether or not the mod is currently loaded.
     * @author Biscuit
     */
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

    /**
     * @return Whether or not we are in the development environment.
     * @author MatthewTGM
     */
    public static boolean isDevelopmentEnvironment() {
        Object gotten = Launch.blackboard.get("fml.deobfuscatedEnvironment");
        return gotten != null && (boolean) gotten;
    }

}