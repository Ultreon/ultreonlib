package com.ultreon.mods.lib.commons.lang.holders;

import com.ultreon.mods.lib.core.ComponentHolder;
import com.ultreon.mods.lib.core.TranslationHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface BaseHolder extends ComponentHolder, TranslationHolder {

    ResourceLocation getRegistryName();

    default String getName() {
        return getRegistryName().getPath();
    }

    @Override
    default Component getTranslation() {
        return Component.translatable(getTranslationId());
    }
}