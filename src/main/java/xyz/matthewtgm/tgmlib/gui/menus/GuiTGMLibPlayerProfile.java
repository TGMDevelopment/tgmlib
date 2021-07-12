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

package xyz.matthewtgm.tgmlib.gui.menus;

import net.minecraft.client.gui.GuiScreen;
import xyz.matthewtgm.tgmlib.gui.GuiTGMLibBase;

import java.awt.image.BufferedImage;

public class GuiTGMLibPlayerProfile extends GuiTGMLibBase {

    private final String username;
    private final BufferedImage icon;

    public GuiTGMLibPlayerProfile(GuiScreen parent, String username, BufferedImage icon) {
        super(username + "'s profile", -1, parent);
        this.username = username;
        this.icon = icon;
    }

    public void initialize() {

    }

    public void draw(int mouseX, int mouseY, float partialTicks) {

    }

}