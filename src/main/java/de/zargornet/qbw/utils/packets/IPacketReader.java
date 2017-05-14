package de.zargornet.qbw.utils.packets;

import org.bukkit.entity.Player;

/**
 * Interface which handles the incoming packets
 */
public interface IPacketReader {
    /**
     * Inject the reader
     *
     * @param p Player
     */
    void inject(Player p);

    /**
     * Eject the reader
     *
     * @param p Player
     */
    void eject(Player p);

    /**
     * Reads incoming packages
     *
     * @param p      Player
     * @param packet Packet
     */
    void readPacket(Player p, Object packet);

}
