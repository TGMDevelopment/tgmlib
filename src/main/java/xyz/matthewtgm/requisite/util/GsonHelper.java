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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import lombok.Getter;

public final class GsonHelper {

    private static Gson gson = new GsonBuilder().create();
    private static Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
    @Getter private static JsonParser parser = new JsonParser();

    private GsonHelper() {}

    /**
     * @return A gson instance.
     * @author MatthewTGM
     */
    public static Gson getGson() {
        if (gson == null)
            gson = new GsonBuilder().create();
        return gson;
    }

    /**
     * @return A pretty gson instance.
     * @author MatthewTGM
     */
    public static Gson getGsonPretty() {
        if (gsonPretty == null)
            gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        return gsonPretty;
    }

}