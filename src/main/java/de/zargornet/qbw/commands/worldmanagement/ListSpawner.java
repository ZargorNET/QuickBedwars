package de.zargornet.qbw.commands.worldmanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.worlds.QbwSpawner;
import de.zargornet.qbw.game.worlds.QbwWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Comparator;

/**
 * List all spawners in a qbw world
 */
@QbwCommandPath(
        path = "listspawner",
        permission = "qbw.listspawner"
)
public class ListSpawner implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW listspawner <World name>");
            return;
        }
        QbwWorld world = Qbw.getInstance().getDatabaseQueries().getWorld(args[0]);
        if (!QbwCommandUtil.worldAdded(sender, world)) {
            return;
        }
        if (!QbwCommandUtil.checkIfWorldIsNotUsed(sender, args[0])) {
            return;
        }
        if (!QbwCommandUtil.worldDisabled(sender, world))
            return;
        final String[] s = {""};
        world.getSpawnerList().stream().sorted(Comparator.comparingInt(QbwSpawner::getId)).forEach(qbwSpawner -> {
            double x = qbwSpawner.getLocation().getX();
            double y = qbwSpawner.getLocation().getY();
            double z = qbwSpawner.getLocation().getZ();
            s[0] += "§e#" + qbwSpawner.getId() + "§7;" + "§eType: " + qbwSpawner.getType().name() + "§7;§eLocation: " + (int) x + "§7,§e" + (int) y + "§7,§e" + (int) z + " \n";
        });
        if (s[0].equals("")) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cNo spawner found");
            return;
        }
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSpawner in §e" + args[0].toLowerCase());
        sender.sendMessage(s[0]);
    }
}
