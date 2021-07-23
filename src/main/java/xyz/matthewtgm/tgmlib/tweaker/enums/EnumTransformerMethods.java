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

package xyz.matthewtgm.tgmlib.tweaker.enums;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import xyz.matthewtgm.tgmlib.tweaker.TGMLibTransformationChecks;

/**
 * Adapted from SkyBlockAddons under MIT license.
 * https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
 *
 * @author Biscuit
 */
public enum EnumTransformerMethods {

    setBossStatus("setBossStatus", "func_82824_a", "(Lnet/minecraft/entity/boss/IBossDisplayData;Z)V"),
    renderBossHealth("renderBossHealth", "func_73828_d", "()V"),
    renderString("renderString", "func_180455_b", "(Ljava/lang/String;FFIZ)I"),
    addPotionEffect("addPotionEffect", "func_70690_d", "(" + EnumTransformerClasses.PotionEffect.getName() +")V"),
    dropOneItem("dropOneItem", "func_71040_bB", "(Z)Lnet/minecraft/entity/item/EntityItem;"),
    sendChatMessage("sendChatMessage", "func_71165_d", "(Ljava/lang/String;)V"),
    isInBed("isInBed", "func_175143_p", "()Z"),
    addToSendQueue("addToSendQueue", "func_147297_a", "(Lnet/minecraft/network/Packet;)V"),
    handleJoinGame("handleJoinGame", "func_147282_a", "(Lnet/minecraft/network/play/server/S01PacketJoinGame;)V"),
    channelRead0("channelRead0", "channelRead0", "(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V"),
    shouldRender("shouldRender", "func_177071_a", "(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;DDD)Z"),
    printChatMessage("printChatMessage", "func_146227_a", "(Lnet/minecraft/util/IChatComponent;)V"),
    clearChatMessages("clearChatMessages", "func_146231_a", "()V"),

    init("<init>", "<init>", "()V");

    private final String name;
    private final String description;
    private String[] exceptions = null;


    EnumTransformerMethods(String deobfMethod, String seargeMethod, String seargeDescription) {
        if (TGMLibTransformationChecks.getDeobfuscated())
            name = deobfMethod;
        else
            name = seargeMethod;

        description = seargeDescription;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public MethodNode createMethodNode() {
        return new MethodNode(Opcodes.ACC_PUBLIC, name, description, null, exceptions);
    }

    public boolean matches(MethodInsnNode methodInsnNode) {
        return this.name.equals(methodInsnNode.name) && this.description.equals(methodInsnNode.desc);
    }

    public boolean matches(MethodNode methodNode) {
        return this.name.equals(methodNode.name) && (this.description.equals(methodNode.desc) || this == init);
    }

}