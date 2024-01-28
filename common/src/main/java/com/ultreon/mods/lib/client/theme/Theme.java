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
import com.ultreon.mods.lib.commons.Color;
import com.ultreon.mods.lib.util.ModCodecs;
import net.minecraft.client.gui.components.WidgetSprites;
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
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.ultreon.mods.lib.client.theme.DefaultTheme.*;
import static java.util.function.Function.identity;

@SuppressWarnings("SameParameterValue")
public class Theme extends Style {
    public static final ResourceLocation VANILLA_ID = UltreonLib.res("vanilla");
    public static final ResourceLocation LIGHT_ID = UltreonLib.res("light");
    public static final ResourceLocation DARK_ID = UltreonLib.res("dark");
    private static final List<Theme> THEMES = new ArrayList<>();
    private static final BiMap<ResourceLocation, Theme> THEME_REGISTRY = HashBiMap.create();
    public static final Codec<Theme> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(Theme::getTexture),
            Color.CODEC.fieldOf("text_color").forGetter(Theme::getTextColor),
            Color.CODEC.fieldOf("inactive_text_color").forGetter(Theme::getInactiveTextColor),
            Color.CODEC.fieldOf("accent_color").forGetter(Theme::getAccentColor),
            Color.CODEC.fieldOf("secondary_color").forGetter(Theme::getSecondaryColor),
            Codec.BOOL.fieldOf("dark").forGetter(Theme::isDark),
            Color.CODEC.fieldOf("title_color").forGetter(Theme::getTitleColor),
            Color.CODEC.fieldOf("header_color").forGetter(Theme::getHeaderColor),
            Color.CODEC.fieldOf("subtitle_color").forGetter(Theme::getSubTitleColor),
            Color.CODEC.fieldOf("border_color").forGetter(Theme::getBorderColor),
            Color.CODEC.fieldOf("inactive_border_color").forGetter(Theme::getInactiveBorderColor),
            ModCodecs.WIDGET_SPRITES.fieldOf("button_sprites").forGetter(Theme::getButtonSprites)
    ).apply(instance, (texture, textColor, inactiveTextColor, accentColor, secondaryColor, dark, titleColor, headerColor, subTitleColor, borderColor, inactiveBorderColor, sprites) -> {
        Theme theme = new Theme(textColor.getRgb(), inactiveTextColor.getRgb(), accentColor.getRgb(), secondaryColor.getRgb(), style -> {
            style.titleColor = titleColor;
            style.headerColor = headerColor;
            style.subTitleColor = subTitleColor;
            style.borderColor = borderColor;
            style.inactiveBorderColor = inactiveBorderColor;
        }, dark);
        theme.texture = texture;
        return theme;
    }));

    public static final Codec<List<ResourceLocation>> LIST_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(ResourceLocation.CODEC).fieldOf("themes").forGetter(identity())
    ).apply(instance, identity()));

    private final Style buttonStyle;
    private final boolean dark;
    private ResourceLocation texture;

    public Theme(int textColor, int inactiveTextColor, int accentColor, int secondaryColor) {
        this(textColor, inactiveTextColor, accentColor, secondaryColor, false);
    }

    public Theme(int textColor, int inactiveTextColor, int accentColor, int secondaryColor, boolean dark) {
        this(textColor, inactiveTextColor, accentColor, secondaryColor, buttonStyle -> {
        }, dark);
    }

    public Theme(int textColor, int inactiveTextColor, int accentColor, int secondaryColor, Consumer<Style> buttonStyle) {
        this(textColor, inactiveTextColor, accentColor, secondaryColor, buttonStyle, false);
    }

    public Theme(int textColor, int inactiveTextColor, int accentColor, int secondaryColor, Consumer<Style> buttonStyle, boolean dark) {
        this.textColor = Color.rgb(textColor);
        this.inactiveTextColor = Color.rgb(inactiveTextColor);
        this.titleColor = Color.rgb(textColor);
        this.headerColor = Color.rgb(textColor);
        this.subTitleColor = Color.rgb(inactiveTextColor);
        this.accentColor = Color.rgb(accentColor);
        this.secondaryColor = Color.rgb(secondaryColor);
        this.borderColor = Color.rgb(secondaryColor);
        this.inactiveBorderColor = Color.rgb(0x000000);
        this.buttonStyle = new Style(this);
        this.buttonStyle.textColor = this.textColor;
        this.buttonStyle.inactiveTextColor = this.inactiveTextColor;
        this.buttonStyle.titleColor = this.titleColor;
        this.buttonStyle.headerColor = this.headerColor;
        this.buttonStyle.subTitleColor = this.subTitleColor;
        this.buttonStyle.accentColor = this.accentColor;
        this.buttonStyle.secondaryColor = this.secondaryColor;
        this.buttonStyle.borderColor = this.borderColor;
        this.buttonStyle.inactiveBorderColor = this.inactiveBorderColor;
        buttonStyle.accept(this.buttonStyle);

        this.dark = dark;
    }

    Supplier<WidgetSprites> buttonSprites = () -> {
        throw new UnsupportedOperationException("Sprites not set");
    };

    public static @NotNull WidgetSprites createWidgetSprites(ResourceLocation id) {
        ResourceLocation disabledTex = new ResourceLocation(id.getNamespace(), id.getPath() + "_disabled");
        ResourceLocation highlightedTex = new ResourceLocation(id.getNamespace(), id.getPath() + "_highlighted");
        return new WidgetSprites(id, disabledTex, highlightedTex);
    }

    public static Optional<Theme> registerTheme(ResourceLocation resourceLocation, ResourceManager resourceManager) {
        if (resourceManager != null) {
            String path = "themes/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath() + ".json";
            ResourceLocation location = new ResourceLocation(UltreonLib.MOD_ID, path);
            Optional<Resource> themeResource = resourceManager.getResource(location);
            if (themeResource.isPresent()) {
                try (InputStream inputStream = themeResource.get().open()) {
                    Optional<Theme> theme = CODEC.parse(JsonOps.INSTANCE, UltreonLib.GSON.fromJson(new InputStreamReader(inputStream), JsonElement.class))
                            .resultOrPartial(s -> UltreonLib.LOGGER.warn("Failed to load themeResource: {}", s));
                    theme.ifPresent(THEMES::add);
                    theme.ifPresent(theme1 -> THEME_REGISTRY.put(resourceLocation, theme1));
                    return theme;
                } catch (IOException e) {
                    UltreonLib.LOGGER.error("Failed to load themes from {}", themeResource.get().source().packId(), e);
                }
            } else {
                UltreonLib.LOGGER.error("Theme {} not found", path);
            }
        }
        return Optional.empty();
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public WidgetSprites getButtonSprites() {
        return this.buttonSprites.get();
    }

    protected Theme withTextureLocation(ResourceLocation location) {
        this.texture = location;
        return this;
    }

    protected final Theme withTitleColor(int color) {
        this.titleColor = Color.rgb(color);
        return this;
    }

    protected final Theme withSubTitleColor(int color) {
        this.subTitleColor = Color.rgb(color);
        return this;
    }

    protected final Theme withHeaderColor(int color) {
        this.headerColor = Color.rgb(color);
        return this;
    }

    protected final Theme withBorderColor(int color) {
        this.borderColor = Color.rgb(color);
        return this;
    }

    protected final Theme withInactiveBorderColor(int color) {
        this.inactiveBorderColor = Color.rgb(color);
        return this;
    }

    protected final Theme withPrimaryColor(int color) {
        this.accentColor = Color.rgb(color);
        return this;
    }

    protected final Theme withSecondaryColor(int color) {
        this.secondaryColor = Color.rgb(color);
        return this;
    }

    public ResourceLocation getFrameSprite() {
        return new ResourceLocation(this.getTexture().getNamespace(), "frame/" + this.getTexture().getPath());
    }

    public ResourceLocation getInvertedFrameSprite() {
        return new ResourceLocation(this.getTexture().getNamespace(), "frame/" + this.getTexture().getPath() + "_inverted");
    }

    public ResourceLocation getBorderFrameSprite() {
        return new ResourceLocation(this.getTexture().getNamespace(), "frame/" + this.getTexture().getPath() + "_border");
    }

    public Style getButtonStyle() {
        return this.buttonStyle;
    }

    public boolean isDark() {
        return dark;
    }

    public void init() {
        buttonSprites = Suppliers.memoize(this::createButtonSprites);
    }

    private WidgetSprites createButtonSprites() {
        ResourceLocation id = this.texture;
        if (this == VANILLA) {
            return createWidgetSprites(new ResourceLocation("widget/button"));
        }
        return createWidgetSprites(new ResourceLocation(id.getNamespace(), "widget/button/" + id.getPath()));
    }

    public static List<Theme> getThemes() {
        return Collections.unmodifiableList(THEMES);
    }

    public static Theme getTheme(ResourceLocation location) {
        Theme theme = THEME_REGISTRY.get(location);
        if (theme == null) {
            throw new IllegalArgumentException("Theme not found: " + location);
        }
        return theme;
    }

    private void registerAs(ResourceLocation location) {
        this.texture = location;
        THEMES.add(this);
        THEME_REGISTRY.put(location, this);
        this.init();
    }

    public static CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        return Theme.prepare(resourceManager, backgroundExecutor)
                .thenCompose(preparationBarrier::wait)
                .thenAcceptAsync(arg2 -> Theme.registerThemes(arg2, reloadProfiler), gameExecutor);
    }

    private static void registerThemes(Map<ResourceLocation, Theme> themes, ProfilerFiller reloadProfiler) {
        reloadProfiler.push("Registering themes");
        VANILLA.registerAs(UltreonLib.res("vanilla"));
        LIGHT.registerAs(UltreonLib.res("light"));
        DARK.registerAs(UltreonLib.res("dark"));

        for (Map.Entry<ResourceLocation, Theme> entry : themes.entrySet()) {
            Theme theme = entry.getValue();
            reloadProfiler.push("Registering theme: " + theme.getId());
            UltreonLib.LOGGER.info("Registering theme: {}", theme.getId());
            theme.registerAs(entry.getKey());
            reloadProfiler.pop();
        }
        reloadProfiler.pop();
    }

    private static CompletableFuture<Map<ResourceLocation, Theme>> prepare(ResourceManager resourceManager, Executor backgroundExecutor) {
        return CompletableFuture.supplyAsync(() -> {
            List<Resource> resourceStack = resourceManager.getResourceStack(new ResourceLocation(UltreonLib.MOD_ID, "themes.json"));
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

            Map<ResourceLocation, Theme> themes = new HashMap<>();
            for (ResourceLocation resourceLocation : themeReferences) {
                UltreonLib.LOGGER.info("Registering theme: {}", resourceLocation);
                Optional<Theme> optionalTheme = Theme.registerTheme(resourceLocation, resourceManager);
                optionalTheme.ifPresent(theme -> {
                    themes.put(resourceLocation, theme);
                });
            }

            return themes;
        });
    }


    public ResourceLocation getId() {
        return Objects.requireNonNull(THEME_REGISTRY.inverse().get(this), () -> "Theme not present: " + this);
    }
}
