package de.zargornet.qbw.listeners.bukkit;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.customevent.events.player.PlayerArenaLeaveEvent;
import de.zargornet.qbw.game.QbwPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

/**
 * Listener for {@link PlayerQuitEvent}
 */
public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Qbw.getInstance().getArenas().forEach(arena -> {
            Optional<QbwPlayer> qbwPlayer = arena.getPlayers().stream().filter(qbwPlayer1 -> qbwPlayer1.getPlayer() == p).findFirst();
            qbwPlayer.ifPresent(qbwPlayer1 -> Qbw.getInstance().getCustomEventHandler().fireEvent(new PlayerArenaLeaveEvent(p, arena)));
        });
    }
}
