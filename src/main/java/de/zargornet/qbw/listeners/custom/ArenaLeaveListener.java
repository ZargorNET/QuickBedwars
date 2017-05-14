package de.zargornet.qbw.listeners.custom;

import de.zargornet.qbw.customevent.QbwEvent;
import de.zargornet.qbw.customevent.events.player.PlayerArenaLeaveEvent;
import de.zargornet.qbw.game.QbwPlayer;
import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.arena.QbwArenaUtil;
import org.bukkit.entity.Player;

/**
 * Listener for {@link PlayerArenaLeaveEvent}
 */
public class ArenaLeaveListener {
    @QbwEvent
    public void onPlayerArenaLeave(PlayerArenaLeaveEvent e) {
        Player p = e.getPlayer();
        QbwArena arena = e.getArena();
        QbwPlayer qbwPlayer = QbwArenaUtil.getQbwPlayerInArenas(p);
        if (qbwPlayer == null)
            return;
        p.getInventory().setContents(qbwPlayer.getContents());
        arena.getPlayers().remove(qbwPlayer);
        QbwArenaUtil.broadcastToArena(arena, "§c- §3" + p.getName());

        //TODO handle thinks like game winning etc.
    }
}
