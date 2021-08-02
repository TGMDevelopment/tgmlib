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

import xyz.matthewtgm.requisite.commands.advanced.Command;
import xyz.matthewtgm.requisite.util.ArrayHelper;
import xyz.matthewtgm.requisite.util.ExceptionHelper;
import lombok.Getter;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.ClientCommandHandler;

import java.lang.reflect.Method;
import java.util.*;

public class CommandManager {

    @Getter private static final Map<Class<?>, ICommand> commandMap = new HashMap<>();

    public static void register(ICommand command, boolean prioritize) {
        if (prioritize && ClientCommandHandler.instance.getCommands().containsKey(command.getCommandName())) unregister(command.getCommandName());
        ClientCommandHandler.instance.registerCommand(command);
    }

    public static void register(ICommand command) {
        register(command, false);
    }

    public static void unregister(ICommand command) {
        unregister(command.getCommandName());
    }

    public static void unregister(String name) {
        ClientCommandHandler.instance.getCommands().remove(name);
    }

    public static void register(Class<?> clazz, boolean prioritize) {
        if (clazz.isAnnotationPresent(Command.class)) {
            ExceptionHelper.tryCatch(() -> {
                ICommand command = new AdvancedCommand(clazz.getAnnotation(Command.class), clazz.newInstance(), getProcessMethod(clazz), getArgumentMethods(clazz));
                register(command, prioritize);
                commandMap.put(clazz, command);
            });
        } else
            throw new IllegalStateException(clazz.getSimpleName() + " is not a command class!");
    }

    public static void register(Class<?> clazz) {
        register(clazz, false);
    }

    public static void unregister(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Command.class)) {
            unregister(clazz.getAnnotation(Command.class).name());
            commandMap.remove(clazz);
        } else
            throw new IllegalStateException(clazz.getSimpleName() + " is not a command class!");
    }

    private static Method getProcessMethod(Class<?> clazz) {
        Method ret = null;
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAccessible())
                method.setAccessible(true);
            if (method.isAnnotationPresent(Command.Process.class))
                ret = method;
        }
        return ret;
    }

    private static ArgumentMethod[] getArgumentMethods(Class<?> clazz) {
        List<ArgumentMethod> methodList = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAccessible())
                method.setAccessible(true);
            if (method.isAnnotationPresent(Command.Argument.class))
                methodList.add(new ArgumentMethod(method, method.getAnnotation(Command.Argument.class)));
        }
        return methodList.toArray(new ArgumentMethod[0]);
    }

    private static class AdvancedCommand implements ICommand {

        private final Command command;
        private final Object instance;
        private final Method process;
        private final ArgumentMethod[] arguments;

        private final boolean passSenderToProcess;
        private final boolean passArgsToProcess;

        private AdvancedCommand(Command command, Object instance, Method process, ArgumentMethod[] arguments) {
            this.command = command;
            this.instance = instance;
            this.process = process;
            this.arguments = arguments;

            this.passSenderToProcess = process != null && ArrayHelper.contains(process.getParameterTypes(), EntityPlayer.class);
            this.passArgsToProcess = process != null && ArrayHelper.contains(process.getParameterTypes(), String[].class);
        }

        public String getCommandName() {
            return command.name();
        }

        public String getCommandUsage(ICommandSender sender) {
            return command.usage();
        }

        public List<String> getCommandAliases() {
            return new ArrayList<>(ArrayHelper.convert(command.aliases()));
        }

        public void processCommand(ICommandSender sender, String[] args) {
            try {
                EntityPlayer playerSender = (EntityPlayer) sender;
                if (command.processIfNoArgs()) {
                    if (args.length <= 0)
                        execute(process, playerSender, args, passSenderToProcess, passArgsToProcess);
                } else
                    execute(process, playerSender, args, passSenderToProcess, passArgsToProcess);
                if (!(args.length <= 0)) {
                    for (ArgumentMethod argument : arguments) {
                        String arg = args[argument.argument.index()];
                        if (arg != null && (arg.equalsIgnoreCase(argument.argument.name()) || Arrays.stream(argument.argument.aliases()).anyMatch(alias -> alias.equalsIgnoreCase(arg))))
                            execute(argument.method, playerSender, args, ArrayHelper.contains(argument.method.getParameterTypes(), EntityPlayer.class), ArrayHelper.contains(argument.method.getParameterTypes(), String[].class));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void execute(Method method, EntityPlayer playerSender, String[] args, boolean passSender, boolean passArgs) throws Exception {
            if (method == null) return;
            if (!passSender && !passArgs)
                method.invoke(instance);
            if (passSender && !passArgs)
                method.invoke(instance, playerSender);
            if (!passSender && passArgs)
                method.invoke(instance, (Object) args);
            if (passSender && passArgs)
                method.invoke(instance, playerSender, args);
        }

        public boolean canCommandSenderUseCommand(ICommandSender sender) {
            return true;
        }

        public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
            List<String> ret = new ArrayList<>();
            if (args.length == 1) {
                if (command.autoGenTabCompleteOptions()) {
                    for (ArgumentMethod argument : arguments) {
                        ret.add(argument.argument.name());
                        ret.addAll(Arrays.asList(argument.argument.aliases()));
                    }
                } else {
                    for (String option : command.tabCompleteOptions()) {
                        if (option.startsWith(args[args.length - 1])) {
                            ret.add(option);
                        }
                    }
                }
            } else {
                for (ArgumentMethod argMethod : arguments) {
                    if (argMethod.argument.name().equalsIgnoreCase(args[0])) {
                        for (String argOption : argMethod.argument.tabCompleteOptions()) {
                            if (argOption.startsWith(args[args.length - 1])) {
                                ret.add(argOption);
                            }
                        }
                    }
                }
            }
            Collections.sort(ret);
            return new ArrayList<>(ret);
        }

        public boolean isUsernameIndex(String[] args, int index) {
            return false;
        }

        public int compareTo(ICommand command) {
            return getCommandName().compareTo(command.getCommandName());
        }

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