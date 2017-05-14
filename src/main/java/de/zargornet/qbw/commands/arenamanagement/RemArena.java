package de.zargornet.qbw.commands.arenamanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.arena.QbwArena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Removes an arena
 */
@QbwCommandPath(
        path = "remarena",
        permission = "qbw.remarena"
)
public class RemArena implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW remarena <Name>");
            return;
        }
        QbwArena arena = QbwCommandUtil.getArenaIfNotNull(sender, args[0]);
        if (arena == null) {
            return;
        }
        if (!QbwCommandUtil.isArenaStopped(sender, arena)) {
            return;
        }
        Qbw.getInstance().getDatabaseQueries().remArena(args[0]);
        Qbw.getInstance().getArenas().remove(arena);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccess!");
    }
}
