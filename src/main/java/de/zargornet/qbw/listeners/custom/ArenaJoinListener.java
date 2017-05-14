package de.zargornet.qbw.listeners.custom;

import de.zargornet.qbw.customevent.QbwEvent;
import de.zargornet.qbw.customevent.events.player.PlayerArenaJoinEvent;
import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.arena.QbwArenaCountdown;
import de.zargornet.qbw.game.arena.QbwArenaUtil;
import de.zargornet.qbw.game.arena.QbwCounterType;
import de.zargornet.qbw.game.worlds.TeamColor;
import de.zargornet.qbw.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Listener for {@link PlayerArenaJoinEvent}
 */
public class ArenaJoinListener {
    @QbwEvent
    public void onArenaJoin(PlayerArenaJoinEvent e) {
        QbwArena arena = e.getArena();
        Player p = e.getPlayer();
        if (!arena.getPlayers().isEmpty() && arena.getPlayers().size() <= 1)
            QbwArenaCountdown.startCountdown(arena, QbwCounterType.STARTING);
        QbwArenaUtil.broadcastToArena(arena, "§a+ §3" + p.getName());
        p.getInventory().clear();

        ItemStack mapVote = new ItemBuilder(new ItemStack(Material.BOOK)).setName("§a§lMap").build();
        ItemStack leave = new ItemBuilder(new ItemStack(Material.SLIME_BALL)).setName("§c§lLeave arena").build();
        ItemStack teamChooser = new ItemBuilder(new ItemStack(Material.WOOL)).setDurability((short) TeamColor.WHITE.getSubid()).setName("§6§lTeam").build();

        p.getInventory().setItem(1, mapVote);
        p.getInventory().setItem(4, teamChooser);
        p.getInventory().setItem(7, leave);
    }
}
