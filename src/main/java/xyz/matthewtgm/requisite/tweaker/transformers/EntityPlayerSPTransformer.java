/*
 * Requisite - Minecraft library mod
 * Copyright (C) 2021 MatthewTGM
 *
 * Requisite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * Requisite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Requisite. If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.matthewtgm.requisite.tweaker.transformers;

import org.objectweb.asm.tree.*;
import xyz.matthewtgm.quickasm.QuickASM;
import xyz.matthewtgm.quickasm.interfaces.ITransformer;
import xyz.matthewtgm.quickasm.types.BasicMethodInformation;
import xyz.matthewtgm.requisite.tweaker.enums.EnumTransformerClasses;
import xyz.matthewtgm.requisite.tweaker.enums.EnumTransformerMethods;

import static org.objectweb.asm.Opcodes.*;

public class EntityPlayerSPTransformer implements ITransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.EntityPlayerSP.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            // nameMatches(method.name, EnumTransformerMethods.dropOneItem) || nameMatches(method.name, "a") && method.desc.equals("(Z)Luz;")
            if (QuickASM.nameMatches(method,
                    new BasicMethodInformation(EnumTransformerMethods.dropOneItem.getName()),
                    new BasicMethodInformation("a", "(Z)Luz;"))) {
                QuickASM.insertBefore(method, method.instructions.getFirst(), list -> {
                    list.add(new VarInsnNode(ILOAD, 1)); /* dropAll */
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/requisite/tweaker/hooks/EntityPlayerSPHook", "callEvent", "(Z)Z", false));
                    LabelNode labelNode = new LabelNode();
                    list.add(new JumpInsnNode(IFEQ, labelNode));
                    list.add(new InsnNode(ACONST_NULL));
                    list.add(new InsnNode(ARETURN));
                    list.add(labelNode);
                });
            }
            if (QuickASM.nameMatches(method,
                    new BasicMethodInformation(EnumTransformerMethods.sendChatMessage.getName()),
                    new BasicMethodInformation("e", "(Ljava/lang/String;)V"))) {
                QuickASM.insertBefore(method, method.instructions.getFirst(), list -> {
                    list.add(new TypeInsnNode(NEW, "xyz/matthewtgm/requisite/events/SendChatMessageEvent"));
                    list.add(new InsnNode(DUP));
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(new VarInsnNode(ALOAD, 1));
                    list.add(new MethodInsnNode(INVOKESPECIAL, "xyz/matthewtgm/requisite/events/SendChatMessageEvent", "<init>", "(" + EnumTransformerClasses.EntityPlayerSP.getName() + "Ljava/lang/String;)V", false));
                    list.add(new VarInsnNode(ASTORE, 2));

                    list.add(new VarInsnNode(ALOAD, 2));
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/requisite/util/ForgeHelper", "postEvent", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
                    LabelNode labelNode = new LabelNode();
                    list.add(new JumpInsnNode(IFEQ, labelNode));
                    list.add(new InsnNode(RETURN));
                    list.add(labelNode);

                    list.add(new VarInsnNode(ALOAD, 2));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/requisite/events/SendChatMessageEvent", "message", "Ljava/lang/String;"));
                    list.add(new VarInsnNode(ASTORE, 1)); /* message = sendChatMessageEvent.message; */
                });
            }
        }
    }

}