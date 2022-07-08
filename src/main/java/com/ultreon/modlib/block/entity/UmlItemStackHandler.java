package com.ultreon.modlib.block.entity;

import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

public class UmlItemStackHandler extends ItemStackHandler {
    public UmlItemStackHandler() {
    }

    public UmlItemStackHandler(int size) {
        super(size);
    }

    public UmlItemStackHandler(NonNullList<ItemStack> stacks) {
        super(stacks);
    }

    public @NotNull ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(stacks, index, count);
    }

    public @NotNull ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(stacks, index);
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        stacks.set(slot, stack);
        if (stack.getCount() > getStackLimit(slot, stack)) {
            stack.setCount(getStackLimit(slot, stack));
        }
    }

    public Collection<ItemStack> getStacks() {
        return Collections.unmodifiableList(stacks);
    }

    public void clear() {
        stacks.clear();
    }
}
