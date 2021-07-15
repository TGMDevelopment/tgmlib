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
import xyz.matthewtgm.json.parser.JsonParser;

import java.awt.*;

public class ColourRGB {

    private int r, g, b, a;

    public ColourRGB(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public ColourRGB(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public ColourRGB(JsonObject object) {
        this(new Color(fromJson(object).getRGBA()));
    }

    public ColourRGB(String input) {
        this(JsonParser.parse(input).getAsJsonObject());
    }

    public ColourRGB(Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public ColourRGB(int rgba) {
        this(new Color(rgba));
    }

    public ColourRGB clone() {
        return new ColourRGB(r, g, b, a);
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getRGB() {
        return toJavaColor().getRGB();
    }

    public int getRGBA() {
        return toJavaColor().getRGB();
    }

    public Color toJavaColor() {
        return new Color(r, g, b, a);
    }

    public String toJson() {
        return new JsonObject().add("red", r).add("green", g).add("blue", b).add("alpha", a).getAsString();
    }

    public static ColourRGB fromJson(JsonObject object) {
        int r = 255;
        int g = 255;
        int b = 255;
        int a = 255;
        r = getIntOrDefault("r", object, r);
        r = getIntOrDefault("red", object, r);
        g = getIntOrDefault("g", object, g);
        g = getIntOrDefault("green", object, g);
        b = getIntOrDefault("b", object, b);
        b = getIntOrDefault("blue", object, b);
        a = getIntOrDefault("a", object, a);
        a = getIntOrDefault("alpha", object, a);
        return new ColourRGB(r, g, b, a);
    }

    private static int getIntOrDefault(String key, JsonObject object, int defaultInt) {
        return object.hasKey(key) ? object.get(key).getAsInt() : defaultInt;
    }

    @Override
    public String toString() {
        return "ColourRGB{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", a=" + a +
                '}';
    }
}