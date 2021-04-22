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