package de.zargornet.qbw.commands.arenamanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.game.arena.QbwArena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Add a arena
 */
@QbwCommandPath(
        path = "addarena",
        permission = "qbw.addarena"
)
public class AddArena implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW addarena <Unique name>");
            return;
        }
        if (Qbw.getInstance().getArenas().stream().anyMatch(arena1 -> arena1.getName().toLowerCase().equals(args[0].toLowerCase()))) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cArena does already exist!");
            return;
        }
        QbwArena arena1 = new QbwArena(args[0], null, 0, 0);
        Qbw.getInstance().getDatabaseQueries().setArena(arena1);
        Qbw.getInstance().getArenas().add(arena1);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccess! Don't forget to configure the other things!");
    }
}
