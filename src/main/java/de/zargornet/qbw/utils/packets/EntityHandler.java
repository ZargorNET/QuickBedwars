package de.zargornet.qbw.utils.packets;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles packet entities
 */
public class EntityHandler {
    /**
     * Map of all entities
     */
    private final Map<Integer, EntityPlayer> entities = new HashMap<>();

    /**
     * Adds an entity to map
     *
     * @param id           EntitiyID
     * @param entityPlayer EntityPlayer
     */
    public void addEntitiy(int id, EntityPlayer entityPlayer) {
        if (!entities.containsKey(id))
            entities.put(id, entityPlayer);
    }

    /***
     * Removes an entity from map
     * @param id EntityID
     */
    public void remEntitity(int id) {
        if (entities.containsKey(id))
            entities.remove(id);
    }

    /***
     * Gets an entity from map
     * @param id EntitiyID
     * @return Entity
     */
    public Object getEntity(int id) {
        return entities.get(id);
    }

    /**
     * Get all entitie ids from a player
     *
     * @param player Player
     * @return List with entity ids
     */
    public List<Integer> getEntitieIDsFromPlayer(Player player) {
        List<Integer> list = new ArrayList<>();
        entities.entrySet().stream().filter(entry -> entry.getValue().getPlayer() == player).forEach(e -> list.add(e.getKey()));
        return list;
    }


    public static class EntityPlayer {
        private Object entity;
        private Player player;

        public EntityPlayer(Object entity, Player player) {
            this.entity = entity;
            this.player = player;
        }

        public Object getEntity() {
            return entity;
        }

        public Player getPlayer() {
            return player;
        }
    }
}
