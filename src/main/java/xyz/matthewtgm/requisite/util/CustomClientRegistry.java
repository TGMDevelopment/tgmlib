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

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

public final class CustomClientRegistry {

    private CustomClientRegistry() {}

    /**
     * @param key The keybinding to register.
     * @author MatthewTGM
     */
    public static void registerKeyBinding(KeyBinding key) {
        Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.add(Minecraft.getMinecraft().gameSettings.keyBindings, key);
    }

    /**
     * @param key The keybinding to unregister.
     * @author MatthewTGM
     */
    public static void unregisterKeyBinding(KeyBinding key) {
        List<KeyBinding> keyBindingList = Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings);
        if (keyBindingList.contains(key))
            Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.remove(Minecraft.getMinecraft().gameSettings.keyBindings, keyBindingList.indexOf(key));
    }

}