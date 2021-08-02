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

package xyz.matthewtgm.requisite.tweaker.hooks;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraftforge.common.MinecraftForge;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.events.PacketEvent;
import xyz.matthewtgm.requisite.events.RequisiteEvent;
import xyz.matthewtgm.requisite.util.ForgeHelper;

import java.nio.charset.StandardCharsets;

public class NetHandlerPlayClientHook {

    public static void callEvent(NetHandlerPlayClient netHandler, Packet<?> packet) {
        MinecraftForge.EVENT_BUS.post(new PacketEvent.SendEvent(packet, netHandler));
    }

    public static void register(NetworkManager netManager) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes("Requisite".getBytes(StandardCharsets.UTF_8));
        if (ForgeHelper.postEvent(new RequisiteEvent.CustomRegisterPacketEvent.Pre(Requisite.getInstance(), netManager, byteBuf)))
            return;
        netManager.sendPacket(new C17PacketCustomPayload("REGISTER", new PacketBuffer(byteBuf)));
        ForgeHelper.postEvent(new RequisiteEvent.CustomRegisterPacketEvent.Post(Requisite.getInstance(), netManager));
    }

}