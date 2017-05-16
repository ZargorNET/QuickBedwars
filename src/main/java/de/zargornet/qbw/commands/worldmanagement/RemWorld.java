package de.zargornet.qbw.commands.worldmanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.worlds.QbwWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Removes a world from qbw
 */
@QbwCommandPath(
        path = "remworld",
        permission = "qbw.remworld"
)
public class RemWorld implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW remworld <World name>");
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
        Qbw.getInstance().getDatabaseQueries().remWorld(args[0]);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccess!");

    }
}
