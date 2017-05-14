package de.zargornet.qbw.customevent.events.bed;

import de.zargornet.qbw.customevent.CustomEvent;
import de.zargornet.qbw.game.worlds.QbwTeam;
import org.bukkit.block.Block;

/**
 * CustomEvent main for bed events
 */
public class BedCustomEvent extends CustomEvent {
    private final Block bed;
    private final QbwTeam team;

    BedCustomEvent(QbwTeam team, Block bed) {
        this.team = team;
        this.bed = bed;
    }


    public Block getBed() {
        return bed;
    }

    public QbwTeam getTeam() {
        return team;
    }
}
