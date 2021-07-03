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

import static org.objectweb.asm.Opcodes.*;

public class EntityPlayerSPTransformer implements TGMLibTransformer {

    public String[] getClassNames() {
        return new String[]{EnumTransformerClasses.EntityPlayerSP.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            if (EnumTransformerMethods.dropOneItem.matches(method))
                method.instructions.insertBefore(method.instructions.getFirst(), call());
        }
    }

    private InsnList call() {
        InsnList list = new InsnList();
        list.add(new VarInsnNode(ILOAD, 1)); /* dropAll */
        list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/tgmlib/tweaker/hooks/EntityPlayerSPHook", "callEvent", "(Z)Z", false)); /* EntityPlayerSPHook.callEvent(dropAll) */
        LabelNode labelNode = new LabelNode(); /* if (EntityPlayerSPHook.callEvent(dropAll)) */
        list.add(new JumpInsnNode(IFEQ, labelNode));
        list.add(new InsnNode(ACONST_NULL)); /* return null; */
        list.add(new InsnNode(ARETURN));
        list.add(labelNode);
        return list;
    }

}