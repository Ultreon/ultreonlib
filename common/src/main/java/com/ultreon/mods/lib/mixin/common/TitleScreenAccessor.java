package com.ultreon.mods.lib.mixin.common;

import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TitleScreen.class)
public interface TitleScreenAccessor {
    @Accessor("PANORAMA_OVERLAY")
    static ResourceLocation getPanoramaOverlay() {
        throw new AssertionError();
    }
}
