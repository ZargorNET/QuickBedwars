package de.zargornet.qbw.game.worlds;

import de.zargornet.qbw.game.QbwLocation;
import lombok.Data;

/**
 * Defines a qbw team
 */
@Data
public class QbwTeam {
    private final TeamColor color;
    private QbwLocation spawnLoc;
    private QbwLocation shopLoc;
    private QbwLocation teamBedLoc;
    private boolean bed;

    public QbwTeam(TeamColor color, QbwLocation spawnLoc, QbwLocation teamBedLoc, QbwLocation shopLoc) {
        this.color = color;
        this.spawnLoc = spawnLoc;
        this.teamBedLoc = teamBedLoc;
        this.bed = true;
        this.shopLoc = shopLoc;
    }
}
