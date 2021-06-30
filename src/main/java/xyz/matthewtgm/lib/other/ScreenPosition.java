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

import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.json.parser.JsonParser;
import xyz.matthewtgm.lib.util.global.GlobalMinecraft;

public class ScreenPosition {

    private int x, y;

    public ScreenPosition(int x, int y) {
        setPosition(x, y);
    }

    public ScreenPosition(JsonObject jsonObject) {
        this(jsonObject.get("x").getAsInt(), jsonObject.get("y").getAsInt());
    }

    public ScreenPosition(String jsonString) {
        this((JsonObject) JsonParser.parse(jsonString));
    }

    public ScreenPosition clone() {
        return new ScreenPosition(x, y);
    }

    public int getX() {
        return calculateX(x);
    }

    public ScreenPosition setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return calculateY(y);
    }

    public ScreenPosition setY(int y) {
        this.y = y;
        return this;
    }

    public ScreenPosition addX(int amount) {
        x += amount;
        return this;
    }

    public ScreenPosition addY(int amount) {
        y += amount;
        return this;
    }

    public ScreenPosition setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    private int calculateX(int x) {
        int width = GlobalMinecraft.getDisplayWidth();
        int ret = -(width - x);
        if (ret < 0) ret = Math.max(x, 0);
        if (ret > width) ret = width;
        return ret;
    }

    private int calculateY(int y) {
        int height = GlobalMinecraft.getDisplayHeight();
        int ret = -(height - y);
        if (ret < 0) ret = Math.max(y, 0);
        if (ret > height) ret = height;
        return ret;
    }

}