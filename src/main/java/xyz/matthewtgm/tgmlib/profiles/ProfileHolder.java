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

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

public class ProfileHolder {

    @Getter private final String uuid;
    @Getter @Setter private boolean friend;
    @Getter @Setter private BufferedImage icon;
    @Getter @Setter private ProfileOnlineStatus onlineStatus;

    public ProfileHolder(String uuid, boolean friend, BufferedImage icon, ProfileOnlineStatus onlineStatus) {
        this.uuid = uuid;
        this.friend = friend;
        this.icon = icon;
        this.onlineStatus = onlineStatus;
    }

    public ProfileHolder get() {
        return null;
    }

    public ProfileHolder refresh() {
        return get();
    }

}