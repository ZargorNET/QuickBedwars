package de.zargornet.qbw.commands.worldmanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.worlds.QbwTeam;
import de.zargornet.qbw.game.worlds.QbwWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Enable a world
 */
@QbwCommandPath(
        path = "enableworld",
        permission = "qbw.enableworld"
)
public class EnableWorld implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW enableworld <Name>");
            return;
        }
        QbwWorld world = Qbw.getInstance().getDatabaseQueries().getWorld(args[0]);
        if (!QbwCommandUtil.worldAdded(sender, world))
            return;
        if (!QbwCommandUtil.checkIfWorldIsNotUsed(sender, world.getName()))
            return;
        boolean success = false;

        for (QbwTeam team : world.getTeams()) {
            if (team.getShopLoc() != null) {
                success = true;
                break;
            }
            if (team.getTeamBedLoc() != null) {
                success = true;
                break;
            }
            if (team.getTeamBedLoc() != null) {
                success = true;
                break;
            }
        }

        if (!world.getSpawnerList().isEmpty())
            success = true;
        if (world.getTeams().size() >= 2)
            success = true;

        if (success) {
            world.setEnabled(true);
            Qbw.getInstance().getDatabaseQueries().setWorld(world);
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccess!");
            return;
        }

        sender.sendMessage(Qbw.getInstance().getPrefix() + "§cWorld couldn't be enabled! Check the configuration: §e/QBW checkworld " + world.getName());

    }
}
