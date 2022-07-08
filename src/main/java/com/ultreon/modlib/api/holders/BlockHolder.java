package com.ultreon.modlib.api.holders;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public interface BlockHolder extends ItemHolder, com.ultreon.modlib.silentlib.block.BlockLike {

    @Nonnull
    Block asBlock();

    default boolean blockMatches(ItemStack otherStack) {
        Item item = otherStack.getItem();
        return item instanceof BlockItem && blockMatches(((BlockItem) item).getBlock());
    }

    default boolean blockMatches(Block other) {
        return asBlock() == other;
    }

    @NotNull
    @Override
    default Item asItem() {
        return asBlock().asItem();
    }

    @Override
    default ResourceLocation getRegistryName() {
        //Make sure to use the block's registry name in case it somehow doesn't match
        return asBlock().getRegistryName();
    }

    @Override
    default String getTranslationId() {
        return asBlock().getDescriptionId();
    }
}