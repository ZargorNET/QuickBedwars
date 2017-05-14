package de.zargornet.qbw.game.worlds;


/**
 * TeamColor
 */
public enum TeamColor {
    WHITE(0),
    ORANGE(1),
    MAGENTA(2),
    LIGHTBLUE(3),
    YELLOW(4),
    LIME(5),
    PINK(6),
    GRAY(7),
    LIGHTGRAY(8),
    CYAN(9),
    PURPLE(10),
    BLUE(11),
    BROWN(12),
    GREEN(13),
    RED(14),
    BLACK(15);

    private int subid;

    TeamColor(int subid) {
        this.subid = subid;
    }

    public int getSubid() {
        return subid;
    }
}
