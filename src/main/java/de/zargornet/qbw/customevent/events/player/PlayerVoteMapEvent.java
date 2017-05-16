package de.zargornet.qbw.customevent.events.player;

import de.zargornet.qbw.game.arena.QbwArena;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * Event will be called if a player votes for a map
 */
public class PlayerVoteMapEvent extends PlayerCustomEvent {
    @Getter
    private final String votedMap;
    @Getter
    private final QbwArena arena;

    public PlayerVoteMapEvent(Player player, String votedMap, QbwArena arena) {
        super(player);
        this.votedMap = votedMap;
        this.arena = arena;
    }
}
