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

package xyz.matthewtgm.requisite.util;

import com.google.common.base.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public final class EntityHelper {

    private EntityHelper() {}

    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * @return The current loaded entity count.
     * @author MatthewTGM
     */
    public static int getLoadedEntityCount() {
        if (mc.theWorld == null)
            return 0;
        if (mc.theWorld.loadedEntityList == null)
            return 0;
        return mc.theWorld.loadedEntityList.size();
    }

    /**
     * @param entityType The type of entity.
     * @return How many entities are currently in the world of this type.
     * @author MatthewTGM
     */
    public static int getEntityCount(Class<? extends Entity> entityType) {
        return getEntityCount(entityType, entity -> true);
    }

    /**
     * @param entityType The type of entity.
     * @param filter The entity filter.
     * @param <T> The type of entity.
     * @return How many entities are in the world after all filters are processed.
     * @author MatthewTGM
     */
    public static <T extends Entity> int getEntityCount(Class<? extends T> entityType, Predicate<? super T> filter) {
        return getEntities(entityType, filter).size();
    }

    public static <T extends Entity> List<T> getEntities(Class<? extends T> entityType, Predicate<? super T> filter) {
        if (mc.theWorld == null)
            return new ArrayList<>();
        return mc.theWorld.getEntities(entityType, filter);
    }

    /**
     * @param renderClazz The entity's render class.
     * @param entityClazz The entity's class.
     * @param <R> The render type.
     * @param <E> The entity type.
     * @return The render class' instance.
     * @author MatthewTGM
     */
    public static <R extends Render<Entity>, E extends Entity> R getEntityRendererFromClass(Class<R> renderClazz, Class<E> entityClazz) {
        return renderClazz.cast(Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(entityClazz));
    }

}