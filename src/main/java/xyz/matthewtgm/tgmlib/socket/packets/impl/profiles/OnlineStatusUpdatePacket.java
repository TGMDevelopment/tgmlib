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

package xyz.matthewtgm.tgmlib.socket.packets.impl.profiles;

import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.profiles.ProfileManager;
import xyz.matthewtgm.tgmlib.profiles.ProfileOnlineStatus;
import xyz.matthewtgm.tgmlib.socket.TGMLibSocket;
import xyz.matthewtgm.tgmlib.socket.packets.BasePacket;

public class OnlineStatusUpdatePacket extends BasePacket {

    private final String uuid;
    private final ProfileOnlineStatus onlineStatus;

    public OnlineStatusUpdatePacket(String uuid, ProfileOnlineStatus onlineStatus) {
        super("ONLINE_STATUS_UPDATE", "PROFILES", 5f);
        this.uuid = uuid;
        this.onlineStatus = onlineStatus;
    }

    public OnlineStatusUpdatePacket() {
        this(null, null);
    }

    public void write(TGMLibSocket socket) {
        data.add("uuid", uuid);
        data.add("online_status", onlineStatus);
    }

    public void read(TGMLibSocket socket, JsonObject json) {
        JsonObject jsonData = json.get("data").getAsJsonObject();

        if (!jsonData.hasKey("online_status") || !jsonData.hasKey("uuid")) return;

        ProfileManager profileManager = TGMLib.getManager().getProfileManager();
        profileManager.updateOnlineStatus(jsonData.get("uuid").getAsString(), ProfileOnlineStatus.valueOf(jsonData.get("online_status").getAsString()), false);
    }

    public void handle(TGMLibSocket socket) {}

}