package de.zargornet.qbw.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Defines a own location for serialising
 * Needed for serialising
 */
public class QbwLocation {
    private String world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    /**
     * Creates a new qbw location
     *
     * @param loc Bukkit location
     */
    public QbwLocation(Location loc) {
        this.world = loc.getWorld().getName();
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
        this.yaw = loc.getYaw();
        this.pitch = loc.getPitch();
    }

    /**
     * Creates a new qbw location
     *
     * @param world World name
     * @param x     X
     * @param y     Y
     * @param z     Z
     * @param yaw   YAW
     * @param pitch PITCH
     */
    public QbwLocation(String world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Creates a new qbw location
     *
     * @param world World name
     * @param x     X
     * @param y     Y
     * @param z     Z
     */
    public QbwLocation(String world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.pitch = 0;
    }

    /**
     * Gets a bukkit location again
     *
     * @return Bukkit location
     */
    public Location getBukkitLocation() {
        Location loc = new Location(Bukkit.getWorld(world), x, y, z);
        loc.setYaw(yaw);
        loc.setPitch(pitch);
        return loc;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
