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

package xyz.matthewtgm.tgmlib.commands;

import xyz.matthewtgm.tgmlib.commands.advanced.Command;
import xyz.matthewtgm.tgmlib.util.ArrayHelper;
import xyz.matthewtgm.tgmlib.util.ExceptionHelper;
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
                if (!command.autoGenTabOptions()) {
                    for (String option : command.tabCompleteOptions()) {
                        if (option.startsWith(args[args.length - 1])) {
                            ret.add(option);
                        }
                    }
                } else {
                    for (ArgumentMethod argument : arguments) {
                        if (argument.argument.index() == 0) {
                            ret.add(argument.argument.name());
                            Collections.addAll(ret, argument.argument.aliases());
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

    /*

    register(theCommand = new CommandBase() {

                    public String getCommandName() {
                        return command.name();
                    }

                    public List<String> getCommandAliases() {
                        return Arrays.asList(command.aliases());
                    }

                    public String getCommandUsage(ICommandSender sender) {
                        return command.usage();
                    }

                    public int getRequiredPermissionLevel() {
                        return command.permissionLevel();
                    }

                    public void processCommand(ICommandSender sender, String[] args) {
                        ExceptionHelper.tryCatch(() -> {
                            EntityPlayer trueSender = (EntityPlayer) sender;
                            execute(processMethod, instance, trueSender, args, processMethodHasSenderParam, processMethodHasArgsParam);
                            if (!(args.length <= 0)) {
                                for (ArgumentMethod method : argumentMethods) {
                                    String arg = args[method.argument.index()];
                                    if (arg != null && arg.equalsIgnoreCase(method.argument.name()) || Arrays.stream(method.argument.aliases()).anyMatch(alias -> alias.equalsIgnoreCase(arg))) {
                                        boolean methodHasSenderParam = ArrayHelper.contains(method.getMethod().getParameterTypes(), EntityPlayer.class);
                                        boolean methodHasArgsParam = ArrayHelper.contains(method.getMethod().getParameterTypes(), String[].class);
                                        execute(method.getMethod(), instance, trueSender, args, methodHasSenderParam, methodHasArgsParam);
                                    }
                                }
                            }
                        });
                    }

                    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
                        List<String> ret = new ArrayList<>();
                        if (args.length == 1) {
                            for (String option : command.tabCompleteOptions()) {
                                if (option.startsWith(args[args.length - 1])) {
                                    ret.add(option);
                                }
                            }
                        } else {
                            for (ArgumentMethod argMethod : argumentMethods) {
                                if (argMethod.argument.name().equalsIgnoreCase(args[0])) {
                                    for (String argOption : argMethod.argument.tabCompleteOptions()) {
                                        if (argOption.startsWith(args[args.length - 1])) {
                                            ret.add(argOption);
                                        }
                                    }
                                }
                            }
                        }
                        return new ArrayList<>(ret);
                    }

                    private void execute(Method method, Object instance, EntityPlayer sender, String[] args, boolean hasSender, boolean hasArgs) throws Exception {
                        if (!hasSender && !hasArgs)
                            method.invoke(instance);
                        if (hasSender && !hasArgs)
                            method.invoke(instance, sender);
                        if (!hasSender && hasArgs)
                            method.invoke(instance, (Object) args);
                        if (hasSender && hasArgs)
                            method.invoke(instance, sender, args);
                    }
                }, prioritize);
                commandMap.put(clazz, theCommand);
            });

     */

}