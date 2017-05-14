package de.zargornet.qbw.customevent.events.arena;

import de.zargornet.qbw.game.arena.QbwArena;

/**
 * Event will be called if an arena ends
 */
public class ArenaEndsEvent extends ArenaCustomEvent {
    private final ForceStop forceStop;

    public ArenaEndsEvent(QbwArena arena, ForceStop forceStop) {
        super(arena);
        this.forceStop = forceStop;
    }

    public ForceStop getForceStop() {
        return forceStop;
    }

    public enum ForceStop {
        NONE,
        ADMIN,
        TIME_OUT
    }
}
