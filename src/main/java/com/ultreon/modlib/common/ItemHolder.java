package com.ultreon.modlib.common;

import com.ultreon.modlib.api.annotations.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("ALL")
@Deprecated
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemHolder implements com.ultreon.modlib.api.holders.ItemHolder {
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
