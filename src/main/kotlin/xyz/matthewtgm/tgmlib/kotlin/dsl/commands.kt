package xyz.matthewtgm.tgmlib.kotlin.dsl

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos
import xyz.matthewtgm.tgmlib.commands.CommandManager

inline fun command(): CommandBuilder = CommandBuilder()

class CommandBuilder {
    private var name: String = ""
    private var aliases: MutableList<String> = mutableListOf()
    private var tabCompletions: MutableList<String> = mutableListOf()
    private var execute: (sender: ICommandSender, args: Array<out String>) -> Unit = { iCommandSender: ICommandSender, strings: Array<out String> ->

    }
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