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
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerFields;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerMethods;

import static org.objectweb.asm.Opcodes.*;

public class GuiNewChatTransformer implements TGMLibTransformer {

    public String[] getClassNames() {
        return new String[]{EnumTransformerClasses.GuiNewChat.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            if (EnumTransformerMethods.printChatMessage.matches(method))
                method.instructions.insertBefore(method.instructions.getFirst(), callPrintEvent());

            if (EnumTransformerMethods.clearChatMessages.matches(method))
                method.instructions.insertBefore(method.instructions.getFirst(), callChatClearEvent());
        }
    }

    private InsnList callPrintEvent() {
        /*
            if (GuiNewChatHook.callPrintEvent(this, chatComponent)) {
                return;
            }
         */
        InsnList list = new InsnList();
        list.add(new VarInsnNode(ALOAD, 0)); /* this */
        list.add(new VarInsnNode(ALOAD, 1)); /* chatComponent */
        list.add(new MethodInsnNode(INVOKESTATIC, hooksPackage() + "GuiNewChatHook", "callPrintEvent", "(" + EnumTransformerClasses.GuiNewChat.getName() + EnumTransformerClasses.IChatComponent.getName() + ")Z", false)); /* GuiNewChatHook.callPrintEvent(this, chatComponent) */
        LabelNode labelNode = new LabelNode(); /* if (GuiNewChatHook.callPrintEvent(this, chatComponent)) */
        list.add(new JumpInsnNode(IFEQ, labelNode));
        list.add(new InsnNode(RETURN)); /* return; */
        list.add(labelNode);
        return list;
    }

    private InsnList callChatClearEvent() {
        /*
            if (GuiNewChatHook.callClearChatEvent(this, this.drawnChatLines, this.chatLines, this.sentMessages)) {
                return;
            }
         */
        InsnList list = new InsnList();
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(EnumTransformerFields.drawnChatLines.getField(EnumTransformerClasses.GuiNewChat));
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(EnumTransformerFields.chatLines.getField(EnumTransformerClasses.GuiNewChat));
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(EnumTransformerFields.sentMessages.getField(EnumTransformerClasses.GuiNewChat));
        list.add(new MethodInsnNode(INVOKESTATIC, hooksPackage() + "GuiNewChatHook", "callChatClearEvent", "(" + EnumTransformerClasses.GuiNewChat.getName() + "Ljava/util/List;Ljava/util/List;Ljava/util/List;)Z", false));
        LabelNode labelNode = new LabelNode();
        list.add(new JumpInsnNode(IFEQ, labelNode));
        list.add(new InsnNode(RETURN));
        list.add(labelNode);
        return list;
    }
    
}