package com.ultreon.mods.lib.core.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class InventoryUtils extends UtilityClass {
    public static Collection<Slot> createPlayerSlots(Inventory playerInventory, int startX, int startY) {
        Collection<Slot> list = new ArrayList<>();
        // Backpack
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                list.add(new Slot(playerInventory, x + y * 9 + 9, startX + x * 18, startY + y * 18));
            }
        }
        // Hotbar
        for (int x = 0; x < 9; ++x) {
            list.add(new Slot(playerInventory, x, 8 + x * 18, startY + 58));
        }
        return list;
    }

    public static boolean canItemsStack(ItemStack a, ItemStack b) {
        if (a.getItem() != b.getItem())
            return false;
        if (a.getTag() == null && b.getTag() != null)
            return false;
        return (a.getTag() == null || a.getTag().equals(b.getTag())) && a.areCapsCompatible(b);
    }

    public static ItemStack mergeItem(Container inventory, int slotStart, int slotEndExclusive, ItemStack stack) {
        if (inventory == null || stack.isEmpty()) {
            return stack;
        }

        // Merge into non-empty slots first
        for (int i = slotStart; i < slotEndExclusive && !stack.isEmpty(); ++i) {
            ItemStack inSlot = inventory.getItem(i);
            if (canItemsStack(inSlot, stack)) {
                int amountCanFit = Meth.min(inSlot.getMaxStackSize() - inSlot.getCount(), stack.getCount(), inventory.getMaxStackSize());
                inSlot.grow(amountCanFit);
                stack.shrink(amountCanFit);
                inventory.setItem(i, inSlot);
            }
        }

        // Fill empty slots next
        for (int i = slotStart; i < slotEndExclusive && !stack.isEmpty(); ++i) {
            if (inventory.getItem(i).isEmpty()) {
                int amountCanFit = Meth.min(stack.getCount(), inventory.getMaxStackSize());
                ItemStack toInsert = stack.copy();
                toInsert.setCount(amountCanFit);
                stack.shrink(amountCanFit);
                inventory.setItem(i, toInsert);
            }
        }

        return stack;
    }

    public static Collection<ItemStack> mergeItems(Container inventory, int slotStart, int slotEndExclusive, Collection<ItemStack> stacks) {
        if (inventory == null && stacks.isEmpty()) {
            return ImmutableList.of();
        }

        ImmutableList.Builder<ItemStack> leftovers = ImmutableList.builder();

        for (ItemStack stack : stacks) {
            stack = mergeItem(inventory, slotStart, slotEndExclusive, stack);

            // Failed to merge?
            if (!stack.isEmpty()) {
                leftovers.add(stack);
            }
        }

        return leftovers.build();
    }
}
