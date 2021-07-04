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

package xyz.matthewtgm.tgmlib.tweaker.hooks;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import xyz.matthewtgm.tgmlib.keybinds.KeyBind;
import xyz.matthewtgm.tgmlib.keybinds.KeyBindManager;

import java.util.List;

public class MinecraftHook {

    public static void dispatchTgmLibKeyPresses(Minecraft mc) {
        List<KeyBind> keyBinds = KeyBindManager.getKeyBinds();
        boolean wereRepeatEventsEnabled = Keyboard.areRepeatEventsEnabled();
        Keyboard.enableRepeatEvents(true);
        int key = Keyboard.getEventKey();
        boolean down = Keyboard.getEventKeyState();
        boolean repeated = Keyboard.isRepeatEvent();
        if (mc.currentScreen == null && !keyBinds.isEmpty()) {
            for (KeyBind keyBind : keyBinds) {
                if (key == keyBind.getKey()) {
                    if (down && !repeated) keyBind.pressed();
                    if (down && repeated) keyBind.held();
                    if (!down) keyBind.released();
                }
            }
        }
        Keyboard.enableRepeatEvents(wereRepeatEventsEnabled);
    }

}