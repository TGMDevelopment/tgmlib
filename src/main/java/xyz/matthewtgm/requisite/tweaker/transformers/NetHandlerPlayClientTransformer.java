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

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

public class NetHandlerPlayClientTransformer implements ITransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.NetHandlerPlayClient.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            if (QuickASM.nameMatches(method,
                    new BasicMethodInformation(EnumTransformerMethods.addToSendQueue.getName()),
                    new BasicMethodInformation("a", "(Lff;)V"))) {
                QuickASM.insertBefore(method, method.instructions.getFirst(), list -> {
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(new VarInsnNode(ALOAD, 1));
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/requisite/tweaker/hooks/NetHandlerPlayClientHook", "callEvent", "(" + EnumTransformerClasses.NetHandlerPlayClient.getName() + EnumTransformerClasses.Packet.getName() + ")V", false)); /* NetHandlerPlayClientHook.callEvent(this, packet); */
                });
            }
            if (QuickASM.nameMatches(method,
                    new BasicMethodInformation(EnumTransformerMethods.handleJoinGame.getName()),
                    new BasicMethodInformation("a", "(Lgt;)V"))) {
                Iterator<AbstractInsnNode> iterator = method.instructions.iterator();
                while (iterator.hasNext()) {
                    AbstractInsnNode next = iterator.next();
                    if (next instanceof MethodInsnNode && next.getOpcode() == INVOKEVIRTUAL) {
                        MethodInsnNode methodInsnNode = (MethodInsnNode) next;
                        if (QuickASM.nameMatches(methodInsnNode,
                                new BasicMethodInformation(EnumTransformerMethods.sendPacket.getName()),
                                new BasicMethodInformation("a", "(Lff;)V"))) {
                            QuickASM.insert(method, methodInsnNode, list -> {
                                list.add(new VarInsnNode(ALOAD, 0));
                                list.add(EnumTransformerFields.netManager.getField(EnumTransformerClasses.NetHandlerPlayClient));
                                list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/requisite/tweaker/hooks/NetHandlerPlayClientHook", "register", "(" + EnumTransformerClasses.NetworkManager.getName() + ")V", false));
                            });
                        }
                    }
                }
            }
        }
    }

}