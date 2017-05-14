package de.zargornet.qbw.commands.worldmanagement;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.game.worlds.QbwWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * List all existing worlds
 */
@QbwCommandPath(
        path = "listworld",
        permission = "qbw.listworld"
)
public class ListWorlds implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        List<QbwWorld> list = Qbw.getInstance().getDatabaseQueries().getWorlds();
        sender.sendMessage("§aWorlds: \n");
        list.forEach(world -> sender.sendMessage("§7- §e" + world.getName()));
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§7For more detailed output type: §e/QBW checkworld <World>");
    }
}
