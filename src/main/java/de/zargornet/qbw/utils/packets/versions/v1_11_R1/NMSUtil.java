package de.zargornet.qbw.utils.packets.versions.v1_11_R1;

import de.zargornet.qbw.utils.packets.EntityHandler;
import de.zargornet.qbw.utils.packets.INMSUtil;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * NMSUtil class for the version <b>v1_11_R1</b>
 *
 * @see de.zargornet.qbw.utils.packets.INMSUtil
 */
public class NMSUtil implements INMSUtil {
    private EntityHandler entityHandler;

    public NMSUtil() {
        this.entityHandler = new EntityHandler();
    }

    @Override
    public int spawnArmorStand(Player p, Location loc, String name, boolean small, boolean invis, boolean noGravity) {
        WorldServer s = ((CraftWorld) loc.getWorld()).getHandle();
        EntityArmorStand armorStand = new EntityArmorStand(s, loc.getX(), loc.getY() - 1.35, loc.getZ());
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(name);
        armorStand.setSmall(small);
        armorStand.setInvisible(invis);
        armorStand.setInvulnerable(true);
        armorStand.setNoGravity(noGravity);

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armorStand);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        entityHandler.addEntitiy(armorStand.getId(), new EntityHandler.EntityPlayer(armorStand, p));
        return armorStand.getId();
    }

    @Override
    public int spawnVillager(Player p, Location loc, String name) {
        WorldServer s = ((CraftWorld) loc.getWorld()).getHandle();
        EntityVillager villager = new EntityVillager(s);
        villager.setPosition(loc.getX(), loc.getY(), loc.getZ());
        villager.setCustomName(name);
        villager.setCustomNameVisible(true);
        villager.setInvulnerable(true);

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(villager);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        entityHandler.addEntitiy(villager.getId(), new EntityHandler.EntityPlayer(villager, p));
        return villager.getId();
    }

    @Override
    public void destroyEntity(Player p, int id) {
        PacketPlayOutEntityDestroy deadEntity = new PacketPlayOutEntityDestroy(id);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(deadEntity);

        entityHandler.remEntitity(id);
    }

    @Override
    public void rotateEntitiy(Player p, int id, float yaw, float pitch) {
        PacketPlayOutEntity.PacketPlayOutEntityLook packet = new PacketPlayOutEntity.PacketPlayOutEntityLook(id, (byte) yaw, (byte) pitch, true);
        PacketPlayOutEntityHeadRotation packet2 = new PacketPlayOutEntityHeadRotation(((Entity) entityHandler.getEntity(id)), (byte) (yaw * 256.0F / 360.0F));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet2);
    }

    @Override
    public void sendActionBar(Player p, String msg) {
        CraftPlayer cp = ((CraftPlayer) p);
        PlayerConnection connection = cp.getHandle().playerConnection;
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}"), (byte) 2);
        connection.sendPacket(packet);
    }

    @Override
    public void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer cp = ((CraftPlayer) p);
        PlayerConnection connection = cp.getHandle().playerConnection;
        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, IChatBaseComponent.ChatSerializer.a(""), fadeIn, stay, fadeOut);
        PacketPlayOutTitle packet2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}"));
        PacketPlayOutTitle packet3 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}"));
        connection.sendPacket(packet);
        connection.sendPacket(packet2);
        connection.sendPacket(packet3);
    }
}
