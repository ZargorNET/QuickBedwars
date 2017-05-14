package de.zargornet.qbw.customevent.events.bed;


import de.zargornet.qbw.game.worlds.QbwTeam;
import org.bukkit.block.Block;

/**
 * Event will be called if a bed gets time
 */
public class BedTimeAddedEvent extends BedCustomEvent {
    BedTimeAddedEvent(QbwTeam team, Block bed) {
        super(team, bed);
    }
}
