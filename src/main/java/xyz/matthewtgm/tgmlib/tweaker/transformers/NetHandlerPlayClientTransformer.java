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
import xyz.matthewtgm.tgmlib.util.AsmHelper;

import static org.objectweb.asm.Opcodes.*;

public class NetHandlerPlayClientTransformer implements TGMLibTransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.NetHandlerPlayClient.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            if (nameMatches(method.name, EnumTransformerMethods.addToSendQueue, "a") || method.desc.equals("(Lff;)V")) {
                method.instructions.insertBefore(method.instructions.getFirst(), AsmHelper.createQuickInsnList(list -> {
                    list.add(new VarInsnNode(ALOAD, 0)); /* this */
                    list.add(new VarInsnNode(ALOAD, 1)); /* packet */
                    list.add(new MethodInsnNode(INVOKESTATIC, hooksPackage() + "NetHandlerPlayClientHook", "callEvent", "(" + EnumTransformerClasses.NetHandlerPlayClient.getName() + EnumTransformerClasses.Packet.getName() + ")V", false)); /* NetHandlerPlayClientHook.callEvent(this, packet); */
                }));
            }
            if (nameMatches(method.name, EnumTransformerMethods.handleJoinGame, "a") || method.desc.equals("(Lgt;)V")) {
                method.instructions.insertBefore(method.instructions.getLast().getPrevious().getPrevious().getPrevious().getPrevious(), AsmHelper.createQuickInsnList(list -> {
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(EnumTransformerFields.netManager.getField(EnumTransformerClasses.NetHandlerPlayClient));
                    list.add(new MethodInsnNode(INVOKESTATIC, hooksPackage() + "NetHandlerPlayClientHook", "register", "(" + EnumTransformerClasses.NetworkManager.getName() + ")V", false));
                }));
            }
        }
    }

}