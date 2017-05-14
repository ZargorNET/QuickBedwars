package de.zargornet.qbw.customevent.events.player;

import de.zargornet.qbw.game.arena.QbwArena;
import org.bukkit.entity.Player;

/**
 * Event will be called if a player votes for a map
 */
public class PlayerVoteMapEvent extends PlayerCustomEvent {
    private final String votedMap;
    private final QbwArena arena;

    public PlayerVoteMapEvent(Player player, String votedMap, QbwArena arena) {
        super(player);
        this.votedMap = votedMap;
        this.arena = arena;
    }

    public String getVotedMap() {
        return votedMap;
    }

    public QbwArena getArena() {
        return arena;
    }
}
