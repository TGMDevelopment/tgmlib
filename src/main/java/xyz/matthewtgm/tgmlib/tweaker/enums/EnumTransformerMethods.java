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
import xyz.matthewtgm.tgmlib.tweaker.TGMLibClassTransformer;

public enum EnumTransformerMethods {

    runTick("runTick", "func_71407_l", "s", "()V"),
    dispatchKeypresses("dispatchKeypresses", "func_152348_aa", "Z", "()V"),
    renderString("renderString", "func_180455_b", "b", "(Ljava/lang/String;FFIZ)I"),
    updateTimer("updateTimer", "func_74275_a", "a", "()V"),
    addPotionEffect("addPotionEffect", "func_70690_d", "c", "(Lnet/minecraft/potion/PotionEffect;)V", "(" + EnumTransformerClasses.PotionEffect.getName() +")V"),
    dropOneItem("dropOneItem", "func_71040_bB", "a", "(Z)Lnet/minecraft/entity/item/EntityItem;", "(Z)Luz;"),
    sendChatMessage("sendChatMessage", "func_71165_d", "e", "(Ljava/lang/String;)V"),
    addToSendQueue("addToSendQueue", "func_147297_a", "a", "(Lnet/minecraft/network/Packet;)V"),
    handleJoinGame("handleJoinGame", "func_147282_a", "a", "(Lnet/minecraft/network/play/server/S01PacketJoinGame;)V", "(Lgt;)V"),
    channelRead0("channelRead0", "channelRead0", "a", "(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V"),
    shouldRender("shouldRender", "func_177071_a", "a", "(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;DDD)Z", "(Lpk;Lbia;DDD)Z"),
    getLocationCape("getLocationCape", "func_110303_q", "k", "()Lnet/minecraft/util/ResourceLocation;", "()Ljy;"),
    printChatMessage("printChatMessage", "func_146227_a", "a", "(Lnet/minecraft/util/IChatComponent;)V", "(Leu;)V"),
    clearChatMessages("clearChatMessages", "func_146231_a", "a", "()V"),

    init("<init>", "<init>", "<init>", "()V");

    private String name;
    private String description;
    private String[] exceptions = null;

    EnumTransformerMethods(String deobfMethod, String seargeMethod, String notchMethod18, String seargeDescription) {
        this(deobfMethod, seargeMethod, notchMethod18, seargeDescription, seargeDescription, false);
    }

    EnumTransformerMethods(String deobfMethod, String seargeMethod, String notchMethod18, String seargeDescription, String notchDescription) {
        this(deobfMethod, seargeMethod, notchMethod18, seargeDescription, notchDescription, false);
    }

    EnumTransformerMethods(String deobfMethod, String seargeMethod, String notchMethod18, String seargeDescription, boolean ioException) {
        this(deobfMethod, seargeMethod, notchMethod18, seargeDescription, seargeDescription, ioException);
    }

    EnumTransformerMethods(String deobfMethod, String seargeMethod, String notchMethod18, String seargeDescription, String notchDescription, boolean ioException) {
        if (TGMLibClassTransformer.isDeobfuscated()) {
            name = deobfMethod;
            description = seargeDescription;
        } else {
            if (TGMLibClassTransformer.isUsingNotchMappings()) {
                name = notchMethod18;
                description = notchDescription;
            } else {
                name = seargeMethod;
                description = seargeDescription;
            }
        }
        if (ioException) exceptions = new String[] {"java/io/IOException"};
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