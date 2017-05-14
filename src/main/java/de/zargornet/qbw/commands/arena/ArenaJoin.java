package de.zargornet.qbw.commands.arena;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.commands.IQbwCommand;
import de.zargornet.qbw.commands.QbwCommandPath;
import de.zargornet.qbw.commands.QbwCommandUtil;
import de.zargornet.qbw.customevent.events.player.PlayerArenaJoinEvent;
import de.zargornet.qbw.game.QbwLocation;
import de.zargornet.qbw.game.QbwPlayer;
import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.arena.QbwArenaState;
import de.zargornet.qbw.utils.Messages;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Player joins an Arena
 */
@QbwCommandPath(
        path = "join",
        permission = "qbw.join"
)
public class ArenaJoin implements IQbwCommand {
    @Override
    public void onCommand(CommandSender sender, Command cmd, String[] args) {
        if (!QbwCommandUtil.senderIsPlayer(sender))
            return;
        if (args.length != 1) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cPlease use: §e/QBW join <Arena name>");
            return;
        }
        QbwArena arena = QbwCommandUtil.getArenaIfNotNull(sender, args[0].toLowerCase());
        if (arena == null)
            return;
        if (arena.getState() != QbwArenaState.LOBBY) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + Messages.ARENA_INVALID_STATE.toString());
            return;
        }
        Player player = (Player) sender;
        int teamAmount = arena.getTeamCount();
        int maxPlayersPerTeam = arena.getPlayersPerTeam();
        int playersInArena = (arena.getPlayers() == null ? 0 : arena.getPlayers().size());
        if (maxPlayersPerTeam * teamAmount <= playersInArena) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cArena is full!");
            return;
        }
        if (arena.getPlayers() != null && arena.getPlayers().stream().anyMatch(qbwPlayer -> qbwPlayer.getPlayer() == player)) {
            sender.sendMessage(Qbw.getInstance().getPrefix() + "§cYou are already in an arena!");
            return;
        }
        QbwPlayer qbwPlayer = new QbwPlayer(player, null, false);
        qbwPlayer.setLastPos(new QbwLocation(player.getLocation()));
        qbwPlayer.setContents(player.getInventory().getContents());
        arena.getPlayers().add(qbwPlayer);
        player.teleport(arena.getLobby().getBukkitLocation());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 50, 50);
        player.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccess!");
        Qbw.getInstance().getCustomEventHandler().fireEvent(new PlayerArenaJoinEvent(player, arena));
    }
}
