package de.zargornet.qbw.customevent.events.player;

import de.zargornet.qbw.customevent.CustomEvent;
import org.bukkit.entity.Player;

/**
 * CustomEvent main for player events
 */
public class PlayerCustomEvent extends CustomEvent {
    private final Player player;

    PlayerCustomEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
