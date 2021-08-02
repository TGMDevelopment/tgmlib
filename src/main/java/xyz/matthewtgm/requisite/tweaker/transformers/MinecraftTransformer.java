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
import xyz.matthewtgm.requisite.tweaker.enums.EnumTransformerFields;
import xyz.matthewtgm.requisite.tweaker.enums.EnumTransformerMethods;
import xyz.matthewtgm.requisite.tweaker.hooks.RequisiteMinecraftAccessor;

import static org.objectweb.asm.Opcodes.*;

public class MinecraftTransformer implements ITransformer {

    public String[] classes() {
        return new String[] {EnumTransformerClasses.Minecraft.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            if (QuickASM.nameMatches(method,
                    new BasicMethodInformation(EnumTransformerMethods.dispatchKeyPresses.getName()),
                    new BasicMethodInformation("Z", "()V"))) {
                QuickASM.insertBefore(method, method.instructions.getFirst(), list -> {
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/requisite/tweaker/hooks/MinecraftHook", "dispatchTgmLibKeyPresses", "(" + EnumTransformerClasses.Minecraft.getName() + ")V", false));
                });
            }
        }

        QuickASM.convertAccessor(classNode, RequisiteMinecraftAccessor.class);
        QuickASM.createAccessorGetter(classNode, "getTimer", "()Lnet/minecraft/util/Timer;", EnumTransformerFields.timer.getField(EnumTransformerClasses.Minecraft), ARETURN);
        QuickASM.createAccessorGetter(classNode, "isEnableGLErrorChecking", "()Z", EnumTransformerFields.enableGLErrorChecking.getField(EnumTransformerClasses.Minecraft), IRETURN);
        QuickASM.createAccessorSetter(classNode, "setEnableGLErrorChecking", "(Z)V", ILOAD, EnumTransformerFields.enableGLErrorChecking.putField(EnumTransformerClasses.Minecraft));
        QuickASM.createAccessorGetter(classNode, "getLeftClickCounter", "()I", EnumTransformerFields.leftClickCounter.getField(EnumTransformerClasses.Minecraft), IRETURN);
        QuickASM.createAccessorSetter(classNode, "setLeftClickCounter", "(I)V", ILOAD, EnumTransformerFields.leftClickCounter.putField(EnumTransformerClasses.Minecraft));
        QuickASM.createAccessorGetter(classNode, "getMyNetworkManager", "()Lnet/minecraft/network/NetworkManager;", EnumTransformerFields.myNetworkManager.getField(EnumTransformerClasses.Minecraft), ARETURN);
    }

}