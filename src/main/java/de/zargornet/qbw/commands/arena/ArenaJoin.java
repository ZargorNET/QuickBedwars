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
import de.zargornet.qbw.game.worlds.QbwWorld;
import de.zargornet.qbw.utils.Messages;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        /*
        Cache available Worlds
         */
        if (playersInArena == 0) {
            List<QbwWorld> uncheckedList = Qbw.getInstance().getDatabaseQueries().getWorlds();
            List<QbwWorld> worldList = new ArrayList<>();
            /*
            Check if worlds are valid
             */
            uncheckedList.forEach(world -> {
                if (world.isEnabled() && world.getTeams().size() == arena.getTeamCount())
                    worldList.add(world);
            });
            if (worldList.isEmpty()) {
                player.sendMessage(Qbw.getInstance().getPrefix() + "§cNo worlds found. Are they well configured? Does at least one world match the TeamCount from this arena?");
                return;
            }
            ///

            List<QbwWorld> finalWorldList = new ArrayList<>();
            if (worldList.size() > 4) {
                Random r = new Random(worldList.size());
                for (int i = 0; i < 4; i++) {
                    finalWorldList.add(worldList.get(r.nextInt()));
                }
            } else {
                finalWorldList.addAll(worldList);
            }
            QbwWorld[] worlds = new QbwWorld[finalWorldList.size()];
            worlds = finalWorldList.toArray(worlds);
            arena.setAvailableWorlds(worlds);
        }
        QbwPlayer qbwPlayer = new QbwPlayer(player, null, false);
        qbwPlayer.setLastPos(new QbwLocation(player.getLocation()));
        qbwPlayer.setContents(player.getInventory().getContents());
        arena.getPlayers().add(qbwPlayer);
        player.teleport(arena.getLobby().getBukkitLocation());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 50, 50);
        player.sendMessage(Qbw.getInstance().getPrefix() + "§aSuccess!");
        arena.setState(QbwArenaState.MAPVOTING);
        Qbw.getInstance().getCustomEventHandler().fireEvent(new PlayerArenaJoinEvent(player, arena));
    }
}
