package de.zargornet.qbw.customevent.events.packets;

import de.zargornet.qbw.customevent.CustomEvent;
import org.bukkit.entity.Player;


/**
 * CustomEvent main for the packet reader
 *
 * @see de.zargornet.qbw.utils.packets.IPacketReader
 */
public class PacketCustomEvent extends CustomEvent {
    private Object packet;
    private Player player;

    PacketCustomEvent(Player player, Object packet) {
        this.packet = packet;
        this.player = player;
    }

    public Object getPacket() {
        return packet;
    }

    public Player getPlayer() {
        return player;
    }
}
