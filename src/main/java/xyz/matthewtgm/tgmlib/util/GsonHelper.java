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

package xyz.matthewtgm.tgmlib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {

    private static Gson gson = new GsonBuilder().create();
    private static Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

    public static Gson getGson() {
        if (gson == null)
            gson = new GsonBuilder().create();
        return gson;
    }

    public static Gson getGsonPretty() {
        if (gsonPretty == null)
            gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        return gsonPretty;
    }

}