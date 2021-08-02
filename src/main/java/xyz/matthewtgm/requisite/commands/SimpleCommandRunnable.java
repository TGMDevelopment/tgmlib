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

package xyz.matthewtgm.requisite.commands;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

import java.util.Collections;
import java.util.List;

public interface SimpleCommandRunnable {

    String name();
    String usage();
    int permissionLevel();
    void process(EntityPlayer sender, String[] args) throws CommandException;
    default List<String> tabCompleteOptions(EntityPlayer sender, String[] args, BlockPos pos) {
        return Collections.emptyList();
    }
    default List<String> aliases() {
        return Collections.emptyList();
    }

}