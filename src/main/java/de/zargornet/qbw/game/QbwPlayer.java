package de.zargornet.qbw.game;

import de.zargornet.qbw.game.worlds.QbwTeam;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A qbw player who playing qbw at the moment
 */

public class QbwPlayer {
    private Player player;
    private QbwTeam team;
    private boolean death;
    private QbwLocation lastPos;
    private ItemStack[] contents;

    public QbwPlayer(Player player, QbwTeam team, boolean death) {
        this.player = player;
        this.team = team;
        this.death = death;
    }

    public Player getPlayer() {
        return player;
    }

    public synchronized void setPlayer(Player player) {
        this.player = player;
    }

    public synchronized QbwTeam getTeam() {
        return team;
    }

    public synchronized void setTeam(QbwTeam team) {
        this.team = team;
    }

    public synchronized boolean isDeath() {
        return death;
    }

    public synchronized void setDeath(boolean death) {
        this.death = death;
    }

    public synchronized QbwLocation getLastPos() {
        return lastPos;
    }

    public synchronized void setLastPos(QbwLocation lastPos) {
        this.lastPos = lastPos;
    }

    public synchronized ItemStack[] getContents() {
        return contents;
    }

    public synchronized void setContents(ItemStack[] contents) {
        this.contents = contents;
    }
}
