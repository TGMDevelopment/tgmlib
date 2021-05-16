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

package ga.matthewtgm.lib.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class DropItemEvent extends Event {
    public final boolean dropAll;
    public DropItemEvent(boolean dropAll) {
        this.dropAll = dropAll;
    }
    @Cancelable
    public static class Pre extends DropItemEvent {
        public final ItemStack item;
        public Pre(ItemStack item, boolean dropAll) {
            super(dropAll);
            this.item = item;
        }
    }
    public static class Post extends DropItemEvent {
        public Post(boolean dropAll) {
            super(dropAll);
        }
    }
}