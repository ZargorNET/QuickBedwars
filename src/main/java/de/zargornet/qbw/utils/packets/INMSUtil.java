package de.zargornet.qbw.utils.packets;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Interface for packet things
 */
public interface INMSUtil {
    /***
     * Spawn an armorstand for player <b>p</b>
     * @param p Player
     * @param loc Location
     * @param name Name
     * @param small Should stand be small?
     * @param invis Should stand be invisible?
     * @param noGravity Should stand have no gravity?
     * @return EntityID
     */
    int spawnArmorStand(Player p, Location loc, String name, boolean small, boolean invis, boolean noGravity);

    /***
     * Spawn a villager for player <b>p</b>
     * @param p Player
     * @param loc Location
     * @param name Name
     * @return EntityID
     */
    int spawnVillager(Player p, Location loc, String name);

    /***
     * Destroys an entity
     * @param p Player
     * @param id EntityID
     */
    void destroyEntity(Player p, int id);

    /***
     * Rotates an entity
     * @param p Player
     * @param id EntityID
     * @param yaw Yaw
     * @param pitch Pitch
     */
    void rotateEntitiy(Player p, int id, float yaw, float pitch);

    /***
     *  Sends player an actionbar title
     * @param p Player
     * @param msg Message
     */
    void sendActionBar(Player p, String msg);

    /***
     * Sends an player an title
     * @param p Player
     * @param title Maintitle message
     * @param subtitle Subtitle message
     * @param fadeIn Fadein time
     * @param stay Stay time
     * @param fadeOut Fadeout time
     */
    void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut);
}
