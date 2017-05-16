package de.zargornet.qbw.commands.worldmanagement;

import com.google.common.primitives.Ints;
import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.worlds.QbwSpawner;
import de.zargornet.qbw.game.worlds.QbwWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Removes a spawner
 */
@QbwCommandPath(
        path = "remspawner",
        permission = "qbw.remspawner"
)
public class RemSpawner implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW remspawner <World name> <ID>");
            return;
        }
        if (Ints.tryParse(args[1]) == null) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§e" + args[1] + " §cisn't a valid number!");
            return;
        }
        if (!QbwCommandUtil.senderIsPlayer(sender)) {
            return;
        }
        if (!QbwCommandUtil.checkIfWorldIsNotUsed(sender, args[0])) {
            return;
        }
        QbwWorld world = Qbw.getInstance().getDatabaseQueries().getWorld(args[0]);
        if (!QbwCommandUtil.worldAdded(sender, world)) {
            return;
        }
        List<QbwSpawner> qbwSpawnerList = world.getSpawnerList();
        if (qbwSpawnerList.stream().noneMatch(qbwSpawner -> qbwSpawner.getId() == Integer.valueOf(args[1]))) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cSpawner[§e#" + Integer.valueOf(args[1]) + "§c] wasn't found");
            return;
        }
        if (!QbwCommandUtil.worldDisabled(sender, world))
            return;
        qbwSpawnerList.remove(qbwSpawnerList.stream().filter(qbwSpawner -> qbwSpawner.getId() == Integer.valueOf(args[1])).findFirst().get());
        Qbw.getInstance().getDatabaseQueries().setWorld(world);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSpawner §e#" + Integer.valueOf(args[1]) + " §adeleted!");
    }
}
