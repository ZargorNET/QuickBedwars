package de.zargornet.qbw.listeners.bukkit;

import de.zargornet.qbw.Qbw;
import de.zargornet.qbw.customevent.events.player.PlayerTeamSelectedEvent;
import de.zargornet.qbw.game.QbwPlayer;
import de.zargornet.qbw.game.arena.QbwArena;
import de.zargornet.qbw.game.arena.QbwArenaState;
import de.zargornet.qbw.game.arena.QbwArenaUtil;
import de.zargornet.qbw.game.worlds.QbwTeam;
import de.zargornet.qbw.game.worlds.QbwWorld;
import de.zargornet.qbw.game.worlds.TeamColor;
import de.zargornet.qbw.utils.InventoryBuilder;
import de.zargornet.qbw.utils.ItemBuilder;
import de.zargornet.qbw.utils.Messages;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Listener for {@link PlayerInteractEvent}
 */
public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        QbwPlayer qbwPlayer = QbwArenaUtil.getQbwPlayerInArenas(p);
        Optional<QbwArena> arenaOptional = Qbw.getInstance().getArenas().stream().filter(qbwArena -> qbwArena.getPlayers().stream().anyMatch(qbwPlayer1 -> qbwPlayer.getPlayer() == p)).findFirst();
        if (!arenaOptional.isPresent())
            return;
        if (qbwPlayer == null)
            return;
        if (arenaOptional.get().getState() == QbwArenaState.LOBBY || arenaOptional.get().getState() == QbwArenaState.MAPVOTING || arenaOptional.get().getState() == QbwArenaState.TEAMSELECT) {
            QbwWorld qbwWorld;
            int i = 0;
            switch (p.getInventory().getItemInMainHand().getType()) {
                case SLIME_BALL:
                    /*
                    Leave
                     */
                    p.performCommand("qbw leave");
                    break;
                case WOOL:
                    /*
                    Set team
                     */
                    i = 0;
                    if (arenaOptional.get().getState() != QbwArenaState.TEAMSELECT) {
                        p.sendMessage(Qbw.getInstance().getPrefix() + Messages.WAIT_UNTIL_MAP_VOTE_FINSIHED.toString());
                        break;
                    }
                    qbwWorld = arenaOptional.get().getWorld();
                    if (qbwWorld == null) {
                        p.sendMessage(Qbw.getInstance().getPrefix() + Messages.UNKNOWN_ERROR.toString());
                        break;
                    }
                    if (qbwWorld.getTeams().isEmpty()) {
                        p.sendMessage(Qbw.getInstance().getPrefix() + Messages.UNKNOWN_ERROR.toString());
                        break;
                    }
                    HashMap<Integer, ItemStack> woolItems = new HashMap<>();
                    for (QbwTeam qbwTeam : qbwWorld.getTeams()) {
                        ItemBuilder builder = new ItemBuilder(new ItemStack(Material.WOOL));
                        builder.setName("§f§l" + StringUtils.capitalize(qbwTeam.getColor().name().toLowerCase()));
                        builder.setDurability((short) qbwTeam.getColor().getSubid());
                        if (qbwPlayer.getTeam() != null && qbwPlayer.getTeam().getColor() == qbwTeam.getColor()) {
                            builder.addEnchant(Enchantment.LUCK, 1, true);
                            builder.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        }
                        woolItems.put(i, builder.build());
                        i++;
                    }
                    new InventoryBuilder(woolItems, 27, "§a§lTeams", p, Optional.of(Material.BARRIER)) {
                        @Override
                        public void onClick(InventoryClickEvent e) {
                            e.setCancelled(true);
                            ItemStack itemStack = e.getCurrentItem();
                            Optional<QbwTeam> selectedTeamOptional = qbwWorld.getTeams().stream().filter(qbwTeam -> qbwTeam.getColor() == TeamColor.valueOf(itemStack.getItemMeta().getDisplayName().toUpperCase().replaceFirst("§f§l", ""))).findFirst();
                            selectedTeamOptional.ifPresent(qbwTeam -> Qbw.getInstance().getCustomEventHandler().fireEvent(new PlayerTeamSelectedEvent(p, qbwTeam, arenaOptional.get())));
                            close(true);
                        }
                    };
                    break;
                case BOOK:
                    /*
                    Votemap
                     */
                    i = 0;
                    if (arenaOptional.get().getState() != QbwArenaState.MAPVOTING) {
                        p.sendMessage(Qbw.getInstance().getPrefix() + Messages.NO_MAPVOTE.toString());
                        break;
                    }
                    List<QbwWorld> worldList = Arrays.asList(arenaOptional.get().getAvailableWorlds());
                    if (worldList.isEmpty()) {
                        arenaOptional.get().getPlayers().forEach(qbwPlayer1 -> {
                            QbwArenaUtil.broadcastToArena(arenaOptional.get(), Qbw.getInstance().getPrefix() + Messages.NO_MAPS_FOUND.toString());
                            qbwPlayer1.getPlayer().performCommand("qbw leave");
                        });
                        break;
                    }
                    HashMap<Integer, ItemStack> bookItems = new HashMap<>();

                    for (QbwWorld world : worldList) {
                        Material m;
                        switch (i) {
                            case 0:
                                m = Material.SAND;
                                break;
                            case 2:
                                m = Material.COOKIE;
                                break;
                            case 4:
                                m = Material.CLAY;
                                break;
                            case 6:
                                m = Material.DIRT;
                                break;
                            default:
                                m = Material.GRASS;
                                break;
                        }
                        ItemStack itemStack = new ItemStack(m);
                        bookItems.put(i, new ItemBuilder(itemStack).setName("§a" + world.getName()).addItemFlags(ItemFlag.HIDE_ENCHANTS).addEnchant(Enchantment.DURABILITY, 1, true).build());
                        i += 2;
                    }
                    new InventoryBuilder(bookItems, 9, "Mapvote", p, Optional.of(Material.BARRIER)) {
                        @Override
                        public void onClick(InventoryClickEvent e) {
                            e.setCancelled(true);
                            ItemStack itemStack = e.getCurrentItem();
                            p.performCommand("qbw votemap " + itemStack.getItemMeta().getDisplayName().replaceFirst("&a", ""));
                            close(true);
                        }
                    };
                    break;
                default:
                case AIR:
                    break;
            }
            e.setCancelled(true);
        }
    }
}
