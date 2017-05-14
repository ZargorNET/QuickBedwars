package de.zargornet.qbw.listeners.bukkit;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.game.QbwPlayer;
import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.arena.QbwArenaUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Optional;

/**
 * Listener for {@link PlayerInteractEvent}
 */
public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        QbwPlayer qbwPlayer = QbwArenaUtil.getQbwPlayerInArenas(p);
        Optional<QbwArena> arenaOptional = Qbw.getInstance().getArenas().stream().filter(qbwArena -> qbwArena.getPlayers().stream().anyMatch(qbwPlayer1 -> qbwPlayer.getPlayer() == p)).findFirst();
        if (!arenaOptional.isPresent())
            return;
        if (qbwPlayer == null)
            return;
        switch (arenaOptional.get().getState()) {
            case LOBBY:
            case MAPVOTING:
                if (p.getInventory().getItemInMainHand().getType() == Material.SLIME_BALL || p.getInventory().getItemInOffHand().getType() == Material.SLIME_BALL) {
                    p.performCommand("qbw leave");
                }
                break;
        }
    }
}
