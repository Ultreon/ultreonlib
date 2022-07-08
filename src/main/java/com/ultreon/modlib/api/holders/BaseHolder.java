package com.ultreon.modlib.api.holders;

import com.ultreon.modlib.api.ComponentHolder;
import com.ultreon.modlib.api.TranslationHolder;
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