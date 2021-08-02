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

package xyz.matthewtgm.requisite.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import xyz.matthewtgm.requisite.Requisite;

public final class ChatHelper {

    public static final String requisiteChatPrefix = ChatColour.GRAY + "[" + ChatColour.GREEN + ChatColour.BOLD + Requisite.NAME + ChatColour.RESET + ChatColour.GRAY + "]";

    private ChatHelper() {}

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
        ChatComponentText text = new ChatComponentText(prefix + ChatColour.RESET + " " + msg);
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
        ChatComponentText text = (ChatComponentText) prefix.appendSibling(new ChatComponentText(ChatColour.RESET + " " + msg));
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
        ChatComponentText msg = (ChatComponentText) prefix.appendSibling(new ChatComponentText(ChatColour.RESET + " ").appendSibling(text));
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
        ChatComponentText msg = (ChatComponentText) new ChatComponentText(prefix + ChatColour.RESET + " ").appendSibling(text);
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
        ChatComponentText text = new ChatComponentText(preString + ChatColour.RESET + " " + oString);
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