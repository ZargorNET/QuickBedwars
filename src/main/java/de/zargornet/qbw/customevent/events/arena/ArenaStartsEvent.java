package de.zargornet.qbw.customevent.events.arena;

import de.zargornet.qbw.game.arena.QbwArena;

/**
 * Event which will be called if a arena starts
 */
public class ArenaStartsEvent extends ArenaCustomEvent {
    public ArenaStartsEvent(QbwArena arena) {
        super(arena);
    }
}
