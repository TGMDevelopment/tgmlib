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

package xyz.matthewtgm.lib.util;

import com.google.common.base.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public class EntityHelper {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static int getLoadedEntityCount() {
        if (mc.theWorld == null) return 0;
        if (mc.theWorld.loadedEntityList == null) return 0;
        return mc.theWorld.loadedEntityList.size();
    }

    public static int getEntityCount(Class<? extends Entity> entityType) {
        return getEntityCount(entityType, entity -> true);
    }

    public static <T extends Entity> int getEntityCount(Class<? extends T> entityType, Predicate<? super T> filter) {
        if (mc.theWorld == null) return 0;
        return mc.theWorld.getEntities(entityType, filter).size();
    }

    public static <R extends Render<Entity>, E extends Entity> R getEntityRendererFromClass(Class<R> renderClazz, Class<E> entityClazz) {
        return renderClazz.cast(Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(entityClazz));
    }

}