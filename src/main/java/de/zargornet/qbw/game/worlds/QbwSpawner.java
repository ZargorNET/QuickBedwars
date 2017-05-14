package de.zargornet.qbw.game.worlds;

import de.zargornet.qbw.game.QbwLocation;

/**
 * Defines the spawner
 */
public class QbwSpawner {
    private int id;
    private QbwLocation location;
    private QbwSpawnerType type;

    public QbwSpawner(int id, QbwLocation location, QbwSpawnerType type) {
        this.id = id;
        this.location = location;
        this.type = type;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized void setId(int id) {
        this.id = id;
    }

    public synchronized QbwLocation getLocation() {
        return location;
    }

    public synchronized void setLocation(QbwLocation location) {
        this.location = location;
    }

    public synchronized QbwSpawnerType getType() {
        return type;
    }

    public synchronized void setType(QbwSpawnerType type) {
        this.type = type;
    }

}
