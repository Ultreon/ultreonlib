package com.ultreon.mods.lib.client.theme;

import com.google.common.base.Suppliers;
import com.ultreon.libs.commons.v0.Color;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.registries.ModRegistries;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
public class Theme extends Style {
    public static final Theme VANILLA = register("vanilla", () -> new Theme(0xffffff, 0xa0a0a0, 0xffffff, 0x808080).withBorderColor(0xffffff).withTitleColor(0x111111).withHeaderColor(0x333333));
    public static final Theme LIGHT = register("light", () -> new Theme(0x808080, 0x949494, 0x19b2ff, 0xb7b7b7, buttonStyle -> buttonStyle.textColor = Color.white).withBorderColor(0xffffff).withInactiveBorderColor(0x868686).withSubTitleColor(0xb0b0b0).withHeaderColor(0x333333));
    public static final Theme DARK = register("dark", () -> new Theme(0xffffff, 0xA0A0A0, 0xffa54c, 0x313131, true).withSubTitleColor(0x808080));
    private final Style buttonStyle;
    private final boolean dark;

    public Theme(int textColor, int inactiveTextColor, int accentColor, int secondaryColor) {
        this(textColor, inactiveTextColor, accentColor, secondaryColor, false);
    }

    public Theme(int textColor, int inactiveTextColor, int accentColor, int secondaryColor,  boolean dark) {
        this(textColor, inactiveTextColor, accentColor, secondaryColor, buttonStyle -> {}, dark);
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

    private static <T extends Theme> Theme register(String path, Supplier<T> supplier) {
        return ModRegistries.THEME.register(UltreonLib.res(path), supplier.get());
    }

    private final Supplier<WidgetSprites> buttonSprites = Suppliers.memoize(() -> {
        ResourceLocation id = this.getId();
        return createButtonSprites(this == VANILLA ? new ResourceLocation("widget/button") : new ResourceLocation(id.getNamespace(), "widget/button/" + id.getPath()));
    });

    public static @NotNull WidgetSprites createButtonSprites(ResourceLocation id) {
        ResourceLocation disabledTex = new ResourceLocation(id.getNamespace(), id.getPath() + "_disabled");
        ResourceLocation highlightedTex = new ResourceLocation(id.getNamespace(), id.getPath() + "_highlighted");
        return new WidgetSprites(id, disabledTex, highlightedTex);
    }

    public ResourceLocation getId() {
        return ModRegistries.THEME.getId(this);
    }

    public WidgetSprites getButtonSprites() {
        return this.buttonSprites.get();
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
        return new ResourceLocation(this.getId().getNamespace(), "frame/" + this.getId().getPath());
    }

    @Deprecated
    public ResourceLocation getBorderFrameSprite() {
        return new ResourceLocation(this.getId().getNamespace(), "frame/" + this.getId().getPath() + "_border");
    }

    public Style getButtonStyle() {
        return this.buttonStyle;
    }

    public boolean isDark() {
        return dark;
    }
}
