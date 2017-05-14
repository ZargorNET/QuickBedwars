package de.zargornet.qbw.commands.arenamanagement;

import com.google.common.primitives.Ints;
import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.arena.QbwArena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Sets the team count
 */
@QbwCommandPath(
        path = "setteamcount",
        permission = "qbw.setteamcount"
)
public class SetTeamCount implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW setteamcount <Arena name> <Count>");
            return;
        }
        if (Ints.tryParse(args[1]) == null) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cSecond argument isn't a number or it is too big!");
            return;
        }
        if (Integer.valueOf(args[1]) <= 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cThe number must be over 1");
            return;
        }
        QbwArena arena = QbwCommandUtil.getArenaIfNotNull(sender, args[0]);
        if (arena == null) {
            return;
        }
        if (!QbwCommandUtil.isArenaStopped(sender, arena)) {
            return;
        }
        arena.setTeamCount(Integer.valueOf(args[1]));
        Qbw.getInstance().getDatabaseQueries().setArena(arena);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccessfully set to §e" + args[1]);
    }
}
