package com.ultreon.modlib.api.holders;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public interface ItemHolder extends BaseHolder, net.minecraft.world.level.ItemLike {

    @Nonnull
    Item asItem();

    @Nonnull
    default ItemStack asItemStack() {
        return asItemStack(1);
    }

    @Nonnull
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
        return asItem().getRegistryName();
    }

    @Override
    default String getTranslationId() {
        return asItem().getDescriptionId();
    }
}