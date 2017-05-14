package de.zargornet.qbw.customevent.events.arena;

import de.zargornet.qbw.customevent.CustomEvent;
import de.zargornet.qbw.game.arena.QbwArena;

/**
 * CustomEvent main for arena events
 */
public class ArenaCustomEvent extends CustomEvent {
    protected final QbwArena arena;

    public ArenaCustomEvent(QbwArena arena) {
        this.arena = arena;
    }

    public QbwArena getArena() {
        return arena;
    }
}
