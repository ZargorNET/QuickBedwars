package de.zargornet.qbw.commands.arenamanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.QbwLocation;
import de.zargornet.qbw.game.arena.QbwArena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Set an arena's lobby spawnpoint
 */
@QbwCommandPath(
        path = "setlobbyspawn",
        permission = "qbw.setlobbyspawn"
)
public class SetLobbySpawn implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW setlobbyspawn <Arena name>");
            return;
        }
        if (!QbwCommandUtil.senderIsPlayer(sender)) {
            return;
        }
        QbwArena arena = QbwCommandUtil.getArenaIfNotNull(sender, args[0]);
        if (arena == null) {
            return;
        }
        if (!QbwCommandUtil.isArenaStopped(sender, arena)) {
            return;
        }
        arena.setLobby(new QbwLocation(((Player) sender).getLocation()));
        Qbw.getInstance().getDatabaseQueries().setArena(arena);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccessfully set the spawn on your location!");
    }
}
