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

import java.util.List;

/**
 * Removes a team from map
 */
@QbwCommandPath(
        path = "remteam",
        permission = "qbw.remteam"
)
public class RemTeam implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW remteam <World name> <Team color>");
            return;
        }
        QbwWorld world = Qbw.getInstance().getDatabaseQueries().getWorld(args[0]);
        if (!QbwCommandUtil.worldAdded(sender, world)) {
            return;
        }
        if (QbwCommandUtil.getTeamColorIfValid(sender, args[1]) == null) {
            return;
        }
        if (!QbwCommandUtil.isTeamAdded(sender, world, args[1])) {
            return;
        }
        if (!QbwCommandUtil.checkIfWorldIsNotUsed(sender, args[0])) {
            return;
        }
        if (!QbwCommandUtil.worldDisabled(sender, world))
            return;
        List<QbwTeam> teamList = world.getTeams();
        teamList.remove(teamList.stream().filter(team -> team.getColor() == TeamColor.valueOf(args[1].toUpperCase())).findFirst().get());
        Qbw.getInstance().getDatabaseQueries().setWorld(world);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccess!");
    }
}
