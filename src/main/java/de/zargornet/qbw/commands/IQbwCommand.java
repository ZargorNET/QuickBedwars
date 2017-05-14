package de.zargornet.qbw.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Interface for {@link QbwCommandPath}
 */
public interface IQbwCommand {
    void onCommand(CommandSender sender, Command cmd, String[] args);
}
