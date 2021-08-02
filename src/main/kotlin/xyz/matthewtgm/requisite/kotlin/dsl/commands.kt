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

package xyz.matthewtgm.requisite.kotlin.dsl

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos
import xyz.matthewtgm.requisite.commands.CommandManager

inline fun command(): CommandBuilder = CommandBuilder()

class CommandBuilder {
    private var name: String = ""
    private var aliases: MutableList<String> = mutableListOf()
    private var tabCompletions: MutableList<String> = mutableListOf()
    private var execute: (sender: ICommandSender, args: Array<out String>) -> Unit = { _, _ -> }
    fun name(name: String): CommandBuilder {
        this.name = name
        return this
    }
    fun aliases(aliases: MutableList<String>): CommandBuilder {
        this.aliases.addAll(aliases)
        return this
    }
    fun tabCompletions(tabCompletions: MutableList<String>): CommandBuilder {
        this.tabCompletions.addAll(tabCompletions)
        return this
    }
    fun execute(execute: (sender: ICommandSender, args: Array<out String>) -> Unit) {
        this.execute = execute
        build()
    }
    private fun build() {
        val generated = object : CommandBase() {
            override fun getCommandName(): String = name
            override fun getCommandAliases(): MutableList<String> = aliases
            override fun getCommandUsage(sender: ICommandSender?): String = ""
            override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean = true
            override fun processCommand(sender: ICommandSender, args: Array<out String>): Unit = execute.invoke(sender, args)
            override fun addTabCompletionOptions(
                sender: ICommandSender?,
                args: Array<out String>?,
                pos: BlockPos?
            ): MutableList<String> = tabCompletions
        }
        CommandManager.register(generated)
    }
}