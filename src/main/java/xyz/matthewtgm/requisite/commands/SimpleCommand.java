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

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class SimpleCommand extends CommandBase {

    private final SimpleCommandRunnable runnable;

    public SimpleCommand(SimpleCommandRunnable runnable) {
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
        return new ArrayList<>(runnable.tabCompleteOptions((EntityPlayer) sender, args, pos));
    }

}