package de.zargornet.qbw.commands.arena;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.customevent.events.arena.ArenaStartsEvent;
import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.arena.QbwCounterType;
import de.zargornet.qbw.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * Force a game to start
 */
@QbwCommandPath(
        path = "forcestart",
        permission = "qbw.forcestart"
)
public class ForceStart implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (!QbwCommandUtil.senderIsPlayer(sender))
            return;
        Player p = (Player) sender;
        Optional<QbwArena> arenaOptional = Qbw.getInstance().getArenas().stream().filter(qbwArena -> qbwArena.getPlayers().stream().anyMatch(qbwPlayer -> qbwPlayer.getPlayer() == p)).findFirst();
        if (!arenaOptional.isPresent()) {
            p.sendMessage(Qbw.getInstance().getPrefix() + Messages.NOT_IN_A_ARENA.toString());
            return;
        }
        QbwArena arena = arenaOptional.get();
        if (!arena.getCounters().containsKey(QbwCounterType.STARTING)) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cTheres no running countdown!");
            return;
        }
        Bukkit.getScheduler().cancelTask(arena.getCounters().get(QbwCounterType.STARTING));
        arena.getCounters().remove(QbwCounterType.STARTING);
        Qbw.getInstance().getCustomEventHandler().fireEvent(new ArenaStartsEvent(arena));
    }
}
