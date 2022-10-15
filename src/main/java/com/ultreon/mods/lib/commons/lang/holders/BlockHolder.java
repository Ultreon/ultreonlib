package com.ultreon.mods.lib.commons.lang.holders;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface BlockHolder extends ItemHolder {

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
        return ForgeRegistries.BLOCKS.getKey(asBlock());
    }

    @Override
    default String getTranslationId() {
        return asBlock().getDescriptionId();
    }

    static BlockHolder self(Block block) {
        return () -> block;
    }
}