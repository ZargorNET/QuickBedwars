package de.zargornet.qbw.customevent.events.player;

import de.zargornet.qbw.customevent.CustomEvent;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * CustomEvent main for player events
 */
public class PlayerCustomEvent extends CustomEvent {
    @Getter
    private final Player player;

    PlayerCustomEvent(Player player) {
        this.player = player;
    }
}
