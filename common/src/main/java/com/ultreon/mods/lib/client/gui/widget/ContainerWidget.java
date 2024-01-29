package com.ultreon.mods.lib.client.gui.widget;

import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ContainerWidget extends UIWidget<ContainerWidget> implements WidgetsContainer {
    protected final List<ULibWidget> widgets = new ArrayList<>();
    private @Nullable ULibWidget focused;
    private boolean isDragging;

    public ContainerWidget(Component p_93633_) {
        super(p_93633_);
    }

    @Override
    public final boolean isDragging() {
        return this.isDragging;
    }

    @Override
    public final void setDragging(boolean dragging) {
        this.isDragging = dragging;
    }

    public void setFocused(@Nullable ULibWidget focused) {
        this.focused = focused;
    }

    @Override
    public void setFocused(GuiEventListener listener) {
        this.focused = (ULibWidget) listener;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        return WidgetsContainer.super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        return WidgetsContainer.super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        return WidgetsContainer.super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pAmountX, double pAmountY) {
        return WidgetsContainer.super.mouseScrolled(pMouseX, pMouseY, pAmountX, pAmountY);
    }

    @Override
    public void mouseMoved(double pMouseX, double pMouseY) {
        WidgetsContainer.super.mouseMoved(pMouseX, pMouseY);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        return WidgetsContainer.super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        return WidgetsContainer.super.keyReleased(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        return WidgetsContainer.super.charTyped(pCodePoint, pModifiers);
    }

    @NotNull
    @Override
    @Deprecated
    public Optional<GuiEventListener> getChildAt(double x, double y) {
        return Optional.ofNullable(this.getExactWidgetAt((int) x, (int) y));
    }

    @Override
    public @NotNull List<? extends ULibWidget> children() {
        return List.copyOf(widgets);
    }

    @Override
    public void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }

    @Nullable
    @Override
    public GuiEventListener getFocused() {
        return focused;
    }

    public ULibWidget getExactWidgetAt(int x, int y) {
        for (ULibWidget element : widgets) {
            if (element.isMouseOver(x, y)) {
                if (element instanceof ContainerWidget container) {
                    ULibWidget subWidget = container.getExactWidgetAt(x, y);
                    return subWidget == null ? element : subWidget;
                }
                return element;
            }
        }
        return null;
    }

    public <T extends ULibWidget> T add(T widget) {
        widgets.add(widget);
        widget.setParent(this);
        return widget;
    }

    @Override
    public void revalidate() {
        super.revalidate();

        this.revalidateWidgets();
    }

    protected void revalidateWidgets() {
        for (ULibWidget widget : widgets) {
            widget.revalidate();
        }
    }
}
