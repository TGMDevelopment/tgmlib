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
import xyz.matthewtgm.tgmlib.tweaker.hooks.TGMLibMinecraftAccessor;
import xyz.matthewtgm.tgmlib.util.AsmHelper;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

public class MinecraftTransformer implements TGMLibTransformer {

    public String[] getClassNames() {
        return new String[] {EnumTransformerClasses.Minecraft.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        convertAccessor(classNode, TGMLibMinecraftAccessor.class);
        createAccessorGetter(classNode, "getTimer", "()Lnet/minecraft/util/Timer;", EnumTransformerFields.timer.getField(EnumTransformerClasses.Minecraft), ARETURN);
        for (MethodNode method : classNode.methods) {
            if (EnumTransformerMethods.dispatchKeypresses.matches(method)) {
                method.instructions.insertBefore(method.instructions.getFirst(), AsmHelper.createQuickInsnList(list -> {
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(new MethodInsnNode(INVOKESTATIC, hooksPackage() + "MinecraftHook", "dispatchTgmLibKeyPresses", "(" + EnumTransformerClasses.Minecraft.getName() + ")V", false));
                }));
                method.instructions.insertBefore(method.instructions.getFirst(), AsmHelper.createQuickInsnList(list -> {
                    list.add(new MethodInsnNode(INVOKESTATIC, hooksPackage() + "MinecraftHook", "callKeyInputEvent", "()Z", false));
                    LabelNode labelNode = new LabelNode();
                    list.add(new JumpInsnNode(IFEQ, labelNode));
                    list.add(new InsnNode(RETURN));
                    list.add(labelNode);
                }));
            }
            /*if (EnumTransformerMethods.runTick.matches(method)) {
                Iterator<AbstractInsnNode> iterator = method.instructions.iterator();
                while (iterator.hasNext()) {
                    AbstractInsnNode next = iterator.next();
                    if (next instanceof MethodInsnNode) {
                        MethodInsnNode methodInsnNode = (MethodInsnNode) next;
                        if (methodInsnNode.owner.equals("net/minecraftforge/fml/common/FMLCommonHandler") && methodInsnNode.name.contains("fireMouseInput")) {
                            AsmHelper.debug(methodInsnNode.getNext().getNext().getNext());
                            method.instructions.insertBefore(methodInsnNode.getNext().getNext().getNext(), mouseInputEvent());
                        }
                    }
                }
            }*/
        }
    }

    /*private InsnList mouseInputEvent() {
        InsnList list = new InsnList();
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new MethodInsnNode(INVOKESTATIC, hooksPackage() + "MinecraftHook", "callMouseInputEvent", "()V", false));
        return list;
    }*/

}