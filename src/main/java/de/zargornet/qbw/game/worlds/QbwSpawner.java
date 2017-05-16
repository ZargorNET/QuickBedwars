package de.zargornet.qbw.game.worlds;

import de.zargornet.qbw.game.QbwLocation;
import lombok.Data;

/**
 * Defines the spawner
 */
@Data
public class QbwSpawner {
    private final int id;
    private QbwLocation location;
    private QbwSpawnerType type;

    public QbwSpawner(int id, QbwLocation location, QbwSpawnerType type) {
        this.id = id;
        this.location = location;
        this.type = type;
    }

}
