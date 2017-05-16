package de.zargornet.qbw.utils.packets.versions.v1_11_R1;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.customevent.events.packets.IncomingPacketEvent;
import de.zargornet.qbw.utils.packets.IPacketReader;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.Getter;
import net.minecraft.server.v1_11_R1.Packet;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Packet reader for v_11_R1
 *
 * @see de.zargornet.qbw.utils.packets.IPacketReader
 */
public class PacketReader implements IPacketReader {
    @Getter
    private final Map<Player, Channel> channels = new HashMap<>();

    @Override
    public void inject(Player p) {
        CraftPlayer player = (CraftPlayer) p;
        if (!channels.containsKey(p)) {
            Channel channel;
            channel = player.getHandle().playerConnection.networkManager.channel;
            channel.pipeline().addAfter("decoder", "QbwPacketReader",
                    new MessageToMessageDecoder<Packet<?>>() {
                        @Override
                        protected void decode(ChannelHandlerContext arg0,
                                              Packet<?> packet, List<Object> arg2)
                                throws Exception {
                            readPacket(p, packet);
                            arg2.add(packet);
                        }
                    });
            channels.put(p, channel);
        }
    }

    @Override
    public void eject(Player p) {
        if (channels.containsKey(p)) {
            Channel channel = channels.get(p);
            if (channel.pipeline().get("QbwPacketReader") != null) {
                channel.pipeline().remove("QbwPacketReader");
                channels.remove(p);
            }
        }
    }

    @Override
    public void readPacket(Player p, Object packet) {
        Qbw.getInstance().getCustomEventHandler().fireEvent(new IncomingPacketEvent(p, packet));
    }
}
