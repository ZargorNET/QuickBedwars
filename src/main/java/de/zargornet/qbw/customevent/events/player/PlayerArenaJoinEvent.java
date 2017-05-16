package de.zargornet.qbw.customevent.events.player;

import de.zargornet.qbw.game.arena.QbwArena;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * PlayerArenaJoinEvent event. Called if a player joins an arena
 */
public class PlayerArenaJoinEvent extends PlayerCustomEvent {
    @Getter
    private final QbwArena arena;

    public PlayerArenaJoinEvent(final Player p, final QbwArena arena) {
        super(p);
        this.arena = arena;
    }
}
