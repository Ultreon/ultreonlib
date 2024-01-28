package com.ultreon.mods.lib.client.gui.widget;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.theme.*;
import com.ultreon.mods.lib.commons.Color;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class PushButton extends TexturedButton<PushButton> implements Stylized {
    private Type type;
    private boolean usesTheme = true;

    public PushButton() {
        super(0, 0, 0, 0, null, null);

        this.type = Type.of(UltreonLib.getTheme().getContentTheme());
        this.setPlacement(WidgetPlacement.CONTENT);
    }

    @Override
    public void revalidate() {
        super.revalidate();

        if (this.type == null || this.useTheme()) {
            this.type = Type.of(UltreonLib.getTheme().get(this.getThemeType()));
        }
    }

    private boolean useTheme() {
        return this.usesTheme;
    }

    @Override
    @Deprecated(forRemoval = true)
    protected final ResourceLocation getWidgetsTexture() {
        return new ResourceLocation("");
    }

    @Override
    protected WidgetSprites getWidgetSprites() {
        return type.getSprites(this.getPlacement());
    }

    @Override
    public void reloadTheme() {
        if (this.type.usesTheme()) {
            GlobalTheme globalTheme = UltreonLib.getTheme();
            this.type = Type.of(globalTheme.getContentTheme());
            this.setTextColor(this.type.getTextColor(this.getPlacement()).getRgb());
        }
    }

    @Override
    public int getTextColor() {
        if (this.isUsingCustomTextColor())
            return super.getTextColor();

        return this.type.getTextColor(this.getPlacement()).getRgb();
    }

    public static PushButton of(Component text) {
        PushButton button = new PushButton();
        return button.message(text);
    }

    public static PushButton of(Component text, Callback<PushButton> onClick) {
        PushButton button = new PushButton();
        return button.message(text).callback(onClick);
    }

    public static PushButton of(Component text, Callback<PushButton> onClick, TooltipFactory<PushButton> onTooltip) {
        PushButton button = new PushButton();
        return button.message(text).callback(onClick).tooltip(onTooltip);
    }

    public static PushButton of(String text) {
        PushButton button = new PushButton();
        return button.message(Component.nullToEmpty(text));
    }

    public static PushButton of(String text, Callback<PushButton> onClick) {
        PushButton button = new PushButton();
        return button.message(Component.nullToEmpty(text)).callback(onClick);
    }

    public static PushButton of(String text, Callback<PushButton> onClick, TooltipFactory<PushButton> onTooltip) {
        PushButton button = new PushButton();
        return button.message(Component.nullToEmpty(text)).callback(onClick).tooltip(onTooltip);
    }

    public PushButton type(Type type) {
        this.usesTheme = false;
        this.type = type;
        return this;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        STANDARD((Theme) null),
        LIGHT(DefaultTheme.LIGHT),
        DARK(DefaultTheme.DARK),
        PRIMARY(UltreonLib.res("primary")),
        SUCCESS(UltreonLib.res("success")),
        WARNING(UltreonLib.res("warning")),
        DANGER(UltreonLib.res("danger")),
        ;

        private final Theme theme;
        private final WidgetSprites sprites;

        Type(ResourceLocation spriteRes) {
            this.sprites = Theme.createWidgetSprites(new ResourceLocation(spriteRes.getNamespace(), "widget/button/" + spriteRes.getPath()));
            this.theme = null;
        }

        Type(Theme theme) {
            this.sprites = null;
            this.theme = theme;
        }

        public boolean usesTheme() {
            return this == LIGHT || this == DARK || this == STANDARD;
        }

        public static Type of(Theme theme) {
            for (Type type : values()) {
                if (type.getTheme() == theme) {
                    return type;
                }
            }
            if (theme.getId().equals(Theme.LIGHT_ID)) return Type.LIGHT;
            if (theme.getId().equals(Theme.DARK_ID)) return Type.DARK;
            return Type.STANDARD;
        }

        public Color getTextColor(WidgetPlacement placement) {
            if (sprites != null) return Color.white;
            if (theme == null) return GlobalTheme.get().getTextColor(placement);
            return theme.getButtonStyle().getTextColor();

        }

        public WidgetSprites getSprites(WidgetPlacement placement) {
            if (sprites != null) return this.sprites;
            if (theme == null) return GlobalTheme.get().get(placement).getButtonSprites();
            return theme.getButtonSprites();
        }

        public Theme getTheme() {
            return theme;
        }
    }
}
