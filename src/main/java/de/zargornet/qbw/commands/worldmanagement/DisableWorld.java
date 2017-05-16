package de.zargornet.qbw.commands.worldmanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.game.worlds.QbwWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Disable a world
 */
@QbwCommandPath(
        path = "disableworld",
        permission = "qbw.disableworld"
)
public class DisableWorld implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW disableworld <Name>");
            return;
        }
        QbwWorld world = Qbw.getInstance().getDatabaseQueries().getWorld(args[0]);
        if (!QbwCommandUtil.worldAdded(sender, world))
            return;
        if (!QbwCommandUtil.checkIfWorldIsNotUsed(sender, world.getName()))
            return;

        if (!world.isEnabled()) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cWorld isn't enabled!");
            return;
        }
        world.setEnabled(false);
        Qbw.getInstance().getDatabaseQueries().setWorld(world);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccess!");
    }
}
