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
import xyz.matthewtgm.tgmlib.tweaker.TGMLibTransformer;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerClasses;
import xyz.matthewtgm.tgmlib.tweaker.hooks.TGMLibGuiIngameAccessor;

import static org.objectweb.asm.Opcodes.*;

public class GuiIngameTransformer implements TGMLibTransformer {

    public String[] getClassNames() {
        return new String[]{EnumTransformerClasses.GuiIngame.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        convertAccessorOrInvoker(classNode, TGMLibGuiIngameAccessor.class);
        createAccessorGetter(classNode, "getRecordPlaying", "()Ljava/lang/String;", new FieldInsnNode(GETFIELD, "net/minecraft/client/gui/GuiIngame", "recordPlaying", "Ljava/lang/String;"), ARETURN);
        createAccessorSetter(classNode, "setRecordPlaying", "(Ljava/lang/String;)V", ALOAD, new FieldInsnNode(PUTFIELD, "net/minecraft/client/gui/GuiIngame", "recordPlaying", "Ljava/lang/String;"));
        createAccessorGetter(classNode, "getRecordPlayingUpFor", "()I", new FieldInsnNode(GETFIELD, "net/minecraft/client/gui/GuiIngame", "recordPlayingUpFor", "I"), IRETURN);
        createAccessorSetter(classNode, "setRecordPlayingUpFor", "(I)V", ILOAD, new FieldInsnNode(PUTFIELD, "net/minecraft/client/gui/GuiIngame", "recordPlayingUpFor", "I"));
        createAccessorGetter(classNode, "isRecordIsPlaying", "()Z", new FieldInsnNode(GETFIELD, "net/minecraft/client/gui/GuiIngame", "recordIsPlaying", "Z"), IRETURN);
        createAccessorSetter(classNode, "setRecordIsPlaying", "(Z)V", ILOAD, new FieldInsnNode(PUTFIELD, "net/minecraft/client/gui/GuiIngame", "recordIsPlaying", "Z"));

        createAccessorGetter(classNode, "getDisplayedTitle", "()Ljava/lang/String;", new FieldInsnNode(GETFIELD, "net/minecraft/client/gui/GuiIngame", "displayedTitle", "Ljava/lang/String;"), ARETURN);
        createAccessorSetter(classNode, "setDisplayedTitle", "(Ljava/lang/String;)V", ALOAD, new FieldInsnNode(PUTFIELD, "net/minecraft/client/gui/GuiIngame", "displayedTitle", "Ljava/lang/String;"));
        createAccessorGetter(classNode, "getDisplayedSubTitle", "()Ljava/lang/String;", new FieldInsnNode(GETFIELD, "net/minecraft/client/gui/GuiIngame", "displayedSubTitle", "Ljava/lang/String;"), ARETURN);
        createAccessorSetter(classNode, "setDisplayedSubTitle", "(Ljava/lang/String;)V", ALOAD, new FieldInsnNode(PUTFIELD, "net/minecraft/client/gui/GuiIngame", "displayedSubTitle", "Ljava/lang/String;"));
    }

}