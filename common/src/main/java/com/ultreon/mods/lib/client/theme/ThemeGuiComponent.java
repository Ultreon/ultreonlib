package com.ultreon.mods.lib.client.theme;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.ultreon.mods.lib.UltreonLib;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public abstract non-sealed class ThemeGuiComponent implements ThemeComponent {
    private final BiMap<ResourceLocation, ThemeGuiComponent> registry = HashBiMap.create();
    private ThemeComponent parent;
    private WidgetPlacement root;

    @Override
    public <T extends ThemeGuiComponent> @NotNull T register(@NotNull ResourceLocation res, @NotNull T component) {
        if (this.registry.putIfAbsent(res, component) != component) {
            UltreonLib.LOGGER.warn("Possibly unintended component override of " + res);
            return component;
        }
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
    public abstract Style getStyle(GlobalTheme currentTheme);

    void setRootComponent(WidgetPlacement root) {
        this.parent = root;
        this.root = root;
    }

    private void setParent(ThemeGuiComponent parent) {
        this.parent = parent;
        this.root = parent.root;
    }

    public ThemeComponent getParent() {
        return this.parent;
    }

    public WidgetPlacement getRoot() {
        return this.root;
    }
}
