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

package xyz.matthewtgm.tgmlib.tweaker.transformers;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import xyz.matthewtgm.quickasm.QuickASM;
import xyz.matthewtgm.quickasm.interfaces.ITransformer;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerClasses;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerFields;
import xyz.matthewtgm.tgmlib.tweaker.hooks.TGMLibGuiIngameAccessor;

import static org.objectweb.asm.Opcodes.*;

public class GuiIngameTransformer implements ITransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.GuiIngame.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        QuickASM.convertAccessor(classNode, TGMLibGuiIngameAccessor.class);
        QuickASM.createAccessorGetter(classNode, "getRecordPlaying", "()Ljava/lang/String;", new FieldInsnNode(GETFIELD, "net/minecraft/client/gui/GuiIngame", EnumTransformerFields.recordPlaying.getName(), "Ljava/lang/String;"), ARETURN);
        QuickASM.createAccessorSetter(classNode, "setRecordPlaying", "(Ljava/lang/String;)V", ALOAD, new FieldInsnNode(PUTFIELD, "net/minecraft/client/gui/GuiIngame", EnumTransformerFields.recordPlaying.getName(), "Ljava/lang/String;"));
        QuickASM.createAccessorGetter(classNode, "getRecordPlayingUpFor", "()I", new FieldInsnNode(GETFIELD, "net/minecraft/client/gui/GuiIngame", EnumTransformerFields.recordPlayingUpFor.getName(), "I"), IRETURN);
        QuickASM.createAccessorSetter(classNode, "setRecordPlayingUpFor", "(I)V", ILOAD, new FieldInsnNode(PUTFIELD, "net/minecraft/client/gui/GuiIngame", EnumTransformerFields.recordPlayingUpFor.getName(), "I"));
        QuickASM.createAccessorGetter(classNode, "isRecordIsPlaying", "()Z", new FieldInsnNode(GETFIELD, "net/minecraft/client/gui/GuiIngame", EnumTransformerFields.recordIsPlaying.getName(), "Z"), IRETURN);
        QuickASM.createAccessorSetter(classNode, "setRecordIsPlaying", "(Z)V", ILOAD, new FieldInsnNode(PUTFIELD, "net/minecraft/client/gui/GuiIngame", EnumTransformerFields.recordIsPlaying.getName(), "Z"));

        QuickASM.createAccessorGetter(classNode, "getDisplayedTitle", "()Ljava/lang/String;", new FieldInsnNode(GETFIELD, "net/minecraft/client/gui/GuiIngame", EnumTransformerFields.displayedTitle.getName(), "Ljava/lang/String;"), ARETURN);
        QuickASM.createAccessorSetter(classNode, "setDisplayedTitle", "(Ljava/lang/String;)V", ALOAD, new FieldInsnNode(PUTFIELD, "net/minecraft/client/gui/GuiIngame", EnumTransformerFields.displayedTitle.getName(), "Ljava/lang/String;"));
        QuickASM.createAccessorGetter(classNode, "getDisplayedSubTitle", "()Ljava/lang/String;", new FieldInsnNode(GETFIELD, "net/minecraft/client/gui/GuiIngame", EnumTransformerFields.displayedSubTitle.getName(), "Ljava/lang/String;"), ARETURN);
        QuickASM.createAccessorSetter(classNode, "setDisplayedSubTitle", "(Ljava/lang/String;)V", ALOAD, new FieldInsnNode(PUTFIELD, "net/minecraft/client/gui/GuiIngame", EnumTransformerFields.displayedSubTitle.getName(), "Ljava/lang/String;"));
    }

}