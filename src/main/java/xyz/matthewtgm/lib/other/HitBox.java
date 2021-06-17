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

import lombok.Getter;
import lombok.Setter;
import xyz.matthewtgm.json.objects.JsonObject;

public class HitBox {

    @Getter @Setter
    private float x, y, width, height;

    public HitBox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public HitBox(JsonObject<String, Number> json) {
        this(json.getAsFloat("x"), json.getAsFloat("y"), json.getAsFloat("width"), json.getAsFloat("height"));
    }

    public HitBox clone() {
        return new HitBox(x, y, width, height);
    }

    public boolean isInBounds(float x, float y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }

}