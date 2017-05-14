package de.zargornet.qbw.commands.worldmanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.worlds.QbwWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * List all qbw teams from a world
 */
@QbwCommandPath(
        path = "listteams",
        permission = "qbw.listteams"
)
public class ListTeams implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW listteams <World name>");
            return;
        }
        QbwWorld world = Qbw.getInstance().getDatabaseQueries().getWorld(args[0]);
        if (!QbwCommandUtil.worldAdded(sender, world)) {
            return;
        }
        if (!QbwCommandUtil.checkIfWorldIsNotUsed(sender, args[0])) {
            return;
        }
        final String[] s = {""};
        world.getTeams().forEach(team -> s[0] += team.getColor().name() + " \n");
        if (s[0].equals("")) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cNo team was added!");
            return;
        }
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aAdded teams: \n" + s[0]);
    }
}
