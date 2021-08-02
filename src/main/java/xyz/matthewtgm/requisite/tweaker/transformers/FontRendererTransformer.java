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

public class FontRendererTransformer implements ITransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.FontRenderer.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            if (QuickASM.nameMatches(method,
                    new BasicMethodInformation(EnumTransformerMethods.renderString.getName()),
                    new BasicMethodInformation("b", "(Ljava/lang/String;FFIZ)I"))) {
                QuickASM.insertBefore(method, method.instructions.getFirst(), list -> {

                    list.add(new TypeInsnNode(NEW, "xyz/matthewtgm/requisite/events/StringRenderedEvent"));
                    list.add(new InsnNode(DUP));
                    list.add(new VarInsnNode(ALOAD, 1));
                    list.add(new VarInsnNode(FLOAD, 2));
                    list.add(new VarInsnNode(FLOAD, 3));
                    list.add(new VarInsnNode(ILOAD, 4));
                    list.add(new VarInsnNode(ILOAD, 5));
                    list.add(new MethodInsnNode(INVOKESPECIAL, "xyz/matthewtgm/requisite/events/StringRenderedEvent", "<init>", "(Ljava/lang/String;FFIZ)V", false));
                    list.add(new VarInsnNode(ASTORE, 7));


                    list.add(new VarInsnNode(ALOAD, 7));
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/requisite/util/ForgeHelper", "postEvent", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));


                    list.add(new VarInsnNode(ALOAD, 7));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/requisite/events/StringRenderedEvent", "text", "Ljava/lang/String;"));
                    list.add(new VarInsnNode(ASTORE, 1));

                    list.add(new VarInsnNode(ALOAD, 7));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/requisite/events/StringRenderedEvent", "x", "F"));
                    list.add(new VarInsnNode(FSTORE, 2));

                    list.add(new VarInsnNode(ALOAD, 7));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/requisite/events/StringRenderedEvent", "y", "F"));
                    list.add(new VarInsnNode(FSTORE, 3));

                    list.add(new VarInsnNode(ALOAD, 7));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/requisite/events/StringRenderedEvent", "colour", "I"));
                    list.add(new VarInsnNode(ISTORE, 4));

                    list.add(new VarInsnNode(ALOAD, 7));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/requisite/events/StringRenderedEvent", "dropShadow", "Z"));
                    list.add(new VarInsnNode(ISTORE, 5));
                });
            }
        }
    }

}
