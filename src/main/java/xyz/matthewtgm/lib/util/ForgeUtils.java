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

package xyz.matthewtgm.lib.util;

import lombok.Getter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ForgeUtils {

    @Getter private static final List<Object> registeredListeners = new CopyOnWriteArrayList<>();

    /**
     * @param listeners All listeners to register.
     * @author MatthewTGM
     */
    public static boolean registerEventListeners(Object... listeners) {
        boolean val = false;
        for (Object listener : listeners) {
            if (!registeredListeners.contains(listener)) {
                MinecraftForge.EVENT_BUS.register(listener);
                registeredListeners.add(listener);
                val = true;
            } else
                val = false;
        }
        return val;
    }

    public static boolean unregisterEventListeners(Object... listeners) {
        boolean val = false;
        for (Object listener : listeners) {
            if (registeredListeners.contains(listener)) {
                MinecraftForge.EVENT_BUS.unregister(listener);
                registeredListeners.remove(listener);
                val = true;
            } else
                val = false;
        }
        return val;
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

}