package com.ultreon.mods.lib.core.common;

import com.ultreon.mods.lib.commons.lang.holders.ItemHolder;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings({"unused", "ClassCanBeRecord"})
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemSelfHolder implements ItemHolder {
    private final Item item;

    public ItemSelfHolder(Item item) {
        this.item = item;
    }

    @NotNull
    @Override
    public Item asItem() {
        return item;
    }
}
