package com.ultreon.mods.lib.util;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class ItemStackList extends ArrayList<ItemStack> {
    public static ItemStackList of(TagKey<Item> tagKey) {
        Optional<HolderSet.Named<Item>> tag = BuiltInRegistries.ITEM.getTag(tagKey);
        ItemStackList itemStacks = new ItemStackList();
        tag.ifPresent(holderSet -> holderSet.unwrap().ifRight(holders -> holders.forEach(itemHolder -> itemHolder.unwrap().ifRight(item -> itemStacks.add(new ItemStack(item))))));
        return itemStacks;
    }

    public static ItemStackList of(Block... blocks) {
        ItemStackList itemStacks = new ItemStackList();
        for (Block block : blocks)
            itemStacks.add(block.asItem().getDefaultInstance());
        return itemStacks;
    }

    public static ItemStackList of(BlockState... states) {
        ItemStackList itemStacks = new ItemStackList();
        for (BlockState state : states)
            itemStacks.add(state.getBlock().asItem().getDefaultInstance());
        return itemStacks;
    }

    public static ItemStackList of(Item... items) {
        ItemStackList itemStacks = new ItemStackList();
        for (Item item : items)
            itemStacks.add(item.getDefaultInstance());
        return itemStacks;
    }

    public static ItemStackList of(ItemStack... stacks) {
        ItemStackList itemStacks = new ItemStackList();
        itemStacks.addAll(Arrays.asList(stacks));
        return itemStacks;
    }

    public static ItemStackList of(Iterable<RegistrySupplier<Item>> registryObjects) {
        ItemStackList itemStacks = new ItemStackList();
        for (RegistrySupplier<Item> registryObject : registryObjects)
            itemStacks.add(registryObject.orElse(Items.AIR).getDefaultInstance());
        return itemStacks;
    }

    public static ItemStackList of(DeferredRegister<Item> deferredRegister) {
        ItemStackList itemStacks = new ItemStackList();
        for (RegistrySupplier<Item> registryObject : deferredRegister)
            itemStacks.add(registryObject.orElse(Items.AIR).getDefaultInstance());
        return itemStacks;
    }
}
