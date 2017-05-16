package de.zargornet.qbw.commands.worldmanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.worlds.QbwTeam;
import de.zargornet.qbw.game.worlds.QbwWorld;
import de.zargornet.qbw.game.worlds.TeamColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Add a team to the map
 */
@QbwCommandPath(
        path = "addteam",
        permission = "qbw.addteam"
)
public class AddTeam implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW addteam <World name> <Team color>");
            return;
        }
        QbwWorld world = Qbw.getInstance().getDatabaseQueries().getWorld(args[0]);
        if (!QbwCommandUtil.worldAdded(sender, world)) {
            return;
        }
        if (QbwCommandUtil.getTeamColorIfValid(sender, args[1]) == null) {
            return;
        }
        if (!QbwCommandUtil.checkIfWorldIsNotUsed(sender, args[0])) {
            return;
        }
        if (TeamColor.valueOf(args[1].toUpperCase()) == TeamColor.UNKNOWN) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cYou cant add a team named 'Unknown'");
            return;
        }
        if (world.getTeams().stream().anyMatch(qbwTeam -> qbwTeam.getColor() == TeamColor.valueOf(args[1].toUpperCase()))) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cTeam already added!");
            return;
        }
        if (!QbwCommandUtil.worldDisabled(sender, world))
            return;
        QbwTeam team = new QbwTeam(TeamColor.valueOf(args[1].toUpperCase()), null, null, null);
        world.getTeams().add(team);
        Qbw.getInstance().getDatabaseQueries().setWorld(world);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccess!");
    }
}
