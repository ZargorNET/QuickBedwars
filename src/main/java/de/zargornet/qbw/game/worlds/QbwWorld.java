package de.zargornet.qbw.game.worlds;

import lombok.Data;

import java.util.List;

/**
 * Defines a qbw world
 */
@Data
public class QbwWorld implements Cloneable {
    private final String name;
    private List<QbwSpawner> spawnerList;
    private List<QbwTeam> teams;
    private boolean enabled;
    private boolean loaded;

    public QbwWorld(String name, List<QbwSpawner> spawner, List<QbwTeam> teams, boolean loaded, boolean enabled) {
        this.name = name;
        this.spawnerList = spawner;
        this.teams = teams;
        this.loaded = loaded;
    }
    public QbwWorld clone() throws CloneNotSupportedException {
        return (QbwWorld) super.clone();
    }
}
