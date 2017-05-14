package de.zargornet.qbw.game.worlds;

import de.zargornet.qbw.game.QbwLocation;

/**
 * Defines a qbw team
 */
public class QbwTeam {
    private TeamColor color;
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

    public synchronized TeamColor getColor() {
        return color;
    }

    public synchronized void setColor(TeamColor color) {
        this.color = color;
    }

    public synchronized QbwLocation getSpawnLoc() {
        return spawnLoc;
    }

    public synchronized void setSpawnLoc(QbwLocation spawnLoc) {
        this.spawnLoc = spawnLoc;
    }

    public synchronized QbwLocation getShopLoc() {
        return shopLoc;
    }

    public synchronized void setShopLoc(QbwLocation shopLoc) {
        this.shopLoc = shopLoc;
    }

    public synchronized QbwLocation getTeamBedLoc() {
        return teamBedLoc;
    }

    public synchronized void setTeamBedLoc(QbwLocation teamBedLoc) {
        this.teamBedLoc = teamBedLoc;
    }

    public synchronized boolean isBed() {
        return bed;
    }

    public synchronized void setBed(boolean bed) {
        this.bed = bed;
    }
}
