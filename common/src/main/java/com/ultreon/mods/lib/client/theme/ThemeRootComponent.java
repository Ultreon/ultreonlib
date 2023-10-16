package com.ultreon.mods.lib.client.theme;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.ultreon.mods.lib.UltreonLib;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Function;

public enum ThemeRootComponent implements ThemeComponent {
    WINDOW(GlobalTheme::getWindowTheme),
    MENU(GlobalTheme::getMenuTheme),
    CONTENT(GlobalTheme::getContentTheme);

    private final BiMap<ResourceLocation, ThemeGuiComponent> registry = HashBiMap.create();
    private final Function<GlobalTheme, Style> themeMapper;

    ThemeRootComponent(Function<GlobalTheme, Style> themeMapper) {
        this.themeMapper = themeMapper;
    }

    @Override
    public <T extends ThemeGuiComponent> @NotNull T register(@NotNull ResourceLocation res, @NotNull T component) {
        if (this.registry.putIfAbsent(res, component) != component) {
            UltreonLib.LOGGER.warn("Possibly unintended component override of " + res);
            return component;
        }
        component.setRootComponent(this);
        return component;
    }

    @Override
    public @Nullable ThemeComponent get(@NotNull ResourceLocation res) {
        return this.registry.get(res);
    }

    @Override
    public @Nullable ResourceLocation get(@NotNull ThemeGuiComponent component) {
        return this.registry.inverse().get(component);
    }

    @Override
    public @NotNull Collection<ThemeGuiComponent> getValues() {
        return this.registry.values();
    }

    @Override
    public Style getStyle(GlobalTheme currentTheme) {
        return this.themeMapper.apply(currentTheme);
    }
}
