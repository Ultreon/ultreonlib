package com.ultreon.mods.lib.client.gui.widget;

import com.ultreon.libs.commons.v0.Color;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.client.theme.Stylized;
import com.ultreon.mods.lib.client.theme.Theme;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public non-sealed class Button extends TexturedButton implements Stylized {
    private Type type;

    public Button(int x, int y, int width, int height, Component title, CommandCallback onClick) {
        this(x, y, width, height, title, onClick, Type.of(UltreonLib.getTheme().getContentTheme()));
    }

    public Button(int x, int y, int width, int height, Component title, CommandCallback onClick, Type type) {
        super(x, y, width, height, title, onClick);
        this.type = type;
        this.setTextColor(type.textColor.getRgb());
    }

    public Button(int x, int y, int width, int height, Component title, CommandCallback onClick, TooltipFactory onTooltip) {
        this(x, y, width, height, title, onClick, onTooltip, Type.of(UltreonLib.getTheme().getContentTheme()));
    }

    public Button(int x, int y, int width, int height, Component title, CommandCallback onClick, TooltipFactory onTooltip, Type type) {
        super(x, y, width, height, title, onClick, onTooltip);
        this.type = type;
        this.setTextColor(type.textColor.getRgb());
    }

    public Button(Component title, CommandCallback onClick) {
        this(title, onClick, Type.of(UltreonLib.getTheme().getContentTheme()));
    }

    public Button(Component title, CommandCallback onClick, Type type) {
        super(0, 0, 0, 0, title, onClick);
        this.type = type;
        this.setTextColor(type.textColor.getRgb());
    }

    public Button(Component title, CommandCallback onClick, TooltipFactory onTooltip) {
        this(title, onClick, onTooltip, Type.of(UltreonLib.getTheme().getContentTheme()));
    }

    public Button(Component title, CommandCallback onClick, TooltipFactory onTooltip, Type type) {
        super(0, 0, 0, 0, title, onClick, onTooltip);
        this.type = type;
        this.setTextColor(type.textColor.getRgb());
    }

    @Override
    @Deprecated(forRemoval = true)
    protected final ResourceLocation getWidgetsTexture() {
        return new ResourceLocation("");
    }

    @Override
    protected WidgetSprites getWidgetSprites() {
        return type.sprites;
    }

    @Override
    public void reloadTheme() {
        if (this.type.usesTheme()) {
            GlobalTheme globalTheme = UltreonLib.getTheme();
            this.type = Type.of(globalTheme.getContentTheme());
            this.setTextColor(this.type.textColor.getRgb());
        }
    }

    @Override
    public int getTextColor() {
        if (this.isUsingCustomTextColor())
            return super.getTextColor();

        return this.type.textColor.getRgb();
    }

    public enum Type {
        VANILLA(Theme.VANILLA),
        LIGHT(Theme.LIGHT),
        DARK(Theme.DARK),
        PRIMARY(UltreonLib.res("primary")),
        SUCCESS(UltreonLib.res("success")),
        WARNING(UltreonLib.res("warning")),
        DANGER(UltreonLib.res("danger")),
        ;

        private final Color textColor;
        private final WidgetSprites sprites;
        private final RegistrySupplier<Theme> theme;

        Type(ResourceLocation spriteRes) {
            this.textColor = Color.white;
            this.sprites = Theme.createButtonSprites(new ResourceLocation(spriteRes.getNamespace(), "widget/button/" + spriteRes.getPath()));
            this.theme = Theme.DARK;
        }

        Type(RegistrySupplier<Theme> theme) {
            this.textColor = theme.get().getButtonStyle().getTextColor();
            this.sprites = theme.get().getButtonSprites();
            this.theme = theme;
        }

        public boolean usesTheme() {
            return this == LIGHT || this == DARK || this == VANILLA;
        }

        public static Type of(Theme theme) {
            for (Type type : values()) {
                if (type.theme.getOrNull() == theme) {
                    return type;
                }
            }
            if (!theme.isDark()) return Type.LIGHT;
            if (theme == Theme.VANILLA.get()) return Type.VANILLA;
            return Type.DARK;
        }
    }
}
