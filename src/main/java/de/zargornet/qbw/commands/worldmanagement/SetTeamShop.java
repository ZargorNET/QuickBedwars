package de.zargornet.qbw.commands.worldmanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.QbwLocation;
import de.zargornet.qbw.game.worlds.QbwTeam;
import de.zargornet.qbw.game.worlds.QbwWorld;
import de.zargornet.qbw.game.worlds.TeamColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Sets a team's shop
 */
@QbwCommandPath(
        path = "setteamshop",
        permission = "qbw.setteamshop"
)
public class SetTeamShop implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW setteamshop <World name> <Team color>");
            return;
        }
        if (!QbwCommandUtil.senderIsPlayer(sender)) {
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
        QbwTeam team = teamList.stream().filter(team1 -> team1.getColor() == TeamColor.valueOf(args[1].toUpperCase())).findFirst().get();
        team.setShopLoc(new QbwLocation(((Player) sender).getLocation()));
        Qbw.getInstance().getDatabaseQueries().setWorld(world);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccess!");
    }
}
