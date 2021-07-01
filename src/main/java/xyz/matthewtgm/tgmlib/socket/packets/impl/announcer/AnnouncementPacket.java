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

package xyz.matthewtgm.tgmlib.socket.packets.impl.announcer;

import net.minecraft.util.EnumChatFormatting;
import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.tgmlib.socket.TGMLibSocket;
import xyz.matthewtgm.tgmlib.socket.packets.BasePacket;
import xyz.matthewtgm.tgmlib.util.ChatHelper;
import xyz.matthewtgm.tgmlib.util.Notifications;

public class AnnouncementPacket extends BasePacket {

    private final String password;
    private final String title;
    private final String description;

    public AnnouncementPacket(String password, String title, String description) {
        super("ANNOUNCEMENT", "ANNOUNCER", 2f);
        this.password = password;
        this.title = title;
        this.description = description;
    }

    public AnnouncementPacket() {
        this("", "", "");
    }

    public void write(TGMLibSocket socket) {
        data.add("title", title);
        data.add("description", description);
        data.add("password", password);
    }

    public void read(TGMLibSocket socket, JsonObject json) {
        JsonObject jsonData = json.get("data").getAsJsonObject();
        Notifications.push(jsonData.get("title").getAsString(), jsonData.get("description").getAsString());
        ChatHelper.sendMessage(EnumChatFormatting.GRAY + "[" + EnumChatFormatting.RED + EnumChatFormatting.BOLD + "TGMLib Announcement" + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + "] [" + jsonData.get("title").getAsString() + "]", jsonData.get("description").getAsString());
    }

    public void handle(TGMLibSocket socket) {}

}