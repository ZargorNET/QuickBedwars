package de.zargornet.qbw.commands.arena;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * List all arenas
 */
@QbwCommandPath(
        path = "listarenas",
        permission = "qbw.listarenas"
)
public class ListArenas implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (Qbw.getInstance().getArenas().isEmpty()) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cNo arenas found!");
            return;
        }
        final String[] s = {Qbw.getInstance().getPrefix() + "§aArenas: \n"};
        Qbw.getInstance().getArenas().forEach(arena -> {
            s[0] += "§e " + arena.getName() + "§7: \n";
            s[0] += "     §7State: §e" + arena.getState().name() + " \n";
            s[0] += "     §7Max players per team: §e" + arena.getPlayersPerTeam() + " \n";
        });
        sender.sendMessage(s[0]);
    }
}
