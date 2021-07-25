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

package xyz.matthewtgm.tgmlib.util;

import org.objectweb.asm.tree.*;

import java.util.function.Consumer;

public class AsmHelper {

    /**
     * @param abstractInsnNode The node to debug.
     * @author Unknown
     */
    public static void debug(AbstractInsnNode abstractInsnNode) {
        String print;
        if (abstractInsnNode instanceof FieldInsnNode) {
            FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
            print = "(Field Node: " + fieldInsnNode.owner + "." + fieldInsnNode.name + " | " + fieldInsnNode.desc + ")";
        }
        else if (abstractInsnNode instanceof VarInsnNode) {
            String op = null;
            switch (abstractInsnNode.getOpcode()) {
                case 25:
                    op = "ALOAD";
                    break;
                case 58:
                    op = "ASTORE";
                    break;
                case 21:
                    op = "ILOAD";
                    break;
                case 54:
                    op = "ISTORE";
                    break;
                case 22:
                    op = "LLOAD";
                    break;
                case 55:
                    op = "LSTORE";
                    break;
                case 24:
                    op = "DLOAD";
                    break;
                case 57:
                    op = "DSTORE";
                    break;
            }
            print = "(Var Insn: (OP: " + op + " | VAR: " + ((VarInsnNode)abstractInsnNode).var + "))";
        }
        else if (abstractInsnNode instanceof MethodInsnNode) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            print = "(Method Node: " + methodInsnNode.owner + "." + methodInsnNode.name + methodInsnNode.desc + ")";
        }
        else if (abstractInsnNode instanceof LdcInsnNode)
            print = "(LDC: " + ((LdcInsnNode)abstractInsnNode).cst + ")";
        else if (abstractInsnNode instanceof LineNumberNode)
            print = "(Line Node: " + ((LineNumberNode)abstractInsnNode).line + ")";
        else if (abstractInsnNode instanceof LabelNode)
            print = "Label node";
        else if (abstractInsnNode instanceof JumpInsnNode)
            print = "Jump Insn";
        else
            print = abstractInsnNode.getClass().getSimpleName();
        System.out.println(print);
    }

    /**
     * @param runnable The list runnable.
     * @return The created list.
     * @author MatthewTGM
     */
    public static InsnList createQuickInsnList(Consumer<InsnList> runnable) {
        InsnList list = new InsnList();
        runnable.accept(list);
        return list;
    }

}