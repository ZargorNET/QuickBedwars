package de.zargornet.qbw.customevent.events.player;

import de.zargornet.qbw.game.arena.QbwArena;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * PlayerArenaLeaveEvent. Called when a player leaves an arena
 */
public class PlayerArenaLeaveEvent extends PlayerCustomEvent {
    @Getter
    private final QbwArena arena;

    public PlayerArenaLeaveEvent(Player player, QbwArena arena) {
        super(player);
        this.arena = arena;
    }
}
