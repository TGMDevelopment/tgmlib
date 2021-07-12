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

package xyz.matthewtgm.tgmlib.tweaker.hooks;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraftforge.common.MinecraftForge;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.events.PacketEvent;
import xyz.matthewtgm.tgmlib.events.TGMLibEvent;
import xyz.matthewtgm.tgmlib.util.ForgeHelper;

import java.nio.charset.StandardCharsets;

public class NetHandlerPlayClientHook {

    public static void callEvent(NetHandlerPlayClient netHandler, Packet<?> packet) {
        MinecraftForge.EVENT_BUS.post(new PacketEvent.SendEvent(packet, netHandler));
    }

    public static void register(NetworkManager netManager) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes("TGMLib".getBytes(StandardCharsets.UTF_8));
        if (ForgeHelper.postEvent(new TGMLibEvent.CustomRegisterPacketEvent.Pre(TGMLib.getInstance(), netManager, byteBuf)))
            return;
        netManager.sendPacket(new C17PacketCustomPayload("REGISTER", new PacketBuffer(byteBuf)));
        ForgeHelper.postEvent(new TGMLibEvent.CustomRegisterPacketEvent.Post(TGMLib.getInstance(), netManager));
    }

}