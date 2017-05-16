package de.zargornet.qbw.game;

import de.zargornet.qbw.game.worlds.QbwTeam;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A qbw player who playing qbw at the moment
 */
@Data
public class QbwPlayer {
    private final Player player;
    private QbwTeam team;
    private boolean death;
    private QbwLocation lastPos;
    private ItemStack[] contents;

    public QbwPlayer(Player player, QbwTeam team, boolean death) {
        this.player = player;
        this.team = team;
        this.death = death;
    }
}
