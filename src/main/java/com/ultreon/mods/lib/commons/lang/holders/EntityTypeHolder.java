package com.ultreon.mods.lib.commons.lang.holders;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import javax.annotation.Nonnull;

public interface EntityTypeHolder extends BaseHolder {

    @Nonnull
    EntityType<?> getEntityType();

    @Override
    default ResourceLocation getRegistryName() {
        return getEntityType().getRegistryName();
    }

    @Override
    default Component getComponent() {
        return getEntityType().getDescription();
    }

    @Override
    default String getTranslationId() {
        return getEntityType().getDescriptionId();
    }
}