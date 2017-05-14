package de.zargornet.qbw.commands.arena;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.customevent.events.arena.ArenaEndsEvent;
import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.arena.QbwArenaState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Optional;

/**
 * Stops a running arena
 */
@QbwCommandPath(
        path = "disablearena",
        permission = "qbw.disablearena"
)
public class DisableArena implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW disablearena <Name>");
            return;
        }
        Optional<QbwArena> arenaOptional = Qbw.getInstance().getArenas().stream().filter(arena -> arena.getName().toLowerCase().equals(args[0].toLowerCase())).findFirst();
        if (!arenaOptional.isPresent()) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cArena doesn't exist!");
            return;
        }
        if (arenaOptional.get().getState() == QbwArenaState.STOPPED) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cArena already stopped! If you want to enable it again use: §e/QBW startarena " + args[0]);
            return;
        }
        if (!arenaOptional.get().getPlayers().isEmpty()) {
            arenaOptional.get().getPlayers().forEach(arenaPlayer -> {
                if (arenaPlayer.getLastPos() != null)
                    arenaPlayer.getPlayer().teleport(arenaPlayer.getLastPos().getBukkitLocation());
                arenaPlayer.getPlayer().sendMessage(Qbw.getInstance().getPrefix() + "§cArena was disabled by an admin!");
            });
        }
        arenaOptional.get().setState(QbwArenaState.STOPPED);
        Qbw.getInstance().getCustomEventHandler().fireEvent(new ArenaEndsEvent(arenaOptional.get(), ArenaEndsEvent.ForceStop.ADMIN));
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccessfully force stopped arena §e" + args[0] + "§a! Don't forget it to enable it again with: §e/QBW startarena " + args[0]);
    }
}
