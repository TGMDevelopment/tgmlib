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

import org.objectweb.asm.tree.*;
import xyz.matthewtgm.tgmlib.tweaker.TGMLibTransformer;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerClasses;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerMethods;
import xyz.matthewtgm.tgmlib.util.AsmHelper;

import static org.objectweb.asm.Opcodes.*;

public class EntityPlayerSPTransformer implements TGMLibTransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.EntityPlayerSP.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            if (nameMatches(method.name, EnumTransformerMethods.dropOneItem, "a")) {
                method.instructions.insertBefore(method.instructions.getFirst(), AsmHelper.createQuickInsnList(list -> {
                    list.add(new VarInsnNode(ILOAD, 1)); /* dropAll */
                    list.add(new MethodInsnNode(INVOKESTATIC, hooksPackage() + "EntityPlayerSPHook", "callEvent", "(Z)Z", false));
                    LabelNode labelNode = new LabelNode();
                    list.add(new JumpInsnNode(IFEQ, labelNode));
                    list.add(new InsnNode(ACONST_NULL));
                    list.add(new InsnNode(ARETURN));
                    list.add(labelNode);
                }));
            }
            if (nameMatches(method.name, EnumTransformerMethods.sendChatMessage, "e")) {
                method.instructions.insertBefore(method.instructions.getFirst(), AsmHelper.createQuickInsnList(list -> {
                    list.add(new TypeInsnNode(NEW, "xyz/matthewtgm/tgmlib/events/SendChatMessageEvent"));
                    list.add(new InsnNode(DUP));
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(new VarInsnNode(ALOAD, 1));
                    list.add(new MethodInsnNode(INVOKESPECIAL, "xyz/matthewtgm/tgmlib/events/SendChatMessageEvent", "<init>", "(" + EnumTransformerClasses.EntityPlayerSP.getName() + "Ljava/lang/String;)V", false));
                    list.add(new VarInsnNode(ASTORE, 2));

                    list.add(new VarInsnNode(ALOAD, 2));
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/tgmlib/util/ForgeHelper", "postEvent", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
                    LabelNode labelNode = new LabelNode();
                    list.add(new JumpInsnNode(IFEQ, labelNode));
                    list.add(new InsnNode(RETURN));
                    list.add(labelNode);

                    list.add(new VarInsnNode(ALOAD, 2));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/tgmlib/events/SendChatMessageEvent", "message", "Ljava/lang/String;"));
                    list.add(new VarInsnNode(ASTORE, 1)); /* message = sendChatMessageEvent.message; */
                }));
            }
        }
    }

}