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

package xyz.matthewtgm.lib.other;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class StandardVersionChecker {

    private final Gson gson = new Gson();

    private String versJsonString;
    private JsonObject versJsonObject;

    public StandardVersionChecker(String url) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            StringBuilder builder = new StringBuilder();
            reader.lines().forEach(builder::append);
            versJsonString = builder.toString();
            versJsonObject = gson.fromJson(versJsonString, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLatestVersion() {
        try {
            return versJsonObject.get("version").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getLatestBeta() {
        try {
            return versJsonObject.get("beta").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getDownloadUrl() {
        try {
            return versJsonObject.get("download").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}