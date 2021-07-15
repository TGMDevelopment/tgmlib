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

package xyz.matthewtgm.tgmlib.data;

import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.json.util.JsonApiHelper;
import xyz.matthewtgm.tgmlib.util.Multithreading;

import java.util.concurrent.TimeUnit;

public class StandardVersionChecker {

    private final String url;
    private JsonObject versionObject;

    public StandardVersionChecker(String url, boolean periodicallyFetch) {
        this.url = url;
        if (periodicallyFetch)
            Multithreading.schedule(this::fetch, 0, 10, TimeUnit.MINUTES);
    }

    public StandardVersionChecker(String url) {
        this(url, false);
    }

    public StandardVersionChecker fetch() {
        versionObject = JsonApiHelper.getJsonObject(url);
        return this;
    }

    public String getLatestVersion() {
        return versionObject.get("version").toString();
    }

    public String getLatestBeta() {
        return versionObject.get("beta").toString();
    }

    public String getDownloadUrl() {
        return versionObject.get("download").toString();
    }

    public String getBetaDownloadUrl() {
        return versionObject.get("beta_download").toString();
    }

    public boolean isLatestVersion(String version) {
        return getLatestVersion().matches(version);
    }

    public boolean isLatestBeta(String version) {
        return getLatestBeta().matches(version);
    }

}