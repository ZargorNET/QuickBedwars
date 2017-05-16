package de.zargornet.qbw.commands.worldmanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.QbwLocation;
import de.zargornet.qbw.game.worlds.QbwTeam;
import de.zargornet.qbw.game.worlds.QbwWorld;
import de.zargornet.qbw.game.worlds.TeamColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Set a team's bed
 */
@QbwCommandPath(
        path = "setteambed",
        permission = "qbw.setteambed"
)
public class SetTeamBed implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW setteambed <World name> <Team color>");
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
        Player p = (Player) sender;
        if (p.getTargetBlock((Set<Material>) null, 5).getType() != Material.BED_BLOCK) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cBlock where you're looking at isn't a bed");
            return;
        }
        if (!QbwCommandUtil.worldDisabled(sender, world))
            return;
        QbwTeam team = world.getTeams().stream().filter(team1 -> team1.getColor() == TeamColor.valueOf(args[1].toUpperCase())).findFirst().get();
        team.setTeamBedLoc(new QbwLocation(p.getTargetBlock((Set<Material>) null, 5).getLocation()));
        Qbw.getInstance().getDatabaseQueries().setWorld(world);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccess setted bed location on your looking location!");
    }
}