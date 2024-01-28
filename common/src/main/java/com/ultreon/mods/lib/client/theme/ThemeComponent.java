package com.ultreon.mods.lib.client.theme;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Function;

public sealed interface ThemeComponent permits ThemeGuiComponent, WidgetPlacement {
    <T extends ThemeGuiComponent> @NotNull T register(@NotNull ResourceLocation res, @NotNull T component);
    @Nullable ThemeComponent get(@NotNull ResourceLocation res);
    @Nullable ResourceLocation get(@NotNull ThemeGuiComponent component);
    @NotNull
    Collection<ThemeGuiComponent> getValues();
    Style getStyle(GlobalTheme currentTheme);

    static ThemeGuiComponent create(Function<GlobalTheme, Style> themeMapper) {
        return new ThemeGuiComponent() {
            @Override
            public Style getStyle(GlobalTheme currentTheme) {
                return themeMapper.apply(currentTheme);
            }
        };
    }
}
