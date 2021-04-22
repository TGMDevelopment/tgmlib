package ga.matthewtgm.lib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatHandler {

    /**
     * @author MatthewTGM
     * @param msg The message to send.
     */
    public static void sendMessage(String msg) {
        if (msg == null)
            return;
        if (Minecraft.getMinecraft().thePlayer == null)
            return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
    }

    /**
     * @author MatthewTGM
     * @param text The text to send.
     */
    public static void sendMessage(ChatComponentText text) {
        if (text == null)
            return;
        if (Minecraft.getMinecraft().thePlayer == null)
            return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(text);
    }

    /**
     * @author MatthewTGM
     * @param o The object to send.
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