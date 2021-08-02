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

package xyz.matthewtgm.requisite.tweaker.hooks;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.events.EntityRenderCheckEvent;

public class RenderHook {

    public static boolean callRenderCheckEvent(Entity entity) {
        return MinecraftForge.EVENT_BUS.post(new EntityRenderCheckEvent<>(entity));
    }

    public static void renderIndicators(Entity entity, String str, double x, double y, double z, int maxDistance) {
        Requisite.getManager().getIndicatorManager().render(entity, str, x, y, z, maxDistance);
    }

}