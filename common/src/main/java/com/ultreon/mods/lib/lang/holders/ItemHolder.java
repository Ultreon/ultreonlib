package com.ultreon.mods.lib.lang.holders;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface ItemHolder extends BaseHolder, net.minecraft.world.level.ItemLike {
    @Override
    Item asItem();

    default ItemStack asItemStack() {
        return asItemStack(1);
    }

    default ItemStack asItemStack(int size) {
        return new ItemStack(asItem(), size);
    }

    default boolean itemMatches(ItemStack otherStack) {
        return itemMatches(otherStack.getItem());
    }

    default boolean itemMatches(Item other) {
        return asItem() == other;
    }

    @Override
    default ResourceLocation getRegistryName() {
        return asItem().arch$registryName();
    }

    @Override
    default String getTranslationId() {
        return asItem().getDescriptionId();
    }
}