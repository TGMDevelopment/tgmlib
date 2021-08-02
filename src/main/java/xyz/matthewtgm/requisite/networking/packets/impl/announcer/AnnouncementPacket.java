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

package xyz.matthewtgm.requisite.networking.packets.impl.announcer;

import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.networking.RequisiteClientSocket;
import xyz.matthewtgm.requisite.networking.packets.BasePacket;
import xyz.matthewtgm.requisite.util.ChatColour;
import xyz.matthewtgm.requisite.util.ChatHelper;
import xyz.matthewtgm.requisite.util.Notifications;

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

    public void write(RequisiteClientSocket socket) {
        data.add("title", title);
        data.add("description", description);
        data.add("password", password);
    }

    public void read(RequisiteClientSocket socket, JsonObject object, JsonObject data) {
        Notifications.push(data.get("title").getAsString(), data.get("description").getAsString());
        ChatHelper.sendMessage(ChatColour.GRAY + "[" + ChatColour.RED + ChatColour.BOLD + Requisite.NAME + " Announcement" + ChatColour.RESET + ChatColour.GRAY + "] [" + data.get("title").getAsString() + "]", data.get("description").getAsString());
    }

    public void handle(RequisiteClientSocket socket) {}

}