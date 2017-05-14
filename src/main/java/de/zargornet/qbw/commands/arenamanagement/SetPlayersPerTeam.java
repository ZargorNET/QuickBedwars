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
 * Set the max players per team
 */
@QbwCommandPath(
        path = "setplayersperteam",
        permission = "qbw.setplayersperteam"
)
public class SetPlayersPerTeam implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW setplayersperteam <Arena name> <Count>");
            return;
        }
        if (Ints.tryParse(args[1]) == null) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cSecond argument isn't a number or it is too big!");
            return;
        }
        if (Integer.valueOf(args[1]) <= 0) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cThe number must be over 0");
            return;
        }
        QbwArena arena = QbwCommandUtil.getArenaIfNotNull(sender, args[0]);
        if (arena == null) {
            return;
        }
        if (!QbwCommandUtil.isArenaStopped(sender, arena)) {
            return;
        }
        arena.setPlayersPerTeam(Integer.valueOf(args[1]));
        Qbw.getInstance().getDatabaseQueries().setArena(arena);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccessfully set to §e" + args[1]);
    }
}
