package de.zargornet.qbw.commands.worldmanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.game.worlds.QbwWorld;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * Adds a world to qbw
 */
@QbwCommandPath(
        path = "addworld",
        permission = "qbw.addworld"
)
public class AddWorld implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW addworld <World name>");
            return;
        }
        if (Qbw.getInstance().getDatabaseQueries().getWorld(args[0]) != null) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cWorld already added!");
            return;
        }
        if (Bukkit.getWorld(args[0]) == null) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cWorld doesn't exist");
            return;
        }
        QbwWorld world = new QbwWorld(args[0].toLowerCase(), new ArrayList<>(), new ArrayList<>(), false);
        Qbw.getInstance().getDatabaseQueries().setWorld(world);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccess!");
    }
}