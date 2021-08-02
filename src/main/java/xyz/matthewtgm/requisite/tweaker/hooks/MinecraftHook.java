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

package xyz.matthewtgm.requisite.tweaker.hooks;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.events.BetterInputEvent;
import xyz.matthewtgm.requisite.events.RequisiteEvent;
import xyz.matthewtgm.requisite.keybinds.KeyBind;
import xyz.matthewtgm.requisite.keybinds.KeyBindManager;
import xyz.matthewtgm.requisite.util.ForgeHelper;
import xyz.matthewtgm.requisite.util.MouseHelper;

import java.util.List;

public class MinecraftHook {

    public static void callMouseInputEvent(Minecraft mc) {
        MinecraftForge.EVENT_BUS.post(new BetterInputEvent.MouseInputEvent(
                mc.currentScreen,
                Mouse.getEventButton(),
                Mouse.getEventX(),
                Mouse.getEventY(),
                MouseHelper.getMouseX(),
                MouseHelper.getMouseY()
        ));
    }

    public static boolean callKeyInputEvent() {
        return MinecraftForge.EVENT_BUS.post(new BetterInputEvent.KeyboardInputEvent(
                Keyboard.getEventKey(),
                Keyboard.getEventCharacter(),
                Keyboard.getKeyCount(),
                Keyboard.isRepeatEvent(),
                Keyboard.areRepeatEventsEnabled()
        ));
    }

    public static void dispatchTgmLibKeyPresses(Minecraft mc) {
        if (callKeyInputEvent())
            return;
        List<KeyBind> keyBinds = KeyBindManager.getKeyBinds();
        boolean wereRepeatEventsEnabled = Keyboard.areRepeatEventsEnabled();
        Keyboard.enableRepeatEvents(true);
        int key = Keyboard.getEventKey();
        boolean down = Keyboard.getEventKeyState();
        boolean repeated = Keyboard.isRepeatEvent();
        if (!keyBinds.isEmpty()) {
            Requisite tgmLib = Requisite.getInstance();
            for (KeyBind keyBind : keyBinds) {
                if (!keyBind.worksInGuis() && mc.currentScreen == null || keyBind.worksInGuis()) {
                    if (key == keyBind.getKey()) {
                        if (down && !repeated) {
                            if (post(new RequisiteEvent.KeyEvent.KeyPressedEvent.Pre(tgmLib, keyBind)))
                                continue;
                            keyBind.pressed();
                            post(new RequisiteEvent.KeyEvent.KeyPressedEvent.Post(tgmLib, keyBind));
                        }
                        if (down && repeated) {
                            if (post(new RequisiteEvent.KeyEvent.KeyHeldEvent.Pre(tgmLib, keyBind)))
                                continue;
                            keyBind.held();
                            post(new RequisiteEvent.KeyEvent.KeyHeldEvent.Post(tgmLib, keyBind));
                        }
                        if (!down) {
                            if (post(new RequisiteEvent.KeyEvent.KeyReleasedEvent.Pre(tgmLib, keyBind)))
                                continue;
                            keyBind.released();
                            post(new RequisiteEvent.KeyEvent.KeyReleasedEvent.Post(tgmLib, keyBind));
                        }
                    }
                }
            }
        }
        Keyboard.enableRepeatEvents(wereRepeatEventsEnabled);
    }

    private static boolean post(Event event) {
        return ForgeHelper.postEvent(event);
    }

}