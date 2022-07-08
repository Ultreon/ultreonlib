package com.ultreon.modlib.silentlib.util;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

/**
 * Adds some ways to access certain things (like registering items for use in the composter). I
 * assume Forge will eventually add their own hooks, but I can put up with access transformers until
 * then.
 */
public final class LibHooks {
    private LibHooks() {
        throw new IllegalAccessError("Utility class");
    }

    public static void registerCompostable(float chance, ItemLike item) {
        ComposterBlock.add(chance, item);
    }
}
