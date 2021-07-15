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
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 255;
    }

    public ColourRGB(Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public ColourRGB(int rgba) {
        Color color = new Color(rgba);
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        this.a = color.getAlpha();
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

    public String toJson() {
        return new JsonObject().add("red", r).add("green", g).add("blue", b).add("alpha", a).getAsString();
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