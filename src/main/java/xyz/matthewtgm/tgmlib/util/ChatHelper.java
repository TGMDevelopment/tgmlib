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

package xyz.matthewtgm.tgmlib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;

public class ChatHelper {

    public static final String tgmLibChatPrefix = ChatColour.GRAY + "[" + ChatColour.GOLD + ChatColour.BOLD + "TGMLib" + ChatColour.RESET + ChatColour.GRAY + "]";

    /**
     * @param msg The message to send.
     * @author MatthewTGM
     */
    public static void sendMessage(String msg) {
        if (msg == null)
            return;
        if (Minecraft.getMinecraft().thePlayer == null)
            return;
        ChatComponentText text = new ChatComponentText(msg);
        if (post(text))
            return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(text);
    }

    /**
     * @param prefix The prefix to be prepended to the message.
     * @param msg The message to send.
     * @author MatthewTGM
     */
    public static void sendMessage(String prefix, String msg) {
        if (msg == null)
            return;
        if (Minecraft.getMinecraft().thePlayer == null)
            return;
        ChatComponentText text = new ChatComponentText(prefix + EnumChatFormatting.RESET + " " + msg);
        if (post(text))
            return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(text);
    }

    /**
     * @param prefix The prefix to be prepended to the message.
     * @param msg The message to send.
     * @author MatthewTGM
     */
    public static void sendMessage(ChatComponentText prefix, String msg) {
        if (msg == null)
            return;
        if (Minecraft.getMinecraft().thePlayer == null)
            return;
        ChatComponentText text = (ChatComponentText) prefix.appendSibling(new ChatComponentText(EnumChatFormatting.RESET + " " + msg));
        if (post(text))
            return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(text);
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
        if (post(text))
            return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(text);
    }

    /**
     * @param text The text to send.
     * @author MatthewTGM
     */
    public static void sendMessage(ChatComponentText prefix, ChatComponentText text) {
        if (text == null)
            return;
        if (Minecraft.getMinecraft().thePlayer == null)
            return;
        ChatComponentText msg = (ChatComponentText) prefix.appendSibling(new ChatComponentText(EnumChatFormatting.RESET + " ").appendSibling(text));
        if (post(msg))
            return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(msg);
    }

    /**
     * @param text The text to send.
     * @author MatthewTGM
     */
    public static void sendMessage(String prefix, ChatComponentText text) {
        if (text == null)
            return;
        if (Minecraft.getMinecraft().thePlayer == null)
            return;
        ChatComponentText msg = (ChatComponentText) new ChatComponentText(prefix + EnumChatFormatting.RESET + " ").appendSibling(text);
        if (post(msg))
            return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(msg);
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

    /**
     * @param o The object to send.
     * @author MatthewTGM
     */
    public static void sendMessage(Object prefix, Object o) {
        if (o == null)
            return;
        if (Minecraft.getMinecraft().thePlayer == null)
            return;
        String preString = String.valueOf(prefix);
        String oString = String.valueOf(o);
        ChatComponentText text = new ChatComponentText(preString + EnumChatFormatting.RESET + " " + oString);
        if (post(text))
            return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(text);
    }

    private static boolean post(ChatComponentText component) {
        return MinecraftForge.EVENT_BUS.post(new ClientChatReceivedEvent((byte) 0, component));
    }

    public static ChatComponentText buildSimple(String input) {
        return new ChatComponentText(input);
    }

    public static ChatComponentTranslation buildTranslated(String input) {
        return new ChatComponentTranslation(input);
    }

    public static IChatComponent editChatComponent(IChatComponent component, ChatComponentEditRunnable runnable) {
        return runnable.edit(component.createCopy());
    }

    public interface ChatComponentEditRunnable {
        IChatComponent edit(IChatComponent component);
    }

}