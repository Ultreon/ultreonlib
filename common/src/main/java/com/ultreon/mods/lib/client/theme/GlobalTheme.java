package com.ultreon.mods.lib.client.theme;

import com.ultreon.libs.commons.v0.Color;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.registries.ModRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * The theme enum class.
 * For Vanilla theme use {@link #VANILLA}.
 */
public class GlobalTheme {
    private static final List<GlobalTheme> THEMES = new ArrayList<>();
    private static int total = 0;

    public static final GlobalTheme VANILLA = registerVanilla("vanilla", GlobalTheme::new);

    public static final GlobalTheme LIGHT = register("light", () -> new GlobalTheme(Theme.LIGHT, Theme.LIGHT));
    public static final GlobalTheme MIX = register("mix", () -> new GlobalTheme(Theme.DARK, Theme.LIGHT));
    public static final GlobalTheme DARK = register("dark", () -> new GlobalTheme(Theme.DARK, Theme.DARK));
    private final Supplier<Theme> windowTheme;
    private final Supplier<Theme> menuTheme;

    private final Supplier<Theme> contentTheme;
    private final int ordinal;

    /**
     * Vanilla theme.
     *
     */
    private GlobalTheme() {
        this.windowTheme = () -> Theme.VANILLA;
        this.menuTheme = () -> Theme.VANILLA;
        this.contentTheme = () -> Theme.VANILLA;
        this.ordinal = total++;
        THEMES.add(this);
    }

    public GlobalTheme(Theme frame, Theme content) {
        this.windowTheme = () -> frame;
        this.menuTheme = () -> frame;
        this.contentTheme = () -> content;
        this.ordinal = total++;
        THEMES.add(this);
    }

    public GlobalTheme(Theme frame, Theme menu, Theme content) {
        this.windowTheme = () -> frame;
        this.menuTheme = () -> menu;
        this.contentTheme = () -> content;
        this.ordinal = total++;
        THEMES.add(this);
    }

    public GlobalTheme(Supplier<Theme> frame, Supplier<Theme> content) {
        this.windowTheme = frame;
        this.menuTheme = frame;
        this.contentTheme = content;
        this.ordinal = total++;
        THEMES.add(this);
    }

    public GlobalTheme(Supplier<Theme> frame, Supplier<Theme> menu, Supplier<Theme> content) {
        this.windowTheme = frame;
        this.menuTheme = menu;
        this.contentTheme = content;
        this.ordinal = total++;
        THEMES.add(this);
    }

    @ApiStatus.Internal
    private static <T extends GlobalTheme> GlobalTheme registerVanilla(String name, Supplier<T> supplier) {
        return ModRegistries.GLOBAL_THEME.register(new ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, name), supplier.get());
    }

    @ApiStatus.Internal
    private static <T extends GlobalTheme> GlobalTheme register(String name, Supplier<T> supplier) {
        return ModRegistries.GLOBAL_THEME.register(UltreonLib.res(name), supplier.get());
    }

    /**
     * Gets the theme from the id.
     *
     * @param id the id of the theme.
     * @return the theme or null if not found.
     */
    @Deprecated
    public static GlobalTheme fromId(String id) {
        return ModRegistries.GLOBAL_THEME.get(new ResourceLocation(id));
    }

    /**
     * Gets the theme from a resource location.
     *
     * @param location the resource location of the theme.
     * @return the theme or null if not found.
     */
    public static GlobalTheme fromLocation(ResourceLocation location) {
        return ModRegistries.GLOBAL_THEME.get(location);
    }

    /**
     * Gets the theme from the id.
     *
     * @param id           the id of the theme.
     * @param defaultGlobalTheme the default theme to return if not found.
     * @return the theme or defaultTheme if not found.
     */
    @Deprecated(forRemoval = true)
    public static GlobalTheme fromIdOr(String id, GlobalTheme defaultGlobalTheme) {
        return defaultGlobalTheme;
    }

    /**
     * Gets the theme from the location.
     *
     * @param location the location of the theme.
     * @param defaultTheme the default theme to return if not found.
     * @return the theme or defaultTheme if not found.
     */
    public static GlobalTheme fromLocationOr(ResourceLocation location, GlobalTheme defaultTheme) {
        if (location != null) {
            GlobalTheme globalTheme = ModRegistries.GLOBAL_THEME.get(location);
            if (globalTheme != null) return globalTheme;
        }
        if (defaultTheme != null) return defaultTheme;
        else return null;
    }

    /**
     * Gets the theme from the id.
     *
     * @param id the id of the theme.
     * @return the theme or the default theme if not found.
     */
    @Deprecated(forRemoval = true)
    public static @NotNull GlobalTheme fromIdOrDefault(String id) {
        return fromIdOr(id, VANILLA);
    }

    /**
     * Gets the theme from the location.
     *
     * @param location the location of the theme.
     * @return the theme or the default theme if not found.
     */
    public static @NotNull GlobalTheme fromLocationOrDefault(ResourceLocation location) {
        return fromLocationOr(location, VANILLA);
    }

    /**
     * Get the theme's id.
     *
     * @return the theme's id.
     */
    @Deprecated
    public String id() {
        return Objects.requireNonNull(ModRegistries.GLOBAL_THEME.getId(this), "Theme missing for " + this).getPath();
    }

    /**
     * Text color for inside the frame.
     * See {@link #getTitleColor(ThemeComponent)} for color of text in the title bar.
     *
     * @return the text color.
     */
    public Color getTextColor(ThemeComponent component) {
        return this.getStyle(component).getTextColor();
    }

    public Color getInactiveTextColor(ThemeComponent type) {
        return this.getStyle(type).getInactiveTextColor();
    }

    /**
     * Text color for inside the title bar.
     * See {@link #getTextColor(ThemeComponent)} for color of text inside the frame.
     *
     * @return the text color in the title bar.
     */
    public Color getTitleColor(ThemeComponent type) {
        return this.getStyle(type).getTitleColor();
    }

    protected Style getStyle(ThemeComponent component) {
        return component.getStyle(this);
    }

    /**
     * Get the button text color.
     *
     * @return the button text color.
     * @deprecated replace with {@link #getTextColor(ThemeComponent)}.
     */
    @Deprecated
    public int getButtonTextColor(ThemeComponent type) {
        return getTextColor(type).getRgb();
    }

    /**
     * Get the display name as a {@link Component chat component}.
     *
     * @return the display name.
     */
    public Component getDisplayName() {
        return Component.translatable(getDescriptionId());
    }

    /**
     * Get the translation's id for the display name.
     *
     * @return the translation id.
     */
    public String getDescriptionId() {
        if (getId().getNamespace().equals(UltreonLib.MOD_ID)) {
            return UltreonLib.MOD_ID +  ".theme." + getId().getPath();
        }
        return getId().getNamespace() + "." + UltreonLib.MOD_ID + ".theme." + getId().getPath();
    }

    /**
     * Get the theme after this one.
     *
     * @return the next theme.
     */
    public GlobalTheme next() {
        return THEMES.get((this.ordinal + 1) % GlobalTheme.total);
    }

    /**
     * Get the theme before this one.
     *
     * @return the previous theme.
     */
    public GlobalTheme previous() {
        return THEMES.get(((ordinal - 1 + GlobalTheme.total) % GlobalTheme.total));
    }

    public ResourceLocation getId() {
        return ModRegistries.GLOBAL_THEME.getId(this);
    }

    public Theme getWindowTheme() {
        return windowTheme.get();
    }

    public Theme getContentTheme() {
        return contentTheme.get();
    }

    public Theme getMenuTheme() {
        return menuTheme.get();
    }

    public Style getContentButtonStyle() {
        return this.contentTheme.get().getButtonStyle();
    }

    public Style getWindowButtonStyle() {
        return this.windowTheme.get().getButtonStyle();
    }

    public Style getMenuButtonStyle() {
        return this.menuTheme.get().getButtonStyle();
    }
}
