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

package xyz.matthewtgm.requisite.util;

import xyz.matthewtgm.json.entities.JsonElement;
import xyz.matthewtgm.json.parser.JsonParser;
import org.apache.commons.io.IOUtils;
import xyz.matthewtgm.requisite.Requisite;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

public final class ApiHelper {

    private ApiHelper() {}

    /**
     * @param url The url of the page to fetch from.
     * @return The page's content from the specified URL.
     * @author MatthewTGM
     */
    public static Object getPageContent(URL url) {
        try {
            return url.getContent();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
            conn.setRequestProperty("User-Agent", Requisite.NAME + "/" + Requisite.VER);
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

    /**
     * @param url The url to fetch from.
     * @return A parsed JSON element from online data.
     * @author MatthewTGM
     */
    public static JsonElement getParsedJsonOnline(URL url) {
        return JsonParser.parse(getJsonOnline(url));
    }

    /**
     * @param url The url to fetch from.
     * @return A parsed JSON element from online data.
     * @author MatthewTGM
     */
    public static JsonElement getParsedJsonOnline(String url) {
        return JsonParser.parse(getJsonOnline(url));
    }

    /**
     * @param uri The uri to fetch from.
     * @return A parsed JSON element from online data.
     * @author MatthewTGM
     */
    public static JsonElement getParsedJsonOnline(URI uri) {
        return JsonParser.parse(getJsonOnline(uri));
    }

    /**
     * @param username The username of the player to fetch
     * @return The UUID of the player.
     * @author Unknown
     */
    public static String fetchUuid(String username) {
        JsonElement response = getParsedJsonOnline("https://api.mojang.com/users/profiles/minecraft/" + username);
        if (response == null)
            return null;
        return response.getAsJsonObject().get("id").getAsString();
    }

}