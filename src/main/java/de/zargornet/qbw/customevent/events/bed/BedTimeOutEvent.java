package de.zargornet.qbw.customevent.events.bed;


import de.zargornet.qbw.game.worlds.QbwTeam;
import org.bukkit.block.Block;

/**
 * Event will be triggered if a bed runs out of time
 */
public class BedTimeOutEvent extends BedCustomEvent {
    BedTimeOutEvent(QbwTeam team, Block bed) {
        super(team, bed);
    }
}
