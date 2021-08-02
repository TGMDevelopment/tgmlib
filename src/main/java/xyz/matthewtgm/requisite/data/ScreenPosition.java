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

package xyz.matthewtgm.requisite.data;

import xyz.matthewtgm.json.entities.JsonObject;
import xyz.matthewtgm.json.parser.JsonParser;
import xyz.matthewtgm.requisite.util.DevelopmentHelper;
import xyz.matthewtgm.requisite.util.MathHelper;
import xyz.matthewtgm.requisite.util.ScreenHelper;
import java.lang.*;

public class ScreenPosition {

    private int x, y, rawX, rawY;

    public ScreenPosition(int x, int y) {
        setPosition(x, y);
        DevelopmentHelper.markUnderHeavyDevelopment(this);
    }

    public ScreenPosition(JsonObject jsonObject) {
        this(jsonObject.get("x").getAsInt(), jsonObject.get("y").getAsInt());
    }

    public ScreenPosition(String jsonString) {
        this(JsonParser.parse(jsonString).getAsJsonObject());
    }

    public ScreenPosition clone() {
        return new ScreenPosition(x, y);
    }

    public int getX() {
        System.out.println("X: " + x);
        return x;
    }

    public ScreenPosition setX(int x) {
        this.x = calculateX(x);
        return this;
    }

    public int getY() {
        System.out.println("Y: " + y);
        return y;
    }

    public int getRawX() {
        return rawX;
    }

    public int getRawY() {
        return rawY;
    }

    public ScreenPosition setY(int y) {
        this.y = calculateY(y);
        return this;
    }

    public ScreenPosition setPosition(int x, int y) {
        this.x = calculateX(x);
        this.y = calculateY(y);
        this.rawX = x;
        this.rawY = y;
        return this;
    }

    private int calculateX(int x) {
        return MathHelper.percentageOf_int(x, 0, ScreenHelper.getScaledWidth());
    }

    private int calculateY(int y) {
        return MathHelper.percentageOf_int(y, 0, ScreenHelper.getScaledHeight());
    }

}