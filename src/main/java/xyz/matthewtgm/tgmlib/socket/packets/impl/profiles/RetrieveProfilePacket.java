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

import javax.imageio.ImageIO;
import java.net.URL;

public class RetrieveProfilePacket extends BasePacket {

    private final String uuid;

    public RetrieveProfilePacket(String uuid) {
        super("RETRIEVE", "PROFILES", 3f);
        this.uuid = uuid;
    }

    public RetrieveProfilePacket() {
        this(null);
    }

    public void write(TGMLibSocket socket) {
        data.add("uuid", uuid);
    }

    public void read(TGMLibSocket socket, JsonObject json) {
        JsonObject jsonData = json.get("data").getAsJsonObject();

        if (!jsonData.hasKey("uuid") || !jsonData.hasKey("friend") || !jsonData.hasKey("icon_url") || !jsonData.hasKey("online")) return;

        ProfileManager profileManager = TGMLib.getManager().getProfileManager();
        try {
            profileManager.create(
                    jsonData.get("uuid").getAsString(),
                    jsonData.get("friend").getAsBoolean(),
                    ImageIO.read(new URL(jsonData.get("icon_url").getAsString())),
                    ProfileOnlineStatus.valueOf(jsonData.get("online").getAsString())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handle(TGMLibSocket socket) {}

}