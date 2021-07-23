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

import static org.objectweb.asm.Opcodes.*;

public class MinecraftTransformer implements TGMLibTransformer {

    public String[] classes() {
        return new String[] {EnumTransformerClasses.Minecraft.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            if (method.name.equals("Z") && method.desc.equals("()V")) {
                method.instructions.insertBefore(method.instructions.getFirst(), AsmHelper.createQuickInsnList(list -> {
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(new MethodInsnNode(INVOKESTATIC, hooksPackage() + "MinecraftHook", "dispatchTgmLibKeyPresses", "(" + EnumTransformerClasses.Minecraft.getName() + ")V", false));
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

        convertAccessorOrInvoker(classNode, TGMLibMinecraftAccessor.class);
        createAccessorGetter(classNode, "getTimer", "()Lnet/minecraft/util/Timer;", EnumTransformerFields.timer.getField(EnumTransformerClasses.Minecraft), ARETURN);
        createAccessorGetter(classNode, "isEnableGLErrorChecking", "()Z", EnumTransformerFields.enableGLErrorChecking.getField(EnumTransformerClasses.Minecraft), IRETURN);
        createAccessorSetter(classNode, "setEnableGLErrorChecking", "(Z)V", ILOAD, EnumTransformerFields.enableGLErrorChecking.putField(EnumTransformerClasses.Minecraft));
        createAccessorGetter(classNode, "getLeftClickCounter", "()I", EnumTransformerFields.leftClickCounter.getField(EnumTransformerClasses.Minecraft), IRETURN);
        createAccessorSetter(classNode, "setLeftClickCounter", "(I)V", ILOAD, EnumTransformerFields.leftClickCounter.putField(EnumTransformerClasses.Minecraft));
        createAccessorGetter(classNode, "getMyNetworkManager", "()Lnet/minecraft/network/NetworkManager;", EnumTransformerFields.myNetworkManager.getField(EnumTransformerClasses.Minecraft), ARETURN);

    }

    /*private InsnList mouseInputEvent() {
        InsnList list = new InsnList();
        list.add(new VarInsnNode(ALOAD, 0));
        list.add(new MethodInsnNode(INVOKESTATIC, hooksPackage() + "MinecraftHook", "callMouseInputEvent", "()V", false));
        return list;
    }*/

}