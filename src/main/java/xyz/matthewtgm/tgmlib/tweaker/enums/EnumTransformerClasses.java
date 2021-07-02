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

package xyz.matthewtgm.tgmlib.tweaker.enums;

import xyz.matthewtgm.tgmlib.tweaker.TGMLibClassTransformer;

@SuppressWarnings("all")
public enum EnumTransformerClasses {

    NetHandlerPlayClient("net/minecraft/client/network/NetHandlerPlayClient", "bcy"),
    NetworkManager("net/minecraft/network/NetworkManager", "ek"),
    Packet("net/minecraft/network/Packet", "ff"),
    PotionEffect("net/minecraft/potion/PotionEffect", "pf"),
    EntityLivingBase("net/minecraft/entity/EntityLivingBase", "pr"),
    EntityPlayerSP("net/minecraft/client/entity/EntityPlayerSP", "bew"),
    Render("net/minecraft/client/renderer/entity/Render", "biv"),
    GuiNewChat("net/minecraft/client/gui/GuiNewChat", "avt"),
    IChatComponent("net/minecraft/util/IChatComponent", "eu");

    private String name;
    private String seargeClass;
    private String notchClass18;

    EnumTransformerClasses(String seargeClass, String notchClass18) {
        this.seargeClass = seargeClass;
        this.notchClass18 = notchClass18;

        if (TGMLibClassTransformer.isDeobfuscated() || !TGMLibClassTransformer.isUsingNotchMappings())
            name = seargeClass;
        else
            name = notchClass18;
    }

    public String getNameRaw() {
        return name;
    }

    public String getName() {
        return "L" + name + ";";
    }

    public String getTransformerName() {
        return seargeClass.replaceAll("/", ".");
    }

}