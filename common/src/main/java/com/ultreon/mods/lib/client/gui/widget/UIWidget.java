package com.ultreon.mods.lib.client.gui.widget;

import com.google.common.base.Suppliers;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.mojang.blaze3d.platform.InputConstants;
import com.ultreon.mods.lib.client.HasContextMenu;
import com.ultreon.mods.lib.client.gui.Clickable;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.widget.menu.ContextMenu;
import com.ultreon.mods.lib.client.theme.Stylized;
import com.ultreon.mods.lib.client.theme.Theme;
import com.ultreon.mods.lib.client.theme.WidgetPlacement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings({"SameParameterValue", "unchecked"})
public abstract class UIWidget<T extends UIWidget<T>> extends AbstractWidget implements Clickable, Stylized, ULibWidget {
    protected final Minecraft minecraft;
    protected final Font font;
    private long multiClickDelay = 500L;
    private int textColor;
    private boolean usingCustomTextColor;
    private int clicks;
    private long lastClickTime;
    protected Supplier<Vector2i> positionGetter = Suppliers.memoize(() -> new Vector2i(0, 0));
    protected Supplier<Vector2i> sizeGetter = Suppliers.memoize(() -> new Vector2i(20, 20));
    private final Vector2i tmp = new Vector2i();
    private boolean dragging;
    @LazyInit
    Theme theme;
    private WidgetPlacement placement;
    private WidgetsContainer parent;

    public UIWidget(Component message) {
        super(0, 0, 20, 20, message);

        this.minecraft = Minecraft.getInstance();
        this.font = minecraft.font;
    }

    @Override
    public final boolean leftClick() {
        clicks = clicks + 1;
        lastClickTime = System.currentTimeMillis();
        return onLeftClick(clicks);
    }

    public boolean onLeftClick(int clicks) {
        return false;
    }

    public final boolean doubleClick() {
        clicks = getClicks() + 2;
        lastClickTime = System.currentTimeMillis();
        return this.onLeftClick(clicks);
    }

    /**
     * <p>NOTE: Override {@link #renderWidget(GuiRenderer, int, int, float)} instead.
     * This is going to be set to final in the future.</p>
     */
    @ApiStatus.NonExtendable
    public /*final*/ void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        ULibWidget.super.render(gfx, mouseX, mouseY, partialTicks);
    }

    public void render(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        this.renderWidget(renderer, mouseX, mouseY, partialTicks);
        this.renderForeground(renderer, mouseX, mouseY, partialTicks);
    }

    private void renderForeground(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        if (isHovered() && !isFocused()) {
            this.renderTooltip(renderer, mouseX, mouseY, partialTicks);
        }
    }

    public void renderTooltip(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        Tooltip tooltip = getTooltip();
        if (tooltip != null) {
            renderer.renderTooltip(tooltip, mouseX, mouseY);
        }
    }

    public void renderWidget(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public final boolean middleClick() {
        if (this instanceof TabCloseable tabCloseable) {
            tabCloseable.closeTab();
            return true;
        } else {
            return onMiddleClick();
        }
    }

    public boolean onMiddleClick() {
        return false;
    }

    @Override
    public final boolean rightClick() {
        if (this instanceof HasContextMenu hasContextMenu)
            return hasContextMenu.contextMenu(getX(), getY(), InputConstants.MOUSE_BUTTON_RIGHT) != null;
        else
            return onRightClick();
    }

    public boolean onRightClick() {
        return false;
    }

    public final int getClicks() {
        long timeSinceLastClick = getTimeSinceLastClick();
        clicks = timeSinceLastClick < multiClickDelay ? clicks : 0;
        return clicks;
    }

    @Override
    public boolean isFocused() {
        return GLFW.glfwGetWindowAttrib(this.minecraft.getWindow().getWindow(), GLFW.GLFW_FOCUSED) == GLFW.GLFW_TRUE && super.isFocused();
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }

    protected final long getTimeSinceLastClick() {
        return this.lastClickTime - System.currentTimeMillis();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            return switch (button) {
                case InputConstants.MOUSE_BUTTON_LEFT -> leftClick();
                case InputConstants.MOUSE_BUTTON_MIDDLE -> middleClick();
                case InputConstants.MOUSE_BUTTON_RIGHT -> rightClick();
                default -> false;
            };
        }

        return false;
    }

    public long getMultiClickDelay() {
        return multiClickDelay;
    }

    public void setMultiClickDelay(long multiClickDelay) {
        this.multiClickDelay = multiClickDelay;
    }

    @Override
    public WidgetPlacement getPlacement() {
        return placement;
    }

    public int getTextColor() {
        if (this.isUsingCustomTextColor()) {
            return textColor;
        }
        return this.getStyle().getTextColor().getRgb();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public boolean isUsingCustomTextColor() {
        return usingCustomTextColor;
    }

    public void setUsingCustomTextColor(boolean usingCustomTextColor) {
        this.usingCustomTextColor = usingCustomTextColor;
    }

    public void revalidate() {
        this.setPositionNow(this.positionGetter.get());
        this.setSizeNow(this.sizeGetter.get());
    }

    private void setSizeNow(Vector2i vector2i) {
        this.setWidth(vector2i.x());
        this.setHeight(vector2i.y());
    }

    public void setPositionNow(Vector2i vector2i) {
        this.setX(vector2i.x());
        this.setY(vector2i.y());
    }

    public T size(Supplier<Vector2i> size) {
        this.sizeGetter = size;
        return (T) this;
    }

    public T size(Function<Vector2i, Vector2i> size) {
        this.sizeGetter = () -> size.apply(this.tmp);
        return (T) this;
    }

    public T position(Supplier<Vector2i> position) {
        this.positionGetter = position;
        return (T) this;
    }

    public T position(Function<Vector2i, Vector2i> position) {
        this.positionGetter = () -> position.apply(this.tmp);
        return (T) this;
    }

    public @Nullable ContextMenu createContextMenu(int x, int y) {
        return null;
    }

    @Override
    public void renderScrollingString(@NotNull GuiRenderer gfx, int inset, int color) {
        ULibWidget.super.renderScrollingString(gfx, inset, color);
    }

    @Override
    public void renderScrollingString(@NotNull GuiRenderer renderer, int x, int y, int color) {
        ULibWidget.super.renderScrollingString(renderer, x, y, color);
    }

    @Override
    public boolean isDragging() {
        return dragging;
    }

    public void startDragging() {
        this.dragging = true;
    }

    public void stopDragging() {
        this.dragging = false;
    }

    @Override
    public Theme getTheme() {
        return theme;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public WidgetPlacement getThemeType() {
        return placement;
    }

    public void setPlacement(WidgetPlacement placement) {
        WidgetPlacement old = this.placement;
        this.placement = placement;
        if (old != this.placement) {
            this.reloadTheme();
        }
    }

    public void setParent(WidgetsContainer parent) {
        this.parent = parent;
    }

    public WidgetsContainer getParent() {
        return parent;
    }
}
