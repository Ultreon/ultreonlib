package com.ultreon.mods.lib.core.common;

import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @deprecated use {@link ItemSelfHolder} instead.
 */
@SuppressWarnings("ALL")
@Deprecated
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemHolder implements com.ultreon.mods.lib.commons.lang.holders.ItemHolder {
    private final Item item;

    public ItemHolder(Item item) {
        this.item = item;
    }

    @NotNull
    @Override
    public Item asItem() {
        return item;
    }
}
