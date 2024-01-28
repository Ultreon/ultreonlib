package com.ultreon.mods.lib.client.theme;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.FrameType;
import com.ultreon.mods.lib.commons.Color;
import com.ultreon.mods.lib.util.IdRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.IdMapper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static java.util.function.Function.identity;

/**
 * The theme enum class.
 */
public class GlobalTheme {

    public static final Codec<GlobalTheme> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("window").forGetter(o -> o.getWindowTheme().getId()),
            ResourceLocation.CODEC.fieldOf("menu").forGetter(o -> o.getMenuTheme().getId()),
            ResourceLocation.CODEC.fieldOf("content").forGetter(o -> o.getContentTheme().getId()),
            ResourceLocation.CODEC.optionalFieldOf("font").forGetter(theme -> Optional.ofNullable(theme.font)),
            Codec.STRING.fieldOf("frame_type").forGetter(theme -> theme.frameType.name().toLowerCase(Locale.ROOT))
    ).apply(instance, (windowTheme, menuTheme, contentTheme, o, s) -> {
        GlobalTheme globalTheme = new GlobalTheme(
                Suppliers.memoize(() -> Theme.getTheme(windowTheme)),
                Suppliers.memoize(() -> Theme.getTheme(menuTheme)),
                Suppliers.memoize(() -> Theme.getTheme(contentTheme))
        );
        globalTheme.font = o.orElse(Minecraft.DEFAULT_FONT);
        globalTheme.frameType = FrameType.valueOf(s.toUpperCase(Locale.ROOT));
        return globalTheme;
    }));

    //region <Registration>
    private static final IdRegistry<GlobalTheme> THEMES = new IdRegistry<>();
    private static final BiMap<ResourceLocation, GlobalTheme> THEME_REGISTRY = HashBiMap.create();
    //endregion

    //region <Default Themes>
    public static final GlobalTheme VANILLA = GlobalTheme.defaultTheme("vanilla");
    private static final List<GlobalTheme> DEFAULT_THEMES = Collections.singletonList(VANILLA);
    //endregion

    public static final Codec<List<ResourceLocation>> LIST_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(ResourceLocation.CODEC).fieldOf("themes").forGetter(identity())
    ).apply(instance, identity()));
    static {
        THEME_REGISTRY.put(new ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, "vanilla"), GlobalTheme.VANILLA);
    }

    private static int total = 0;

    private final Supplier<Theme> windowTheme;

    private final Supplier<Theme> menuTheme;
    private final Supplier<Theme> contentTheme;
    private ResourceLocation font;
    private FrameType frameType = FrameType.NORMAL;
    /**
     * Vanilla theme.
     */
    private GlobalTheme() {
        this.windowTheme = () -> DefaultTheme.VANILLA;
        this.menuTheme = () -> DefaultTheme.VANILLA;
        this.contentTheme = () -> DefaultTheme.VANILLA;
        THEMES.add(this);
    }

    public GlobalTheme(Supplier<Theme> window, Supplier<Theme> content) {
        this.windowTheme = window;
        this.menuTheme = window;
        this.contentTheme = content;
    }

    public GlobalTheme(@NotNull Supplier<Theme> window, @NotNull Supplier<Theme> menu, @NotNull Supplier<Theme> content) {
        this.windowTheme = window;
        this.menuTheme = menu;
        this.contentTheme = content;
    }

    private static GlobalTheme defaultTheme(String id) {
        final ResourceLocation location = UltreonLib.res(id);
        return new GlobalTheme() {
            @Override
            public ResourceLocation getId() {
                return location;
            }
        };
    }

    /**
     * Gets the theme from a resource location.
     *
     * @param location the resource location of the theme.
     * @return the theme or null if not found.
     */
    public static GlobalTheme fromLocation(ResourceLocation location) {
        return THEME_REGISTRY.get(location);
    }

    /**
     * Gets the theme from the location.
     *
     * @param location     the location of the theme.
     * @param defaultTheme the default theme to return if not found.
     * @return the theme or defaultTheme if not found.
     */
    public static GlobalTheme fromLocationOr(ResourceLocation location, GlobalTheme defaultTheme) {
        GlobalTheme globalTheme = THEME_REGISTRY.get(location);
        if (defaultTheme != null && globalTheme == null) return defaultTheme;
        else if (defaultTheme == null && globalTheme == null) return VANILLA;
        else return globalTheme;
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

    public static GlobalTheme get() {
        return UltreonLib.getTheme();
    }

    public static void reload(ResourceManager resourceManager) {
        THEMES.clear();
        THEMES.add(VANILLA);

        for (Resource themes : resourceManager.getResourceStack(new ResourceLocation(UltreonLib.MOD_ID, "global_themes.json"))) {
            try (InputStream inputStream = themes.open()) {
                loadThemes(UltreonLib.GSON.fromJson(new InputStreamReader(inputStream), JsonElement.class));
            } catch (IOException e) {
                UltreonLib.LOGGER.error("Failed to load themes from {}", themes.source().packId(), e);
            }
        }

        total = THEMES.size();
    }

    private static void loadThemes(JsonElement themes) {
        LIST_CODEC.parse(JsonOps.INSTANCE, themes).resultOrPartial(s -> UltreonLib.LOGGER.warn("Failed to load themes: {}", s));
    }

    public static List<GlobalTheme> getThemes() {
        return Collections.unmodifiableList(THEMES);
    }

    public static List<GlobalTheme> getDefaultThemes() {
        return DEFAULT_THEMES;
    }

    public static Optional<GlobalTheme> registerTheme(ResourceLocation resourceLocation, ResourceManager resourceManager) {
        UltreonLib.LOGGER.debug("Registering theme {}", resourceLocation);
        if (resourceManager != null) {
            String path = "global_themes/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath() + ".json";
            ResourceLocation location = new ResourceLocation(UltreonLib.MOD_ID, path);
            Optional<Resource> theme = resourceManager.getResource(location);
            if (theme.isPresent()) {
                try (InputStream inputStream = theme.get().open()) {
                    Optional<GlobalTheme> globalTheme = CODEC.parse(JsonOps.INSTANCE, UltreonLib.GSON.fromJson(new InputStreamReader(inputStream), JsonElement.class))
                            .resultOrPartial(s -> UltreonLib.LOGGER.warn("Failed to load theme: {}", s));
                    globalTheme.ifPresent(THEMES::add);
                    globalTheme.ifPresent(theme1 -> THEME_REGISTRY.put(resourceLocation, theme1));
                    return globalTheme;
                } catch (IOException e) {
                    UltreonLib.LOGGER.error("Failed to load global themes from {}", theme.get().source().packId(), e);
                }
            } else {
                UltreonLib.LOGGER.error("Global theme {} not found", path);
            }
        }
        return Optional.empty();
    }

    public static void clear() {
        THEMES.clear();
        THEMES.add(VANILLA);
        total = 1;
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
            return UltreonLib.MOD_ID + ".theme." + getId().getPath();
        }
        return UltreonLib.MOD_ID + "." + UltreonLib.MOD_ID + ".theme." + getId().getNamespace() + "." + getId().getPath();
    }

    /**
     * Get the theme after this one.
     *
     * @return the next theme.
     */
    public GlobalTheme next() {
        return THEMES.get((this.ordinal() + 1) % THEMES.size());
    }

    private int ordinal() {
        return THEMES.getId(this);
    }

    /**
     * Get the theme before this one.
     *
     * @return the previous theme.
     */
    public GlobalTheme previous() {
        return THEMES.get(((ordinal() - 1 + GlobalTheme.total) % GlobalTheme.total));
    }

    public ResourceLocation getId() {
        return Objects.requireNonNull(THEME_REGISTRY.inverse().get(this), () -> "Theme not present: " + this);
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

    public ResourceLocation font() {
        return this.font == null ? Minecraft.DEFAULT_FONT : this.font;
    }

    public Theme get(WidgetPlacement widgetPlacement) {
        return switch (widgetPlacement) {
            case WINDOW -> this.windowTheme.get();
            case CONTENT -> this.contentTheme.get();
            case MENU -> this.menuTheme.get();
        };
    }

    public FrameType getFrameType() {
        return this.frameType;
    }

    public void init() {
        this.windowTheme.get().init();
        this.contentTheme.get().init();
        this.menuTheme.get().init();
    }

    public static CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        return prepare(resourceManager, backgroundExecutor)
                .thenCompose(preparationBarrier::wait)
                .thenAcceptAsync(arg2 -> registerThemes(arg2, reloadProfiler), gameExecutor);
    }

    private static void registerThemes(Map<ResourceLocation, GlobalTheme> globalThemes, ProfilerFiller reloadProfiler) {
        if (reloadProfiler != null) {
            reloadProfiler.push("Register Themes");

            THEMES.clear();
            THEMES.add(VANILLA);

            THEME_REGISTRY.clear();
            for (GlobalTheme defaultTheme : DEFAULT_THEMES) {
                THEMES.add(defaultTheme);
                THEME_REGISTRY.put(defaultTheme.getId(), defaultTheme);
            }
            globalThemes.forEach((resourceLocation, theme) -> {
                THEMES.add(theme);
                THEME_REGISTRY.put(resourceLocation, theme);
            });

            reloadProfiler.pop();
        }
    }

    private static CompletableFuture<Map<ResourceLocation, GlobalTheme>> prepare(ResourceManager resourceManager, Executor backgroundExecutor) {
        return CompletableFuture.supplyAsync(() -> {
            List<Resource> resourceStack = resourceManager.getResourceStack(new ResourceLocation(UltreonLib.MOD_ID, "global_themes.json"));
            List<ResourceLocation> themeReferences = new ArrayList<>();
            GlobalTheme.clear();

            for (Resource resource : resourceStack) {
                try (InputStream inputStream = resource.open()) {
                    DataResult<Pair<List<ResourceLocation>, JsonElement>> decode = GlobalTheme.LIST_CODEC.decode(JsonOps.INSTANCE, UltreonLib.GSON.fromJson(new InputStreamReader(inputStream), JsonElement.class));
                    if (decode.result().isPresent()) {
                        themeReferences.addAll(decode.result().get().getFirst());
                    } else {
                        UltreonLib.LOGGER.error("Failed to load themes from {}: Invalid json", resource.source().packId());
                    }
                } catch (IOException e) {
                    UltreonLib.LOGGER.error("Failed to load themes from {}", resource.source().packId(), e);
                }
            }

            Map<ResourceLocation, GlobalTheme> themes = new HashMap<>();
            for (ResourceLocation themeRef : themeReferences) {
                UltreonLib.LOGGER.info("Registering global theme: {}", themeRef);
                Optional<GlobalTheme> optionalTheme = GlobalTheme.registerTheme(themeRef, resourceManager);
                optionalTheme.ifPresent(globalTheme -> themes.put(themeRef, globalTheme));
            }

            return themes;
        }, backgroundExecutor);
    }
}
