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

package ga.matthewtgm.lib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatHandler {

    /**
     * @param msg The message to send.
     * @author MatthewTGM
     */
    public static void sendMessage(String msg) {
        if (msg == null)
            return;
        if (Minecraft.getMinecraft().thePlayer == null)
            return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
    }

    /**
     * @param text The text to send.
     * @author MatthewTGM
     */
    public static void sendMessage(ChatComponentText text) {
        if (text == null)
            return;
        if (Minecraft.getMinecraft().thePlayer == null)
            return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(text);
    }

    /**
     * @param o The object to send.
     * @author MatthewTGM
     */
    public static void sendMessage(Object o) {
        if (o == null)
            return;
        if (Minecraft.getMinecraft().thePlayer == null)
            return;
        String toString = String.valueOf(o);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(toString));
    }

}