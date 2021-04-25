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

package ga.matthewtgm.lib.util;

import ga.matthewtgm.lib.TGMLib;
import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;

public class ApiHelper {

    /**
     * @param stringUrl The url to fetch from.
     * @return The json returned as a string.
     * @author MatthewTGM
     */
    public static String getJsonOnline(String stringUrl) {
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setRequestProperty("User-Agent", TGMLib.NAME + "/" + TGMLib.VERSION);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                return IOUtils.toString(conn.getInputStream());
            return "{\"failed\": \"true\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"failed\": \"true\"}";
        }
    }

}