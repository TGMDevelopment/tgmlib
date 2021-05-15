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

import ga.matthewtgm.lib.commands.bettercommands.Command;
import ga.matthewtgm.lib.util.ExceptionHelper;
import lombok.Getter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.ClientCommandHandler;

import java.lang.reflect.Method;
import java.util.*;

public class CommandManager {

    @Getter private static final Map<Class<?>, CommandBase> commandMap = new HashMap<>();

    public static void register(ICommand command) {
        ClientCommandHandler.instance.registerCommand(command);
    }

    public static void unregister(ICommand command) {
        ClientCommandHandler.instance.getCommands().remove(command.getCommandName());
    }

    public static void unregister(String name) {
        ClientCommandHandler.instance.getCommands().remove(name);
    }

    public static void register(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Command.class)) {
            ExceptionHelper.tryCatch(() -> {
                Object instance = clazz.newInstance();
                Command command = clazz.getAnnotation(Command.class);
                CommandBase theCommand;
                register(theCommand = new CommandBase() {
                    @Override
                    public String getCommandName() {
                        return command.name();
                    }

                    @Override
                    public List<String> getCommandAliases() {
                        return Arrays.asList(command.aliases());
                    }

                    @Override
                    public String getCommandUsage(ICommandSender sender) {
                        return command.usage();
                    }

                    @Override
                    public int getRequiredPermissionLevel() {
                        return command.permissionLevel();
                    }

                    @Override
                    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
                        ExceptionHelper.tryCatch(() -> {
                            getProcessMethod(clazz).invoke(instance, sender, args);
                            if (!(args.length <= 0)) {
                                for (ArgumentMethod method : getArgumentMethods(clazz)) {
                                    String arg = args[method.argument.index()];
                                    if (arg != null && arg.equalsIgnoreCase(method.argument.name()) || Arrays.stream(method.argument.aliases()).anyMatch(alias -> alias.equalsIgnoreCase(arg)))
                                        method.method.invoke(arg, args);
                                }
                            }
                        });
                    }

                    @Override
                    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
                        return Arrays.asList(command.tabCompleteOptions());
                    }
                });
                commandMap.put(clazz, theCommand);
            });
        } else {
            throw new IllegalStateException("The class provided is not a command!");
        }
    }

    public static void unregister(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Command.class)) {
            unregister(clazz.getAnnotation(Command.class).name());
            commandMap.remove(clazz);
        } else {
            throw new IllegalStateException("The class provided is not a command!");
        }
    }

    private static Method getProcessMethod(Class<?> clazz) {
        Method ret = null;
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAccessible())
                method.setAccessible(true);
            if (method.isAnnotationPresent(Command.Process.class) && method.getParameterTypes() != null && !Arrays.asList(method.getParameterTypes()).isEmpty() && method.getParameterTypes()[0].isAssignableFrom(EntityPlayer.class) && method.getParameterTypes()[1].isAssignableFrom(String[].class))
                ret = method;
        }
        if (ret == null)
            throw new IllegalStateException(clazz.getSimpleName() + " either doesn't contain a process method or the parameters are sorted incorrectly.");
        return ret;
    }

    private static ArgumentMethod[] getArgumentMethods(Class<?> clazz) {
        List<ArgumentMethod> methodList = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAccessible())
                method.setAccessible(true);
            if (method.isAnnotationPresent(Command.Argument.class) && method.getParameterTypes() != null && !Arrays.asList(method.getParameterTypes()).isEmpty() && method.getParameterTypes()[0].isAssignableFrom(String[].class))
                methodList.add(new ArgumentMethod(method, method.getAnnotation(Command.Argument.class)));
        }
        return methodList.toArray(new ArgumentMethod[0]);
    }

    private static class ArgumentMethod  {

        @Getter private final Method method;
        @Getter private final Command.Argument argument;

        public ArgumentMethod(Method method, Command.Argument argument) {
            this.method = method;
            this.argument = argument;
        }

    }

}