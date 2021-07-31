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

import lombok.Getter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import xyz.matthewtgm.tgmlib.tweaker.TGMLibTransformationChecks;

/**
 * Adapted from SkyBlockAddons under MIT license.
 * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
 *
 * @author Biscuit
 */
public enum EnumTransformerFields {

    timer("timer", "field_71428_T", "Lnet/minecraft/util/Timer;"),
    enableGLErrorChecking("enableGLErrorChecking", "field_175619_R", "Z"),
    leftClickCounter("leftClickCounter", "field_71429_W", "I"),
    myNetworkManager("myNetworkManager", "field_71453_ak", "Lnet/minecraft/network/NetworkManager;"),
    tagMap("tagMap", "field_74784_a", "Ljava/util/Map;"),
    displayedTitle("displayedTitle", "field_175201_x", "Ljava/lang/String;"),
    displayedSubTitle("displayedSubTitle", "field_175200_y", "Ljava/lang/String;"),
    recordPlaying("recordPlaying", "field_73838_g", "Ljava/lang/String;"),
    recordPlayingUpFor("recordPlayingUpFor", "field_73845_h", "Ljava/lang/String;"),
    recordIsPlaying("recordIsPlaying", "field_73844_j", "Ljava/lang/String;"),
    drawnChatLines("drawnChatLines", "field_146253_i", "Ljava/util/List;"),
    netManager("netManager", "field_147302_e", "Lnet/minecraft/network/NetworkManager;"),
    chatLines("chatLines", "field_146252_h", "Ljava/util/List;"),
    sentMessages("sentMessages", "field_146248_g", "Ljava/util/List;"),
    xSize("xSize", "field_146999_f", "I"),
    ySize("ySize", "field_147000_g", "I"),
    guiTop("guiTop", "field_147009_r", "I"),
    guiLeft("guiLeft", "field_147003_i", "I"),
    PositionedSound_volume("volume", "field_147662_b", "F");

    @Getter private final String name;
    @Getter private final String type;

    EnumTransformerFields(String deobfName, String seargeName, String type) {
        this.type = type;

        if (TGMLibTransformationChecks.getDeobfuscated())
            name = deobfName;
        else
            name = seargeName;
    }

    public FieldInsnNode getField(EnumTransformerClasses currentClass) {
        return new FieldInsnNode(Opcodes.GETFIELD, currentClass.getNameRaw(), name, type);
    }

    public FieldInsnNode putField(EnumTransformerClasses currentClass) {
        return new FieldInsnNode(Opcodes.PUTFIELD, currentClass.getNameRaw(), name, type);
    }

    public boolean matches(FieldInsnNode fieldInsnNode) {
        return this.name.equals(fieldInsnNode.name) && this.type.equals(fieldInsnNode.desc);
    }

}