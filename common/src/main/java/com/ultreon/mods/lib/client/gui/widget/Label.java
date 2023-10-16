package com.ultreon.mods.lib.client.gui.widget;

import com.ultreon.mods.lib.client.theme.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class Label implements Renderable, Stylized {
    protected final Minecraft minecraft;
    protected final Font font;
    public int y;
    public int x;
    private Component message;
    private boolean shadow;
    private int color;
    private GlobalTheme globalTheme;
    private ThemeComponent component;

    public Label(int x, int y, Component message, GlobalTheme globalTheme, ThemeComponent component) {
        this(x, y, message, false, globalTheme, component);
    }

    public Label(int x, int y, Component message, boolean shadow, GlobalTheme globalTheme, ThemeComponent component) {
        this.x = x;
        this.y = y;
        this.component = component;
        this.color = getStyle().getTextColor().getRgb();
        this.message = message;
        this.minecraft = Minecraft.getInstance();
        this.font = minecraft.font;
        this.shadow = shadow;
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        if (this.shadow) {
            gfx.drawString(this.font, this.message, this.x, this.y, getColor());
        } else {
            gfx.drawString(this.font, this.message, this.x, this.y, getColor(), false);
        }
    }

    public Component getMessage() {
        return message;
    }

    public void setMessage(Component message) {
        this.message = message;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean hasShadow() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public void setMessage(String s) {
        setMessage(Component.literal(s));
    }

    @Override
    public ThemeComponent getThemeComponent() {
        return component;
    }

    @Override
    public void reloadTheme() {
        this.color = getStyle().getTextColor().getRgb();
    }
}
