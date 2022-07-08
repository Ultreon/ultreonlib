package com.ultreon.modlib.silentlib.registry;

import com.ultreon.modlib.api.holders.EntityTypeHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

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