package com.ultreon.commons.lang.holders;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface BaseHolder extends ComponentHolder, TranslationHolder, RegistryEntryHolder {
    @Override
    ResourceLocation getRegistryName();

    default String getName() {
        return getRegistryName().getPath();
    }

    @Override
    default Component getTranslation() {
        return Component.literal(getTranslationId());
    }
}