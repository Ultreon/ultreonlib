package com.ultreon.mods.lib.core.silentlib.registry;

import com.ultreon.mods.lib.commons.lang.holders.EntityTypeHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

/**
 * @deprecated Removed
 */
@Deprecated
public class EntityTypeRegistryObject<ENTITY extends Entity> extends RegistryObjectWrapper<EntityType<ENTITY>> implements EntityTypeHolder {

    public EntityTypeRegistryObject(RegistryObject<EntityType<ENTITY>> registryObject) {
        super(registryObject);
    }

    @Nonnull
    @Override
    public EntityType<ENTITY> getEntityType() {
        return get();
    }
}