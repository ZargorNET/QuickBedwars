package de.zargornet.qbw.game.worlds;

import java.util.List;

/**
 * Defines a qbw world
 */
public class QbwWorld implements Cloneable {
    private String name;
    private List<QbwSpawner> spawnerList;
    private List<QbwTeam> teams;
    private boolean loaded;

    public QbwWorld(String name, List<QbwSpawner> spawner, List<QbwTeam> teams, boolean loaded) {
        this.name = name;
        this.spawnerList = spawner;
        this.teams = teams;
        this.loaded = loaded;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized List<QbwSpawner> getSpawnerList() {
        return spawnerList;
    }

    public synchronized void setSpawnerList(List<QbwSpawner> spawnerList) {
        this.spawnerList = spawnerList;
    }

    public synchronized List<QbwTeam> getTeams() {
        return teams;
    }

    public synchronized void setTeams(List<QbwTeam> teams) {
        this.teams = teams;
    }

    public synchronized boolean isLoaded() {
        return loaded;
    }

    public synchronized void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }


    public QbwWorld clone() throws CloneNotSupportedException {
        return (QbwWorld) super.clone();
    }
}
