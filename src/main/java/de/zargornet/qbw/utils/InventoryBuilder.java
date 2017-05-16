package de.zargornet.qbw.utils;

import de.zargornet.qbw.Qbw;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Optional;

/**
 * Util class for building inventories
 */
public abstract class InventoryBuilder implements Listener {
    private HashMap<Integer, ItemStack> items;
    private int size;
    private InventoryType type;
    private String name;
    private Player player;
    private boolean closeable;
    private Inventory inv;
    private Material closeItem;

    public InventoryBuilder(HashMap<Integer, ItemStack> items, int size, String name, Player player, Optional<Material> closeItem) {
        this.items = items;
        this.size = size;
        this.name = name;
        this.player = player;
        closeItem.ifPresent(itemStack -> this.closeItem = itemStack);
        build();
    }

    public InventoryBuilder(HashMap<Integer, ItemStack> items, InventoryType type, String name, Player player, Optional<Material> closeItem) {
        this.items = items;
        this.type = type;
        this.name = name;
        this.player = player;
        closeItem.ifPresent(itemStack -> this.closeItem = itemStack);
        build();
    }

    /**
     * Builds the inventory and opens it
     */
    private void build() {
        if (type != null)
            inv = Bukkit.createInventory(null, type, name);
        else
            inv = Bukkit.createInventory(null, size, name);

        items.forEach(inv::setItem);
        if (closeItem != null)
            inv.setItem(inv.getSize() - 1, new ItemBuilder(new ItemStack(closeItem)).setName(Messages.INV_CLOSE_ITEM.toString()).build());
        closeable = false;
        Qbw.getInstance().getServer().getPluginManager().registerEvents(this, Qbw.getInstance());
        player.openInventory(inv);
    }

    /**
     * Closes the inventory and deregister the listeners
     *
     * @param closeInv Should player#closeInventory be called?
     */
    protected void close(boolean closeInv) {
        closeable = true;
        HandlerList.unregisterAll(this);
        if (player.isOnline() && closeInv)
            player.closeInventory();
    }

    public abstract void onClick(InventoryClickEvent e);

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (e.getClickedInventory() != null)
            if (e.getCurrentItem() != null || e.getCurrentItem().getType() != Material.AIR)
                if (e.getWhoClicked() == player)
                    if (closeItem != null)
                        if (e.getCurrentItem().getType() == closeItem && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName().equals(Messages.INV_CLOSE_ITEM.toString()))
                            close(true);
                        else if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName())
                            onClick(e);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (!closeable)
            if (e.getPlayer() == player)
                if (player.isOnline() && !player.isDead())
                    Bukkit.getScheduler().runTaskLater(Qbw.getInstance(), () -> {
                        player.openInventory(inv);
                    }, 5L);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent e) {
        if (e.getPlayer() == player)
            close(false);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity() == player)
            close(false);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (e.getPlayer() == player)
            close(true);
    }
}
