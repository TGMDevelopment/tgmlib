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
import xyz.matthewtgm.quickasm.QuickASM;
import xyz.matthewtgm.quickasm.interfaces.ITransformer;
import xyz.matthewtgm.quickasm.types.BasicMethodInformation;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerClasses;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerFields;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerMethods;
import xyz.matthewtgm.tgmlib.util.AsmHelper;

import static org.objectweb.asm.Opcodes.*;

public class GuiNewChatTransformer implements ITransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.GuiNewChat.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            if (QuickASM.nameMatches(method,
                    new BasicMethodInformation(EnumTransformerMethods.printChatMessage.getName()),
                    new BasicMethodInformation("a", "(Leu;)V"))) {
                QuickASM.insertBefore(method, method.instructions.getFirst(), list -> {
                    list.add(new TypeInsnNode(NEW, "xyz/matthewtgm/tgmlib/events/PrintChatMessageEvent"));
                    list.add(new InsnNode(DUP));
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(new VarInsnNode(ALOAD, 1));
                    list.add(new MethodInsnNode(INVOKESPECIAL, "xyz/matthewtgm/tgmlib/events/PrintChatMessageEvent", "<init>", "(" + EnumTransformerClasses.GuiNewChat.getName() + EnumTransformerClasses.IChatComponent.getName() + ")V", false));
                    list.add(new VarInsnNode(ASTORE, 2));

                    list.add(new VarInsnNode(ALOAD, 2));
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/tgmlib/util/ForgeHelper", "postEvent", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
                    LabelNode labelNode = new LabelNode();
                    list.add(new JumpInsnNode(IFEQ, labelNode));
                    list.add(new InsnNode(RETURN));
                    list.add(labelNode);

                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(new VarInsnNode(ALOAD, 2));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/tgmlib/events/PrintChatMessageEvent", "component", EnumTransformerClasses.IChatComponent.getName()));
                    list.add(new VarInsnNode(ASTORE, 1));
                });
            }
            if (QuickASM.nameMatches(method,
                    new BasicMethodInformation(EnumTransformerMethods.clearChatMessages.getName()),
                    new BasicMethodInformation("a", "()V"))) {
                QuickASM.insertBefore(method, method.instructions.getFirst(), list -> {
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(EnumTransformerFields.drawnChatLines.getField(EnumTransformerClasses.GuiNewChat));
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(EnumTransformerFields.chatLines.getField(EnumTransformerClasses.GuiNewChat));
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(EnumTransformerFields.sentMessages.getField(EnumTransformerClasses.GuiNewChat));
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/tgmlib/tweaker/hooks/GuiNewChatHook", "callChatClearEvent", "(" + EnumTransformerClasses.GuiNewChat.getName() + "Ljava/util/List;Ljava/util/List;Ljava/util/List;)Z", false));
                    LabelNode labelNode = new LabelNode();
                    list.add(new JumpInsnNode(IFEQ, labelNode));
                    list.add(new InsnNode(RETURN));
                    list.add(labelNode);
                });
            }
        }
    }
    
}