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
        return new ArrayList<>(runnable.tabCompleteOptions());
    }

}