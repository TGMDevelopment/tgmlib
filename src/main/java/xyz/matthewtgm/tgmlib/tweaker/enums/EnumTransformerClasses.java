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

import xyz.matthewtgm.tgmlib.tweaker.TGMLibTransformationChecks;

/**
 * Adapted from SkyBlockAddons under MIT license.
 * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
 *
 * @author Biscuit
 */
public enum EnumTransformerClasses {

    Minecraft("net/minecraft/client/Minecraft"),
    IBossDisplayData("net/minecraft/entity/boss/IBossDisplayData"),
    NBTTagCompound("net/minecraft/nbt/NBTTagCompound"),
    BossStatus("net/minecraft/entity/boss/BossStatus"),
    FontRenderer("net/minecraft/client/gui/FontRenderer"),
    PositionedSound("net/minecraft/client/audio/PositionedSound"),
    NetHandlerPlayClient("net/minecraft/client/network/NetHandlerPlayClient"),
    NetworkManager("net/minecraft/network/NetworkManager"),
    Packet("net/minecraft/network/Packet"),
    PotionEffect("net/minecraft/potion/PotionEffect"),
    Entity("net/minecraft/entity/Entity"),
    EntityLivingBase("net/minecraft/entity/EntityLivingBase"),
    EntityPlayer("net/minecraft/entity/player/EntityPlayer"),
    EntityPlayerSP("net/minecraft/client/entity/EntityPlayerSP"),
    AbstractClientPlayer("net/minecraft/client/entity/AbstractClientPlayer"),
    Render("net/minecraft/client/renderer/entity/Render"),
    GuiScreen("net/minecraft/client/gui/GuiScreen"),
    GuiNewChat("net/minecraft/client/gui/GuiNewChat"),
    GuiIngameForge("net/minecraftforge/client/GuiIngameForge"),
    GuiIngame("net/minecraft/client/gui/GuiIngame"),
    GuiContainer("net/minecraft/client/gui/inventory/GuiContainer"),
    IChatComponent("net/minecraft/util/IChatComponent");

    private final String seargeClass;

    EnumTransformerClasses(String seargeClass) {
        this.seargeClass = seargeClass;
    }

    public String getNameRaw() {
        return seargeClass;
    }

    public String getName() {
        return "L" + seargeClass + ";";
    }

    public String getTransformerName() {
        return seargeClass.replaceAll("/", ".");
    }

}