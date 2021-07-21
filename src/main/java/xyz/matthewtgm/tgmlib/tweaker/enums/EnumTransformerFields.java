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
import xyz.matthewtgm.tgmlib.tweaker.TGMLibClassTransformer;

public enum EnumTransformerFields {

    timer("timer", "field_71428_T", "Y", "Lnet/minecraft/util/Timer;"),
    tagMap("tagMap", "field_74784_a", "b", "Ljava/util/Map;"),
    displayedTitle("displayedTitle", "field_175201_x", "x", "Ljava/lang/String;"),
    displayedSubTitle("displayedSubTitle", "field_175200_y", "y", "Ljava/lang/String;"),
    recordPlaying("recordPlaying", "field_73838_g", "o", "Ljava/lang/String;"),
    renderPartialTicks("renderPartialTicks", "field_74281_c", "c", "F"),
    drawnChatLines("drawnChatLines", "field_146253_i", "i", "Ljava/util/List;"),
    netManager("netManager", "field_147302_e", "c", "Lnet/minecraft/network/NetworkManager;"),
    chatLines("chatLines", "field_146252_h", "h", "Ljava/util/List;"),
    sentMessages("sentMessages", "field_146248_g", "g", "Ljava/util/List;"),
    xSize("xSize", "field_146999_f", "f", "I"),
    ySize("ySize", "field_147000_g", "g", "I"),
    guiTop("guiTop", "field_147009_r", "r", "I"),
    guiLeft("guiLeft", "field_147003_i", "i", "I"),
    PositionedSound_volume("volume", "field_147662_b", "b", "F");

    @Getter private String name;
    @Getter private String type;

    EnumTransformerFields(String deobfName, String seargeName, String notchName18, String type) {
        this.type = type;

        if (TGMLibClassTransformer.isDeobfuscated()) {
            name = deobfName;
        } else {
            if (TGMLibClassTransformer.isUsingNotchMappings()) {
                name = notchName18;
            } else {
                name = seargeName;
            }
        }
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