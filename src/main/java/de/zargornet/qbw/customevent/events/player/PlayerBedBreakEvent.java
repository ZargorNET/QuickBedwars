package de.zargornet.qbw.customevent.events.player;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * PlayerBedBreakEvent will be called if a player destroys a bed
 */
public class PlayerBedBreakEvent extends PlayerCustomEvent {
    private final Block block;

    public PlayerBedBreakEvent(Player player, Block block) {
        super(player);
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }
}
