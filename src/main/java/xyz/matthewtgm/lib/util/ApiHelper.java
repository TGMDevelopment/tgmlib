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

package xyz.matthewtgm.lib.util;

import xyz.matthewtgm.json.base.Json;
import xyz.matthewtgm.json.parsing.JsonParser;
import xyz.matthewtgm.lib.TGMLib;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

public class ApiHelper {

    public static Object getPageContent(URL url) {
        try {
            return url.getContent();
        } catch (IOException e) {
            e.printStackTrace();
            return "EMPTY";
        }
    }

    /**
     * @param url The url to fetch from.
     * @return The json returned as a string.
     * @author MatthewTGM
     */
    public static String getJsonOnline(URL url) {
        try {
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

    /**
     * @param url The url to fetch from.
     * @return The json returned as a string.
     * @author MatthewTGM
     */
    public static String getJsonOnline(String url) {
        AtomicReference<String> json = new AtomicReference<>(null);
        ExceptionHelper.tryCatch(() -> json.set(getJsonOnline(new URL(url))));
        return json.get();
    }

    /**
     * @param uri The url to fetch from.
     * @return The json returned as a string.
     * @author MatthewTGM
     */
    public static String getJsonOnline(URI uri) {
        AtomicReference<String> json = new AtomicReference<>(null);
        ExceptionHelper.tryCatch(() -> json.set(getJsonOnline(uri.toURL())));
        return json.get();
    }

    public static Json getParsedJsonOnline(URL url, Class<? extends Json> typeOfJson) {
        return JsonParser.parse(getJsonOnline(url), typeOfJson);
    }

    public static Json getParsedJsonOnline(String url, Class<? extends Json> typeOfJson) {
        return JsonParser.parse(getJsonOnline(url), typeOfJson);
    }

    public static Json getParsedJsonOnline(URI uri, Class<? extends Json> typeOfJson) {
        return JsonParser.parse(getJsonOnline(uri), typeOfJson);
    }

}