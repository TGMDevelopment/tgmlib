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

package ga.matthewtgm.lib.util.betterkeybinds;

import lombok.Getter;

/**
 * Custom keybind used to create a {@link net.minecraft.client.settings.KeyBinding}.
 * Must be used along with {@link KeyBindManager}.
 */
public abstract class KeyBind {

    @Getter
    private final String name;
    @Getter
    private final int keyCode;
    @Getter
    private final String category;

    public KeyBind(String description, int keyCode, String category) {
        this.name = description;
        this.keyCode = keyCode;
        this.category = category;
    }

    /**
     * Action performed on key press.
     */
    public abstract void press();

    /**
     * Action performed on key hold.
     */
    public abstract void hold();

}