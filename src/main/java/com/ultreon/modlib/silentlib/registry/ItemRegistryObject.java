package com.ultreon.modlib.silentlib.registry;

import com.ultreon.modlib.api.holders.ItemHolder;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ItemRegistryObject<T extends Item> extends RegistryObjectWrapper<T> implements ItemHolder {
    public ItemRegistryObject(RegistryObject<T> item) {
        super(item);
    }

    @Override
    public @NotNull T asItem() {
        return registryObject.get();
    }
}
