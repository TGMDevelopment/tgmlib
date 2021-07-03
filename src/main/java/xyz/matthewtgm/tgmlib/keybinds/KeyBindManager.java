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

package xyz.matthewtgm.tgmlib.keybinds;

import lombok.Getter;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import xyz.matthewtgm.tgmlib.TGMLib;

import java.util.ArrayList;
import java.util.List;

public class KeyBindManager {

    @Getter private static final List<KeyBind> keyBinds = new ArrayList<>();

    public static void register(KeyBind keyBind) {
        keyBinds.add(keyBind);
        TGMLib.getManager().getKeyBindConfigHandler().update();
    }

    public static void unregister(String name) {
        keyBinds.stream().filter(keyBind -> keyBind.name().equalsIgnoreCase(name)).findFirst().ifPresent(keyBinds::remove);
        TGMLib.getManager().getKeyBindConfigHandler().update();
    }

    public static void unregister(KeyBind keyBind) {
        unregister(keyBind.name());
    }

}