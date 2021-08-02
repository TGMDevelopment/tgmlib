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

    public ColourRGB setR_builder(int ar) {
        setR(r);
        return this;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public ColourRGB setG_builder(int g) {
        setG(g);
        return this;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public ColourRGB setB_builder(int b) {
        setB(b);
        return this;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public ColourRGB setA_builder(int a) {
        setA(a);
        return this;
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

    public JsonObject toJson() {
        return new JsonObject().add("red", r).add("green", g).add("blue", b).add("alpha", a);
    }

    public static ColourRGB fromJson(JsonObject object) {
        int r = 255;
        int g = 255;
        int b = 255;
        int a = 255;
        r = object.getOrDefault("r", r).getAsInt();
        r = object.getOrDefault("red", r).getAsInt();
        g = object.getOrDefault("g", g).getAsInt();
        g = object.getOrDefault("green", g).getAsInt();
        b = object.getOrDefault("b", b).getAsInt();
        b = object.getOrDefault("blue", b).getAsInt();
        a = object.getOrDefault("a", a).getAsInt();
        a = object.getOrDefault("alpha", a).getAsInt();
        return new ColourRGB(r, g, b, a);
    }

    @Override
    public String toString() {
        return toJson().getAsString();
    }
}