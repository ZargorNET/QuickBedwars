package de.zargornet.qbw.commands.arena;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.customevent.events.player.PlayerArenaLeaveEvent;
import de.zargornet.qbw.game.QbwPlayer;
import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.arena.QbwArenaUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * Player leaves an arena
 */
@QbwCommandPath(
        path = "leave",
        permission = "qbw.arenaleave"
)
public class ArenaLeave implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (!QbwCommandUtil.senderIsPlayer(sender))
            return;
        Player player = (Player) sender;
        Optional<QbwArena> arenaOptional = Qbw.getInstance().getArenas().stream().filter(qbwArena -> qbwArena.getPlayers().stream().anyMatch(qbwPlayer -> qbwPlayer.getPlayer() == player)).findFirst();
        if (!arenaOptional.isPresent()) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cYou are not in an arena!");
            return;
        }
        QbwArena arena = arenaOptional.get();
        QbwPlayer qbwPlayer = QbwArenaUtil.getQbwPlayerInArenas(player);
        sender.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccess!");
        Qbw.getInstance().getCustomEventHandler().fireEvent(new PlayerArenaLeaveEvent(player, arena));
        if (qbwPlayer == null)
            return;
        arena.getPlayers().remove(qbwPlayer);
        if (qbwPlayer.getLastPos() != null)
            player.teleport(qbwPlayer.getLastPos().getBukkitLocation());

    }
}
