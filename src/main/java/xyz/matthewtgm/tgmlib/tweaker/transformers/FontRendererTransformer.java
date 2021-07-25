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
import xyz.matthewtgm.tgmlib.util.AsmHelper;

import static org.objectweb.asm.Opcodes.*;

public class FontRendererTransformer implements TGMLibTransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.FontRenderer.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            if (nameMatches(method.name, EnumTransformerMethods.renderString, "b")) {
                method.instructions.insertBefore(method.instructions.getFirst(), AsmHelper.createQuickInsnList(list -> {
                    /*

                        Objective: Call `StringRenderedEvent` and set the parameter values to it's field values.



                        StringRenderedEvent stringRenderedEvent = new StringRenderedEvent(text, x, y, color, dropShadow);
                        ForgeHelper.postEvent(stringRenderedEvent);
                        text = stringRenderedEvent.text
                        x = stringRenderedEvent.x
                        y = stringRenderedEvent.y
                        colour = stringRenderedEvent.colour
                        dropShadow = stringRenderedEvent.dropShadow

                    */

                    list.add(new TypeInsnNode(NEW, "xyz/matthewtgm/tgmlib/events/StringRenderedEvent"));
                    list.add(new InsnNode(DUP));
                    list.add(new VarInsnNode(ALOAD, 1));
                    list.add(new VarInsnNode(FLOAD, 2));
                    list.add(new VarInsnNode(FLOAD, 3));
                    list.add(new VarInsnNode(ILOAD, 4));
                    list.add(new VarInsnNode(ILOAD, 5));
                    list.add(new MethodInsnNode(INVOKESPECIAL, "xyz/matthewtgm/tgmlib/events/StringRenderedEvent", "<init>", "(Ljava/lang/String;FFIZ)V", false));
                    list.add(new VarInsnNode(ASTORE, 7)); /* StringRenderedEvent stringRenderedEvent = new StringRenderedEvent(text, x, y, color, dropShadow); */


                    list.add(new VarInsnNode(ALOAD, 7));
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/tgmlib/util/ForgeHelper", "postEvent", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
                    /* ForgeHelper.postEvent(stringRenderedEvent); */


                    list.add(new VarInsnNode(ALOAD, 7));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/tgmlib/events/StringRenderedEvent", "text", "Ljava/lang/String;"));
                    list.add(new VarInsnNode(ASTORE, 1)); /* text = stringRenderedEvent.text; */

                    list.add(new VarInsnNode(ALOAD, 7));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/tgmlib/events/StringRenderedEvent", "x", "F"));
                    list.add(new VarInsnNode(FSTORE, 2)); /* x = stringRenderedEvent.x; */

                    list.add(new VarInsnNode(ALOAD, 7));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/tgmlib/events/StringRenderedEvent", "y", "F"));
                    list.add(new VarInsnNode(FSTORE, 3)); /* y = stringRenderedEvent.y; */

                    list.add(new VarInsnNode(ALOAD, 7));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/tgmlib/events/StringRenderedEvent", "colour", "I"));
                    list.add(new VarInsnNode(ISTORE, 4)); /* colour = stringRenderedEvent.colour; */

                    list.add(new VarInsnNode(ALOAD, 7));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/tgmlib/events/StringRenderedEvent", "dropShadow", "Z"));
                    list.add(new VarInsnNode(ISTORE, 5)); /* dropShadow = stringRenderedEvent.dropShadow; */
                }));
            }
        }
    }

}