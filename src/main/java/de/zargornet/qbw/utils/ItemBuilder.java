package de.zargornet.qbw.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Helps to build items
 */
public class ItemBuilder {
    private ItemStack item;
    private ItemMeta meta;

    /***
     * Inits the class
     * @param item ItemStack which should be modified
     */
    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    /***
     * Set the name
     * @param name Name
     * @return this
     */
    public ItemBuilder setName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    /***
     * Adds an enchant
     * @param enchantment Enchantment type
     * @param level Level
     * @param ignoreLevelLimit Should Enchantment ignore the level limit?
     * @return this
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level, boolean ignoreLevelLimit) {
        meta.addEnchant(enchantment, level, ignoreLevelLimit);
        return this;
    }

    /***
     * Removes an enchant
     * @param enchantment Enchantment
     * @return this
     */
    public ItemBuilder removeEnchant(Enchantment enchantment) {
        if (meta.getEnchants().containsKey(enchantment))
            meta.removeEnchant(enchantment);
        return this;
    }

    /***
     * Adds flags to the item
     * @param flags Flags
     * @return this
     */
    public ItemBuilder addItemFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    /***
     * Remove item flags
     * @param flags Flags
     * @return this
     */
    public ItemBuilder removeItemFlags(ItemFlag... flags) {
        meta.removeItemFlags(flags);
        return this;
    }

    /***
     * Set item's lore
     * @param lore Lore
     * @return this
     */
    public ItemBuilder setLore(String[] lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    /***
     * Set the amount (ex. 64x dirt)
     * @param amount Amount
     * @return this
     */
    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    /***
     * Sets item's durability
     * @param durability Durability
     * @return this
     */
    public ItemBuilder setDurability(short durability) {
        item.setDurability(durability);
        return this;
    }

    /***
     * Set the type
     * @param type Type
     * @return this
     */
    public ItemBuilder setType(Material type) {
        item.setType(type);
        return this;
    }

    /***
     * Build the item with the meta
     * @return new ItemStack
     */
    public final ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}
