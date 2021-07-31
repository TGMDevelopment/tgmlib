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

package xyz.matthewtgm.tgmlib.tweaker.hooks;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.events.EntityRenderCheckEvent;

public class RenderHook {

    public static boolean callRenderCheckEvent(Entity entity) {
        return MinecraftForge.EVENT_BUS.post(new EntityRenderCheckEvent<>(entity));
    }

    public static void renderIndicators(Entity entity, String str, double x, double y, double z, int maxDistance) {
        TGMLib.getManager().getIndicatorManager().render(entity, str, x, y, z, maxDistance);
    }

}