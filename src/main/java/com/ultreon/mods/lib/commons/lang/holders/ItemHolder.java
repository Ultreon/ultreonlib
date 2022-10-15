package com.ultreon.mods.lib.commons.lang.holders;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

@FunctionalInterface
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
        return ForgeRegistries.ITEMS.getKey(asItem());
    }

    @Override
    default String getTranslationId() {
        return asItem().getDescriptionId();
    }
}