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

package xyz.matthewtgm.tgmlib.events;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PacketEvent extends Event {
    public final Packet<?> packet;
    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }
    public static class SendEvent extends PacketEvent {
        public final NetHandlerPlayClient netHandler;
        public SendEvent(Packet<?> packet, NetHandlerPlayClient netHandler) {
            super(packet);
            this.netHandler = netHandler;
        }
    }
    public static class ReceiveEvent extends PacketEvent {
        public final NetworkManager networkManager;
        public ReceiveEvent(Packet<?> packet, NetworkManager networkManager) {
            super(packet);
            this.networkManager = networkManager;
        }
    }
}