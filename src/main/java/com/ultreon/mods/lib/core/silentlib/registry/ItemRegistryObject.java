package com.ultreon.mods.lib.core.silentlib.registry;

import com.ultreon.mods.lib.commons.lang.holders.ItemHolder;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

/**
 * @deprecated Removed
 */
@Deprecated
public class ItemRegistryObject<T extends Item> extends RegistryObjectWrapper<T> implements ItemHolder {
    public ItemRegistryObject(RegistryObject<T> item) {
        super(item);
    }

    @Override
    public @NotNull T asItem() {
        return registryObject.get();
    }
}
