package de.zargornet.qbw.customevent.events.arena;

import de.zargornet.qbw.game.arena.QbwArena;
import lombok.Getter;

/**
 * Event will be called if an arena ends
 */
public class ArenaEndsEvent extends ArenaCustomEvent {
    @Getter
    private final EndReason endReason;

    public ArenaEndsEvent(QbwArena arena, EndReason endReason) {
        super(arena);
        this.endReason = endReason;
    }

    public enum EndReason {
        WIN,
        ADMIN,
        TIME_OUT
    }
}
