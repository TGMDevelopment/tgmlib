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

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

public class BossStatusTransformer implements ITransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.BossStatus.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
           if (QuickASM.nameMatches(method,
                   new BasicMethodInformation(EnumTransformerMethods.setBossStatus.getName()),
                   new BasicMethodInformation("a", "(Luc;Z)V"))) {
               Iterator<AbstractInsnNode> iterator = method.instructions.iterator();
               while (iterator.hasNext()) {
                   AbstractInsnNode next = iterator.next();
                   if (next instanceof FieldInsnNode && next.getOpcode() == PUTSTATIC) {
                       FieldInsnNode fieldInsnNode = (FieldInsnNode) next;
                       if (fieldInsnNode.name.equals("hasColorModifier")) {
                           method.instructions.insert(fieldInsnNode, AsmHelper.createQuickInsnList(list -> {
                               list.add(new TypeInsnNode(NEW, "xyz/matthewtgm/requisite/events/BossBarEvent$SetEvent"));
                               list.add(new InsnNode(DUP));
                               list.add(new MethodInsnNode(INVOKESPECIAL, "xyz/matthewtgm/requisite/events/BossBarEvent$SetEvent", "<init>", "()V", false));
                               list.add(new VarInsnNode(ASTORE, 3));

                               list.add(new VarInsnNode(ALOAD, 3));
                               list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/requisite/util/ForgeHelper", "postEvent", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
                           }));
                       }
                   }
               }
           }
        }
    }

}