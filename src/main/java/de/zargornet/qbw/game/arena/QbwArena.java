package de.zargornet.qbw.game.arena;

import de.zargornet.qbw.game.QbwLocation;
import de.zargornet.qbw.game.QbwPlayer;
import de.zargornet.qbw.game.worlds.QbwWorld;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Defines a new arena
 */
@Data
public class QbwArena implements Cloneable {
    private final String name;
    private QbwLocation lobby;
    private List<QbwPlayer> players = new ArrayList<>();
    private QbwArenaState state;
    private int playersPerTeam;
    private int teamCount;
    private QbwWorld world;
    private QbwWorld[] availableWorlds;
    private Map<QbwCounterType, Integer> counters = new HashMap<>();

    public QbwArena(String name, QbwLocation lobby, int playersPerTeam, int teamCount) {
        this.lobby = lobby;
        this.name = name;
        this.playersPerTeam = playersPerTeam;
        this.teamCount = teamCount;
        this.state = QbwArenaState.STOPPED;
    }
    public QbwArena clone() throws CloneNotSupportedException {
        return (QbwArena) super.clone();
    }

}
