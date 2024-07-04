package dev.ultreon.mods.lib.mixin.common;

import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PanoramaRenderer.class)
public interface PanoramaRendererAccessor {
    @Accessor("PANORAMA_OVERLAY")
    static ResourceLocation getPanoramaOverlay() {
        throw new AssertionError();
    }
}
