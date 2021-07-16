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

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class CustomClientRegistry {

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
        if (Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings).contains(key))
            Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.remove(Minecraft.getMinecraft().gameSettings.keyBindings, Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings).indexOf(key));
    }

}