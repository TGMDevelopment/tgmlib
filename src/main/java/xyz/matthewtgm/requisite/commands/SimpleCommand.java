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
import xyz.matthewtgm.requisite.function.QuadConsumer;
import xyz.matthewtgm.requisite.function.TriConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class SimpleCommand extends CommandBase {

    private final String name;
    private final List<String> aliases;
    private final QuadConsumer<List<String>, EntityPlayer, String[], BlockPos> tabCompletions;
    private final BiConsumer<EntityPlayer, String[]> execute;

    public SimpleCommand(String name, List<String> aliases, QuadConsumer<List<String>, EntityPlayer, String[], BlockPos> tabCompletions, BiConsumer<EntityPlayer, String[]> execute) {
        this.name = name;
        this.aliases = aliases;
        this.tabCompletions = tabCompletions;
        this.execute = execute;
    }

    public SimpleCommand(String name, List<String> aliases, BiConsumer<EntityPlayer, String[]> execute) {
        this(name, aliases, (ar, e, a, p) -> {}, execute);
    }

    public SimpleCommand(String name, QuadConsumer<List<String>, EntityPlayer, String[], BlockPos> tabCompletions, BiConsumer<EntityPlayer, String[]> execute) {
        this(name, new ArrayList<>(), tabCompletions, execute);
    }

    public SimpleCommand(String name, BiConsumer<EntityPlayer, String[]> execute) {
        this(name, new ArrayList<>(), (ar, e, a, p) -> {}, execute);
    }

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        execute.accept((EntityPlayer) sender, args);
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> options = new ArrayList<>();
        tabCompletions.accept(options, (EntityPlayer) sender, args, pos);
        return new ArrayList<>(options);
    }

}