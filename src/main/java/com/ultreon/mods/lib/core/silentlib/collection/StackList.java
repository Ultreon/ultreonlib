/*
 * QModLib - StackList
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ultreon.mods.lib.core.silentlib.collection;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * ArrayList designed to hold non-empty ItemStacks. Ignores any empty stacks that are added. Has
 * some convenience methods for selecting stacks.
 *
 * @since 3.0.0(?)
 */

/**
 * @deprecated Removed
 */
@Deprecated
public final class StackList extends ArrayList<ItemStack> {
    private StackList() {
    }

    /**
     * Create a StackList of the provided stacks, automatically removing any empty stacks.
     *
     * @param stacks The {@link ItemStack}s, may be empty but not null
     * @return A new list of all non-empty (valid) stacks
     */
    public static StackList of(ItemStack... stacks) {
        StackList newList = new StackList();
        Collections.addAll(newList, stacks);
        return newList;
    }

    /**
     * Create a StackList from the non-empty (valid) stacks in the provided container.
     *
     * @param container The {@link Container}
     * @return A new list of all non-empty stacks from the container
     * @since 3.0.6
     */
    public static StackList from(Container container) {
        StackList newList = new StackList();
        for (int i = 0; i < container.getContainerSize(); ++i) {
            newList.add(container.getItem(i));
        }
        return newList;
    }

    public static StackList from(Iterable<Tag> tagList) {
        StackList newList = new StackList();
        for (Tag nbt : tagList) {
            if (nbt instanceof CompoundTag) {
                newList.add(ItemStack.of((CompoundTag) nbt));
            }
        }
        return newList;
    }

    //region Convenience methods

    private static Predicate<ItemStack> itemClassMatcher(Class<?> itemClass) {
        return stack -> itemClass.isInstance(stack.getItem());
    }

    public ItemStack firstOfType(Class<?> itemClass) {
        return firstMatch(itemClassMatcher(itemClass));
    }

    public ItemStack firstMatch(Predicate<ItemStack> predicate) {
        return stream().filter(predicate).findFirst().orElse(ItemStack.EMPTY);
    }

    public ItemStack uniqueOfType(Class<?> itemClass) {
        return uniqueMatch(itemClassMatcher(itemClass));
    }

    public ItemStack uniqueMatch(Predicate<ItemStack> predicate) {
        return stream().filter(predicate).collect(Collectors.collectingAndThen(Collectors.toList(),
                list -> list.size() == 1 ? list.get(0) : ItemStack.EMPTY));
    }

    public Collection<ItemStack> allOfType(Class<?> itemClass) {
        return allMatches(itemClassMatcher(itemClass));
    }

    public Collection<ItemStack> allMatches(Predicate<ItemStack> predicate) {
        return stream().filter(predicate).collect(Collectors.toList());
    }

    public int countOfType(Class<?> itemClass) {
        return countOfMatches(itemClassMatcher(itemClass));
    }

    public int countOfMatches(Predicate<ItemStack> predicate) {
        return (int) stream().filter(predicate).count();
    }

    //endregion

    //region ArrayList overrides

    @Override
    public boolean add(ItemStack itemStack) {
        return !itemStack.isEmpty() && super.add(itemStack);
    }

    @Override
    public boolean addAll(Collection<? extends ItemStack> c) {
        boolean added = false;
        for (ItemStack stack : c) {
            if (!stack.isEmpty()) {
                added |= super.add(stack);
            }
        }
        return added;
    }

    @Override
    public boolean addAll(int index, Collection<? extends ItemStack> c) {
        boolean added = false;
        for (ItemStack stack : c) {
            if (!stack.isEmpty()) {
                super.add(index, stack);
                added = true;
            }
        }
        return added;
    }

    @Override
    public void add(int index, ItemStack element) {
        if (!element.isEmpty()) {
            super.add(index, element);
        }
    }

    //endregion
}
