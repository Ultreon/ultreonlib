package com.ultreon.mods.lib.client.gui.widget;

import com.google.errorprone.annotations.concurrent.LazyInit;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.Clickable;
import com.ultreon.mods.lib.client.gui.FrameType;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.widget.menu.ContextMenu;
import com.ultreon.mods.lib.client.theme.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

import java.util.function.Supplier;

public class TextBox extends EditBox implements Clickable, ULibWidget {
    protected static final ThemeGuiComponent WINDOW_THEME = WidgetPlacement.WINDOW.register(UltreonLib.res("text_box"), ThemeComponent.create(GlobalTheme::getContentButtonStyle));
    protected static final ThemeGuiComponent CONTENT_THEME = WidgetPlacement.CONTENT.register(UltreonLib.res("text_box"), ThemeComponent.create(GlobalTheme::getContentButtonStyle));
    protected Callback<String> callback;
    private TooltipFactory<TextBox> tooltipFactory;

    @LazyInit
    Theme theme;
    private Supplier<Vector2i> positionGetter;
    private Supplier<Vector2i> sizeGetter;
    private WidgetPlacement placement;
    private WidgetsContainer parent;

    protected TextBox(Component message, Callback<String> callback) {
        this(message, callback, (button) -> null);
    }

    protected TextBox(Component message, Callback<String> callback, TooltipFactory<TextBox> tooltipFactory) {
        super(Minecraft.getInstance().font, 0, 0, 0, 0, message);
        this.callback = callback;
        this.tooltipFactory = tooltipFactory;

        updateTooltip();

        super.setResponder(this::response);
    }

    @Override
    public void render(GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        renderer.renderContentFrame(this.getX(), this.getY(), this.getWidth(), this.getHeight(), FrameType.INVERTED);
    }

    @Override
    public boolean isUsingCustomTextColor() {
        return false;
    }

    @Override
    public boolean isDragging() {
        return false;
    }

    @Override
    public void revalidate() {
        if (this.positionGetter != null) this.setPosition(this.positionGetter.get());
        if (this.sizeGetter != null) this.setSize(this.sizeGetter.get());
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int i, int j, float f) {
        TextBox.super.renderWidget(gfx, i, j, f);
        ULibWidget.super.render(gfx, i, j, f);
    }

    public void updateTooltip() {
        setTooltip(tooltipFactory.create(this));
    }

    @Override
    public void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }

    public TextBox callback(Callback<String> onClick) {
        this.callback = onClick;
        return this;
    }

    public TextBox tooltip(TooltipFactory<TextBox> onTooltip) {
        this.setTooltipFactory(onTooltip);
        return this;
    }

    public TextBox message(Component text) {
        this.setMessage(text);
        return this;
    }

    public void setCallback(Callback<String> callback) {
        this.callback = callback;
    }

    public void setTooltipFactory(TooltipFactory<TextBox> tooltipFactory) {
        this.tooltipFactory = tooltipFactory;
    }

    @Override
    public int getTextColor() {
        if (isUsingCustomTextColor()) {
            return this.getCustomTextColor();
        }
        return (active ? getStyle().getTextColor() : getStyle().getInactiveTextColor()).getRgb();
    }

    @Override
    public Theme getTheme() {
        return theme;
    }

    @Override
    public void setPlacement(WidgetPlacement placement) {
        this.placement = placement;
    }

    @Override
    public void setParent(WidgetsContainer parent) {
        this.parent = parent;
    }

    @Override
    public ContextMenu createContextMenu(int x, int y) {
        return null;
    }

    private int getCustomTextColor() {
        return this.getStyle().getTextColor().getRgb();
    }

    private Style getStyle() {
        return UltreonLib.getTheme().getContentButtonStyle();
    }

    private void response(String message) {
        if (this.callback != null) {
            this.callback.call(message);
        }
    }

    public TextBox position(Supplier<Vector2i> position) {
        this.positionGetter = position;
        return this;
    }

    public TextBox size(Supplier<Vector2i> size) {
        this.sizeGetter = size;
        return this;
    }

    @Override
    public WidgetPlacement getPlacement() {
        return placement;
    }

    @Override
    public WidgetsContainer getParent() {
        return parent;
    }
}
