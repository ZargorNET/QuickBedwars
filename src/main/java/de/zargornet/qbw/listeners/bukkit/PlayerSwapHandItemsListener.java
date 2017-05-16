package de.zargornet.qbw.listeners.bukkit;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.game.QbwPlayer;
import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.arena.QbwArenaState;
import de.zargornet.qbw.game.arena.QbwArenaUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.Optional;

/**
 * Listener for {@link PlayerSwapHandItemsEvent}
 */
public class PlayerSwapHandItemsListener implements Listener {
    @EventHandler
    public void on(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();
        QbwPlayer qbwPlayer = QbwArenaUtil.getQbwPlayerInArenas(p);
        Optional<QbwArena> arenaOptional = Qbw.getInstance().getArenas().stream().filter(qbwArena -> qbwArena.getPlayers().stream().anyMatch(qbwPlayer1 -> qbwPlayer.getPlayer() == p)).findFirst();
        if (!arenaOptional.isPresent())
            return;
        if (qbwPlayer == null)
            return;
        if (arenaOptional.get().getState() != QbwArenaState.RUNNING) ;
        e.setCancelled(true);
    }
}
