package com.ultreon.mods.lib.core.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PlayerUtils {
    /**
     * Gives an item to a player. Spawns an {@linkplain ItemEntity item entity} just above feet level.
     *
     * @param player the player
     * @param stack  the item stack
     */
    public static void giveItem(Player player, ItemStack stack) {
        ItemStack copy = stack.copy();
        if (!player.getInventory().add(copy)) {
            ItemEntity entityItem = new ItemEntity(player.level, player.getX(), player.getY(0.5), player.getZ(), copy);
            entityItem.setNoPickUpDelay();
            entityItem.setOwner(player.getUUID());
            player.level.addFreshEntity(entityItem);
        }
    }

    /**
     * Removes an item stack from the player's inventory, but only if it exists.
     *
     * @param player the player
     * @param stack  the item stack
     */
    public static void removeItem(Player player, ItemStack stack) {
        List<NonNullList<ItemStack>> inventories = ImmutableList.of(player.getInventory().items, player.getInventory().offhand, player.getInventory().armor);
        for (NonNullList<ItemStack> inv : inventories) {
            for (int i = 0; i < inv.size(); ++i) {
                if (stack == inv.get(i)) {
                    inv.set(i, ItemStack.EMPTY);
                    return;
                }
            }
        }
    }

    public static CompoundTag getPersistedCompound(Player player, String key) {
        return player.getPersistentData().getCompound(key);
    }
}
