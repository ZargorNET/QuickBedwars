package de.zargornet.qbw.commands.worldmanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.QbwLocation;
import de.zargornet.qbw.game.worlds.QbwSpawner;
import de.zargornet.qbw.game.worlds.QbwSpawnerType;
import de.zargornet.qbw.game.worlds.QbwWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

/**
 * Adds a spawner to a qbw map
 */
@QbwCommandPath(
        path = "addspawner",
        permission = "qbw.addspawner"
)
public class AddSpawner implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW addspawner <World name> <Type>");
            return;
        }
        if (!QbwCommandUtil.senderIsPlayer(sender)) {
            return;
        }
        if (Arrays.stream(QbwSpawnerType.values()).noneMatch(type -> type.name().equalsIgnoreCase(args[1]))) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cInvalid type! Valid types: ");
            Arrays.stream(QbwSpawnerType.values()).forEach(type -> sender.sendMessage("§e" + type.name()));
            return;
        }
        QbwWorld world = Qbw.getInstance().getDatabaseQueries().getWorld(args[0]);
        if (!QbwCommandUtil.worldAdded(sender, world)) {
            return;
        }
        if (!QbwCommandUtil.checkIfWorldIsNotUsed(sender, args[0])) {
            return;
        }
        List<QbwSpawner> spawnerList = world.getSpawnerList();
        int id = spawnerList.size() + 1;
        QbwLocation qbwLocation = new QbwLocation(((Player) sender).getLocation());
        qbwLocation.setYaw(0);
        qbwLocation.setPitch(0);
        spawnerList.add(new QbwSpawner(id, qbwLocation, QbwSpawnerType.valueOf(args[1].toUpperCase())));
        Qbw.getInstance().getDatabaseQueries().setWorld(world);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSpawner §e#" + id + " §aadded!");
    }
}
