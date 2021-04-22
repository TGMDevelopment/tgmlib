package ga.matthewtgm.lib.commands;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collections;
import java.util.List;

public abstract class SimpleCommandEntry {

    public abstract String name();
    public abstract String usage();
    public abstract int permissionLevel();
    public abstract void process(EntityPlayer sender, String[] args) throws CommandException;
    public abstract List<String> tabCompleteOptions();
    public List<String> aliases() {
        return Collections.emptyList();
    }

}