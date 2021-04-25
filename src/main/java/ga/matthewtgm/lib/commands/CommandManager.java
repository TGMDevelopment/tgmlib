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

package ga.matthewtgm.lib.commands;

import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;

import java.lang.reflect.Field;

public class CommandManager {

    public static void register(ICommand command) {
        ClientCommandHandler.instance.registerCommand(command);
    }

    public static void unregister(ICommand command) {
        ClientCommandHandler.instance.getCommands().remove(command.getCommandName());
    }

    public static void unregister(String name) {
        ClientCommandHandler.instance.getCommands().remove(name);
    }

}