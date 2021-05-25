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

package xyz.matthewtgm.lib.util.betterkeybinds;

import lombok.Getter;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to add Minecraft keybinds with ease.
 *
 * @author MatthewTGM
 */
public class KeyBindManager {

    @Getter
    private static final Map<KeyBind, KeyBinding> keyBinds = new HashMap<>();

    /**
     * Used to register new Minecraft keybinds.
     *
     * @param keyBind The keybind to register.
     * @author MatthewTGM
     */
    public static void register(KeyBind keyBind) {
        KeyBinding generated = new KeyBinding(keyBind.getName(), keyBind.getKeyCode(), keyBind.getCategory());
        keyBinds.put(keyBind, generated);
        ClientRegistry.registerKeyBinding(generated);
    }

    @SubscribeEvent
    protected void onKeyPressed(InputEvent.KeyInputEvent event) {
        for (Map.Entry<KeyBind, KeyBinding> entry : keyBinds.entrySet()) {
            if (entry.getValue().isPressed())
                entry.getKey().press();

            if (entry.getValue().isKeyDown())
                entry.getKey().hold();
        }
    }

}