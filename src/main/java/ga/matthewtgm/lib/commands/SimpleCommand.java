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

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class SimpleCommand extends CommandBase {

    private final SimpleCommandEntry runnable;

    public SimpleCommand(SimpleCommandEntry runnable) {
        this.runnable = runnable;
    }

    @Override
    public String getCommandName() {
        return runnable.name();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return runnable.permissionLevel();
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return runnable.usage();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        runnable.process((EntityPlayer) sender.getCommandSenderEntity(), args);
    }

    @Override
    public List<String> getCommandAliases() {
        return runnable.aliases();
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        ArrayList<String> options = new ArrayList<>();
        options.addAll(runnable.tabCompleteOptions());
        options.addAll(runnable.tabCompleteOptions((EntityPlayer) sender, args, pos));
        return options;
    }

}