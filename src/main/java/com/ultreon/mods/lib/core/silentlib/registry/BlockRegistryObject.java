package com.ultreon.mods.lib.core.silentlib.registry;

import com.ultreon.mods.lib.commons.lang.holders.BlockHolder;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

/**
 * @deprecated Removed
 */
@Deprecated
public class BlockRegistryObject<T extends Block> extends RegistryObjectWrapper<T> implements BlockHolder {
    public BlockRegistryObject(RegistryObject<T> block) {
        super(block);
    }

    @Override
    public @NotNull Block asBlock() {
        return registryObject.get();
    }
}
