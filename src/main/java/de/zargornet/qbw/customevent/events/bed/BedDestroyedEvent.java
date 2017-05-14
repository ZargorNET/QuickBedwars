package de.zargornet.qbw.customevent.events.bed;

import de.zargornet.qbw.game.worlds.QbwTeam;
import org.bukkit.block.Block;

/**
 * Event will be called if a bed was destroyed
 */
public class BedDestroyedEvent extends BedCustomEvent {
    private final boolean byPlayer;

    public BedDestroyedEvent(QbwTeam team, Block bed, boolean byPlayer) {
        super(team, bed);
        this.byPlayer = byPlayer;
    }

    public boolean isByPlayer() {
        return byPlayer;
    }
}
