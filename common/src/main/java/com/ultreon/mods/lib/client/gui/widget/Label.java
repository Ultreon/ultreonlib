package com.ultreon.mods.lib.client.gui.widget;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.commons.Color;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class Label extends UIWidget<Label> {
    public int y;
    public int x;
    private boolean shadow;
    private int color;
    public Label(int x, int y, Component message) {
        this(x, y, message, false);
    }

    public Label(int x, int y, Component message, boolean shadow) {
        super(message);
        this.x = x;
        this.y = y;
        this.color = getStyle().getTextColor().getRgb();
        this.shadow = shadow;
    }

    @Override
    public void renderWidget(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        renderer.textLeft(this.getMessage(), this.x, this.y, this.getColorRgb(), this.shadow);
    }

    public int getColorRgb() {
        return color;
    }

    public Color getColor() {
        return Color.rgb(color);
    }

    public void setColorRgb(int color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color.getRgb();
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

    public Label message(Component message) {
        this.setMessage(message);
        return this;
    }

    public Label color(int color) {
        this.color = color;
        return this;
    }

    public Label color(Color color) {
        this.color = color.getRgb();
        return this;
    }

    public Label shadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    @Override
    public void reloadTheme() {
        this.theme = UltreonLib.getTheme().get(getPlacement());
    }
}
