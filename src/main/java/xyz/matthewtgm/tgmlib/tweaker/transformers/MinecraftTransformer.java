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
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerFields;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerMethods;
import xyz.matthewtgm.tgmlib.tweaker.hooks.TGMLibMinecraftAccessor;
import xyz.matthewtgm.tgmlib.util.AsmHelper;

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
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/tgmlib/tweaker/hooks/MinecraftHook", "dispatchTgmLibKeyPresses", "(" + EnumTransformerClasses.Minecraft.getName() + ")V", false));
                });
            }
        }

        QuickASM.convertAccessor(classNode, TGMLibMinecraftAccessor.class);
        QuickASM.createAccessorGetter(classNode, "getTimer", "()Lnet/minecraft/util/Timer;", EnumTransformerFields.timer.getField(EnumTransformerClasses.Minecraft), ARETURN);
        QuickASM.createAccessorGetter(classNode, "isEnableGLErrorChecking", "()Z", EnumTransformerFields.enableGLErrorChecking.getField(EnumTransformerClasses.Minecraft), IRETURN);
        QuickASM.createAccessorSetter(classNode, "setEnableGLErrorChecking", "(Z)V", ILOAD, EnumTransformerFields.enableGLErrorChecking.putField(EnumTransformerClasses.Minecraft));
        QuickASM.createAccessorGetter(classNode, "getLeftClickCounter", "()I", EnumTransformerFields.leftClickCounter.getField(EnumTransformerClasses.Minecraft), IRETURN);
        QuickASM.createAccessorSetter(classNode, "setLeftClickCounter", "(I)V", ILOAD, EnumTransformerFields.leftClickCounter.putField(EnumTransformerClasses.Minecraft));
        QuickASM.createAccessorGetter(classNode, "getMyNetworkManager", "()Lnet/minecraft/network/NetworkManager;", EnumTransformerFields.myNetworkManager.getField(EnumTransformerClasses.Minecraft), ARETURN);
    }

}