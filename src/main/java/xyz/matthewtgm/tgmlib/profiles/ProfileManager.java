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

package xyz.matthewtgm.tgmlib.profiles;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.socket.packets.impl.profiles.OnlineStatusUpdatePacket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.profiles.RetrieveProfilePacket;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ProfileManager {

    @Getter private static boolean loaded;
    @Getter private final List<ProfileHolder> cachedProfiles = new ArrayList<>();
    @Getter private final Multimap<String, String> receivedMessages = ArrayListMultimap.create();

    public void start() {
        initialize();
        TGMLib.getManager().getWebSocket().send(new RetrieveProfilePacket(Minecraft.getMinecraft().getSession().getProfile().getId().toString()));
        loaded = true;
    }

    private void initialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> updateOnlineStatus(Minecraft.getMinecraft().getSession().getProfile().getId().toString(), ProfileOnlineStatus.OFFLINE, true), "TGMLib ProfileManager Shutdown"));
    }

    public void cache(ProfileHolder profile) {
        cachedProfiles.add(profile);
    }

    public void create(String uuid, boolean friend, BufferedImage icon, ProfileOnlineStatus onlineStatus) {
        cache(new ProfileHolder(uuid, friend, icon, onlineStatus));
    }

    public void receiveMessage(String uuid, String message) {
        receivedMessages.put(uuid, message);
    }

    public void updateOnlineStatus(String uuid, ProfileOnlineStatus onlineStatus, boolean send) {
        if (send) TGMLib.getManager().getWebSocket().send(new OnlineStatusUpdatePacket(uuid, onlineStatus));
        else cachedProfiles.stream().filter(profile -> profile.getUuid().matches(uuid)).findFirst().ifPresent(profile -> profile.setOnlineStatus(onlineStatus));
    }

}