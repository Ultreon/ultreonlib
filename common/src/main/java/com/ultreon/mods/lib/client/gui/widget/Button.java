/*
 * Copyright (c) 2022. - Qboi SMP Development Team
 * Do NOT redistribute, or copy in any way, and do NOT modify in any way.
 * It is not allowed to hack into the code, use cheats against the code and/or compiled form.
 * And it is not allowed to decompile, modify or/and patch parts of code or classes or in full form.
 * Sharing this file isn't allowed either, and is hereby strictly forbidden.
 * Sharing decompiled code on social media or an online platform will cause in a report on that account.
 *
 * ONLY the owner can bypass these rules.
 */

package com.ultreon.mods.lib.client.gui.widget;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.Themed;
import com.ultreon.mods.lib.client.gui.Theme;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public non-sealed class Button extends TexturedButton implements Themed {
    private Type type;

    public Button(int x, int y, int width, int height, Component title, CommandCallback onClick) {
        this(x, y, width, height, title, onClick, Type.of(UltreonLib.getTheme()));
    }

    public Button(int x, int y, int width, int height, Component title, CommandCallback onClick, Type type) {
        super(x, y, width, height, title, onClick);
        this.type = type;
        setTextColor(type.textColor);
    }

    public Button(int x, int y, int width, int height, Component title, CommandCallback onClick, TooltipFactory onTooltip) {
        this(x, y, width, height, title, onClick, onTooltip, Type.of(UltreonLib.getTheme()));
    }

    public Button(int x, int y, int width, int height, Component title, CommandCallback onClick, TooltipFactory onTooltip, Type type) {
        super(x, y, width, height, title, onClick, onTooltip);
        this.type = type;
        setTextColor(type.textColor);
    }

    public Button(Component title, CommandCallback onClick) {
        this(title, onClick, Type.of(UltreonLib.getTheme()));
    }

    public Button(Component title, CommandCallback onClick, Type type) {
        super(0, 0, 0, 0, title, onClick);
        this.type = type;
        setTextColor(type.textColor);
    }

    public Button(Component title, CommandCallback onClick, TooltipFactory onTooltip) {
        this(title, onClick, onTooltip, Type.of(UltreonLib.getTheme()));
    }

    public Button(Component title, CommandCallback onClick, TooltipFactory onTooltip, Type type) {
        super(0, 0, 0, 0, title, onClick, onTooltip);
        this.type = type;
        setTextColor(type.textColor);
    }

    @Override
    protected final ResourceLocation getWidgetsTexture() {
        return type.res;
    }

    @Override
    public void reloadTheme() {
        if (type.usesTheme()) {
            Theme theme = UltreonLib.getTheme();
            this.type = Type.of(theme);
            setTextColor(type.textColor);
        }
    }

    public enum Type {
        LIGHT(UltreonLib.res("textures/gui/widgets/main/light.png"), Theme.LIGHT.getButtonTextColor()),
        NORMAL(new ResourceLocation("textures/gui/widgets.png"), Theme.NORMAL.getButtonTextColor()),
        DARK(UltreonLib.res("textures/gui/widgets/main/dark.png"), Theme.DARK.getButtonTextColor()),
        PRIMARY(UltreonLib.res("textures/gui/widgets/main/primary.png"), Theme.NORMAL.getButtonTextColor()),
        SUCCESS(UltreonLib.res("textures/gui/widgets/main/success.png"), Theme.NORMAL.getButtonTextColor()),
        WARNING(UltreonLib.res("textures/gui/widgets/main/warning.png"), Theme.NORMAL.getButtonTextColor()),
        DANGER(UltreonLib.res("textures/gui/widgets/main/danger.png"), Theme.NORMAL.getButtonTextColor()),
        ;

        private final ResourceLocation res;
        private final int textColor;

        Type(ResourceLocation res, int textColor) {
            this.res = res;
            this.textColor = textColor;
        }

        public boolean usesTheme() {
            return this == LIGHT || this == DARK || this == NORMAL;
        }

        public static Type of(Theme theme) {
            return switch (theme) {
                case LIGHT, MIX -> LIGHT;
                case DARK -> DARK;
                default -> NORMAL;
            };
        }

        public static Type ofTitleBar(Theme theme) {
            return switch (theme) {
                case LIGHT -> LIGHT;
                case DARK, MIX -> DARK;
                default -> NORMAL;
            };
        }
    }
}
