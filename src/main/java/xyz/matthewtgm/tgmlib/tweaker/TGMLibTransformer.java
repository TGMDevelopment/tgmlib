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

package xyz.matthewtgm.tgmlib.tweaker;

import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

public interface TGMLibTransformer {

    String[] getClassNames();

    void transform(ClassNode classNode, String name);

    default void createReturnValue(InsnList list, int var) {
        list.add(new TypeInsnNode(NEW, "xyz/matthewtgm/tgmlib/tweaker/util/ReturnValue"));
        list.add(new InsnNode(DUP));
        list.add(new MethodInsnNode(INVOKESPECIAL, "xyz/matthewtgm/tgmlib/tweaker/util/ReturnValue", "<init>", "()V", false));
        list.add(new VarInsnNode(ASTORE, var));
    }
    default void checkReturnValueCancellation(InsnList list, int var, ReturnValueCancellation cancellation) {
        list.add(new VarInsnNode(ALOAD, var));
        list.add(new MethodInsnNode(INVOKEVIRTUAL, "xyz/matthewtgm/tgmlib/tweaker/util/ReturnValue", "isCancelled", "()Z", false));
        LabelNode labelNode = new LabelNode();
        list.add(new JumpInsnNode(IFEQ, labelNode));
        if (cancellation.returnValue) {
            list.add(new VarInsnNode(ALOAD, var));
            list.add(new MethodInsnNode(INVOKEVIRTUAL, "xyz/matthewtgm/tgmlib/tweaker/util/ReturnValue", "getValue", "()Ljava/lang/Object;", false));
            list.add(new InsnNode(cancellation.valueOp));
        }
        list.add(new InsnNode(cancellation.returnOp));
        list.add(labelNode);
    }
    default String hooksPackage() {
        return "xyz/matthewtgm/tgmlib/tweaker/hooks/";
    }

    class ReturnValueCancellation {
        public final boolean returnValue;
        public final int valueOp;
        public final int returnOp;
        public ReturnValueCancellation(boolean returnValue, int valueOp, int returnOp) {
            this.returnValue = returnValue;
            this.valueOp = valueOp;
            this.returnOp = returnOp;
        }
        public ReturnValueCancellation(int returnOp) {
            this(false, -1, returnOp);
        }
    }

}