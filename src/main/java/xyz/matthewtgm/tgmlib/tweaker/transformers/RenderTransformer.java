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

public class RenderTransformer implements TGMLibTransformer {

    public String[] getClassNames() {
        return new String[]{EnumTransformerClasses.Render.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            if (EnumTransformerMethods.shouldRender.matches(method))
                method.instructions.insertBefore(method.instructions.getFirst(), callRenderCheck());
        }
    }

    private InsnList callRenderCheck() {
        InsnList list = new InsnList();
        list.add(new VarInsnNode(ALOAD, 1)); /* livingEntity */
        list.add(new MethodInsnNode(INVOKESTATIC, hooksPackage() + "RenderHook", "callRenderCheckEvent", "(Lnet/minecraft/entity/Entity;)Z", false)); /* RenderHook.callRenderCheckEvent(livingEntity) */
        LabelNode labelNode = new LabelNode(); /* if (RenderHook.callRenderCheckEvent(livingEntity)) */
        list.add(new JumpInsnNode(IFEQ, labelNode));
        list.add(new InsnNode(ICONST_0)); /* return false; */
        list.add(new InsnNode(IRETURN));
        list.add(labelNode);
        return list;
    }

}