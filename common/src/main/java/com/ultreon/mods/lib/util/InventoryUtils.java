package com.ultreon.mods.lib.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("unused")
public final class InventoryUtils extends UtilityClass {
    private InventoryUtils() {

    }

    public static int count(Player player, Item item) {
        final ItemStack stackItem = new ItemStack(item);

        int count = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (ItemStack.isSameItemSameTags(stack, stackItem)) {
                count += stack.getCount();
            }
        }
        return count;
    }

    /**
     * @return remainder count.
     */
    public static int remove(Player player, Item item, int count) {
        final ItemStack stackItem = new ItemStack(item);

        Inventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (ItemStack.isSameItemSameTags(stack, stackItem)) {
                int toRemove = Math.min(count, stack.getCount());
                stack.shrink(toRemove);
                if (stack.getCount() == 0) {
                    inventory.setItem(slot, ItemStack.EMPTY);
                }
                count -= toRemove;
            }

            if (count <= 0) {
                return 0;
            }
        }

        return count;
    }

    /**
     * @return true if 1 or more items are removed.
     */
    public static boolean removeAll(Player player, Item item) {
        final ItemStack stackItem = new ItemStack(item);
        boolean flag = false;

        Inventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (ItemStack.isSameItemSameTags(stack, stackItem)) {
                inventory.setItem(slot, ItemStack.EMPTY);
                flag = true;
            }
        }

        return flag;
    }

    /**
     * @return true if 1 or more items are removed.
     */
    public static boolean clear(Player player) {
        boolean flag = false;

        Inventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (!stack.isEmpty()) {
                inventory.setItem(slot, ItemStack.EMPTY);
                flag = true;
            }
        }

        return flag;
    }
}
