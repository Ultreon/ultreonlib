package com.ultreon.mods.lib.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ultreon.mods.lib.UltreonLib;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

import static java.util.function.Function.identity;

public class ModCodecs {
    public static final Codec<WidgetSprites> WIDGET_SPRITES = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("enabled").forGetter(WidgetSprites::enabled),
            ResourceLocation.CODEC.fieldOf("disabled").forGetter(WidgetSprites::disabled),
            ResourceLocation.CODEC.fieldOf("enabled_focused").forGetter(WidgetSprites::enabledFocused),
            ResourceLocation.CODEC.fieldOf("disabled_focused").forGetter(WidgetSprites::disabledFocused)
    ).apply(instance, (resourceLocation, resourceLocation2, resourceLocation3, resourceLocation4) -> {
        UltreonLib.LOGGER.info("Registering widget sprites:\n {}\n {}\n {}\n {}", resourceLocation, resourceLocation2, resourceLocation3, resourceLocation4);
        return new WidgetSprites(resourceLocation, resourceLocation2, resourceLocation3, resourceLocation4);
    }));
    public static final Codec<List<ResourceLocation>> RESOURCE_LIST = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(ResourceLocation.CODEC).fieldOf("themes").forGetter(identity())
    ).apply(instance, identity()));
}
