package de.zargornet.qbw.game.arena;

import de.zargornet.qbw.game.QbwLocation;
import de.zargornet.qbw.game.QbwPlayer;
import de.zargornet.qbw.game.worlds.QbwWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Defines a new arena
 */
public class QbwArena implements Cloneable {
    private final String name;
    private QbwLocation lobby;
    private List<QbwPlayer> players = new ArrayList<>();
    private QbwArenaState state;
    private int playersPerTeam;
    private int teamCount;
    private QbwWorld world;
    private Map<QbwCounterType, Integer> counters = new HashMap<>();

    public QbwArena(String name, QbwLocation lobby, int playersPerTeam, int teamCount) {
        this.lobby = lobby;
        this.name = name;
        this.playersPerTeam = playersPerTeam;
        this.teamCount = teamCount;
        this.state = QbwArenaState.STOPPED;
    }

    public synchronized void setCounters(Map<QbwCounterType, Integer> counters) {
        this.counters = counters;
    }

    public synchronized Map<QbwCounterType, Integer> getCounters() {
        return counters;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized QbwLocation getLobby() {
        return lobby;
    }

    public synchronized void setLobby(QbwLocation lobby) {
        this.lobby = lobby;
    }

    public synchronized List<QbwPlayer> getPlayers() {
        return players;
    }

    public synchronized void setPlayers(List<QbwPlayer> players) {
        this.players = players;
    }

    public synchronized QbwArenaState getState() {
        return state;
    }

    public synchronized void setState(QbwArenaState state) {
        this.state = state;
    }

    public synchronized int getPlayersPerTeam() {
        return playersPerTeam;
    }

    public synchronized void setPlayersPerTeam(int playersPerTeam) {
        this.playersPerTeam = playersPerTeam;
    }

    public synchronized QbwWorld getWorld() {
        return world;
    }

    public synchronized void setWorld(QbwWorld world) {
        this.world = world;
    }

    public synchronized int getTeamCount() {
        return teamCount;
    }

    public synchronized void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public QbwArena clone() throws CloneNotSupportedException {
        return (QbwArena) super.clone();
    }

}
