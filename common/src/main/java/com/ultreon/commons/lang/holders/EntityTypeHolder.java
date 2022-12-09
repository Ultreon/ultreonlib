package com.ultreon.commons.lang.holders;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

@FunctionalInterface
public interface EntityTypeHolder extends BaseHolder {
    EntityType<?> getEntityType();

    @Override
    default ResourceLocation getRegistryName() {
        return getEntityType().arch$registryName();
    }

    @Override
    default Component getTranslation() {
        return getEntityType().getDescription();
    }

    @Override
    default String getTranslationId() {
        return getEntityType().getDescriptionId();
    }
}