package de.zargornet.qbw.commands.arena;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.arena.QbwArenaState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Enables an arena again
 */
@QbwCommandPath(
        path = "enablearena",
        permission = "qbw.enablearena"
)
public class EnableArena implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW enablearena <Name>");
            return;
        }
        QbwArena arena = QbwCommandUtil.getArenaIfNotNull(sender, args[0]);
        if (arena == null)
            return;
        if (arena.getState() != QbwArenaState.STOPPED) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cArena isn't stopped!");
            return;
        }
        if (arena.getLobby() == null) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cLobby isn't defined!");
            return;
        }
        if (arena.getPlayersPerTeam() <= 0) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlayersPerTeam is 0");
            return;
        }
        if (arena.getTeamCount() <= 0) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cTeamCount is 0");
            return;
        }
        arena.setState(QbwArenaState.LOBBY);
        Qbw.getInstance().getDatabaseQueries().setArena(arena);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccessfully enabled arena §e" + args[0]);
    }
}
