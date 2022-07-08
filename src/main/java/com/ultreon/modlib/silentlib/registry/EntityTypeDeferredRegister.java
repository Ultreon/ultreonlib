package com.ultreon.modlib.silentlib.registry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeDeferredRegister extends DeferredRegisterWrapper<EntityType<?>> {

    public EntityTypeDeferredRegister(String modid) {
        super(modid, ForgeRegistries.ENTITIES);
    }

    public <ENTITY extends Entity> EntityTypeRegistryObject<ENTITY> register(String name, EntityType.Builder<ENTITY> builder) {
        return register(name, () -> builder.build(name), EntityTypeRegistryObject::new);
    }

    public <ENTITY extends Entity> EntityTypeRegistryObject<ENTITY> register(String name, EntityType<ENTITY> type) {
        return register(name, () -> type, EntityTypeRegistryObject::new);
    }
}