package com.ultreon.mods.lib.core.util;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

/**
 * Item stack helper class.
 * Currently, used for save or load {@link ItemStack item stacks} to or from {@link CompoundTag}
 *
 * @author MrCrayfish
 */
@SuppressWarnings("unused")
public class ItemUtils {
    public static CompoundTag saveAllItems(String key, CompoundTag tag, NonNullList<ItemStack> list) {
        return saveAllItems(key, tag, list, true);
    }

    public static CompoundTag saveAllItems(String key, CompoundTag tag, NonNullList<ItemStack> list, boolean saveEmpty) {
        ListTag listTag = new ListTag();
        for (int i = 0; i < list.size(); ++i) {
            ItemStack stack = list.get(i);
            if (!stack.isEmpty()) {
                CompoundTag itemCompound = new CompoundTag();
                itemCompound.putByte("Slot", (byte) i);
                stack.save(itemCompound);
                listTag.add(itemCompound);
            }
        }
        if (!listTag.isEmpty() || saveEmpty) {
            tag.put(key, listTag);
        }
        return tag;
    }

    public static void loadAllItems(String key, CompoundTag tag, NonNullList<ItemStack> list) {
        ListTag listTag = tag.getList(key, Tag.TAG_COMPOUND);
        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag slotCompound = listTag.getCompound(i);
            int j = slotCompound.getByte("Slot") & 255;
            if (j < list.size()) {
                list.set(j, ItemStack.of(slotCompound));
            }
        }
    }
}
