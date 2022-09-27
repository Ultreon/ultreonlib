package com.ultreon.mods.lib.commons.lang.holders;

import com.ultreon.mods.lib.core.api.ComponentHolder;
import com.ultreon.mods.lib.core.api.TranslationHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public interface BaseHolder extends ComponentHolder, TranslationHolder {

    ResourceLocation getRegistryName();

    default String getName() {
        return getRegistryName().getPath();
    }

    @Override
    default Component getComponent() {
        return new TranslatableComponent(getTranslationId());
    }
}