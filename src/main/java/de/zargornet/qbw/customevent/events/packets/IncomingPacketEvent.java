package de.zargornet.qbw.customevent.events.packets;

import org.bukkit.entity.Player;

/**
 * Events will be called if a packet arrives
 */
public class IncomingPacketEvent extends PacketCustomEvent {
    public IncomingPacketEvent(Player player, Object packet) {
        super(player, packet);
    }
}
