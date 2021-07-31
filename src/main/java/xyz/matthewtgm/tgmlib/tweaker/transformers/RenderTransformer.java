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
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerMethods;
import xyz.matthewtgm.tgmlib.util.AsmHelper;

import static org.objectweb.asm.Opcodes.*;

public class RenderTransformer implements ITransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.Render.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            if (QuickASM.nameMatches(method,
                    new BasicMethodInformation(EnumTransformerMethods.shouldRender.getName()),
                    new BasicMethodInformation("a", "(Lpk;Lbia;DDD)Z"))) {
                QuickASM.insertBefore(method, method.instructions.getFirst(), list -> {
                    list.add(new VarInsnNode(ALOAD, 1));
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/tgmlib/tweaker/hooks/RenderHook", "callRenderCheckEvent", "(Lnet/minecraft/entity/Entity;)Z", false));
                    LabelNode labelNode = new LabelNode();
                    list.add(new JumpInsnNode(IFEQ, labelNode));
                    list.add(new InsnNode(ICONST_0));
                    list.add(new InsnNode(IRETURN));
                    list.add(labelNode);
                });
            }
            if (QuickASM.nameMatches(method,
                    new BasicMethodInformation(EnumTransformerMethods.renderLivingLabel.getName()),
                    new BasicMethodInformation("a", "(Lpk;Ljava/lang/String;DDDI)V"))) {
                QuickASM.insertBefore(method, method.instructions.getLast().getPrevious().getPrevious().getPrevious(), list -> {
                    list.add(new VarInsnNode(ALOAD, 1));
                    list.add(new VarInsnNode(ALOAD, 2));
                    list.add(new VarInsnNode(DLOAD, 3));
                    list.add(new VarInsnNode(DLOAD, 5));
                    list.add(new VarInsnNode(DLOAD, 7));
                    list.add(new VarInsnNode(ILOAD, 9));
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/tgmlib/tweaker/hooks/RenderHook", "renderIndicators", "(" + EnumTransformerClasses.Entity.getName() + "Ljava/lang/String;DDDI)V", false));
                });
            }
        }
    }

}