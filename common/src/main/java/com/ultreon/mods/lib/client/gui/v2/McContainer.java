package com.ultreon.mods.lib.client.gui.v2;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.util.ScissorStack;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Contract;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class McContainer extends McComponent {

    private final List<McComponent> children = new ArrayList<>();
    private Insets border = new Insets(0, 0, 0, 0);
    private int borderColor = 0x00000000;
    private McComponent focused;
    public McContainer(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        var innerX = getX() + getBorder().left;
        var innerY = getY() + getBorder().top;
        ScissorStack.pushScissorTranslated(poseStack,
                innerX, innerY,
                getWidth() + getBorder().left, getHeight() + getBorder().top
        );
        poseStack.pushPose();
        poseStack.translate(innerX, innerY, 0);
        var translatedX = mouseX - getX() - getBorder().left;
        var translatedY = mouseY - getY() - getBorder().top;
        renderContents(poseStack, translatedX, translatedY, partialTicks);
        poseStack.popPose();
        ScissorStack.popScissor();
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    private void renderContents(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        for (var child : children()) {
            child.render(poseStack, mouseX, mouseY, partialTicks);
        }
    }

    @CanIgnoreReturnValue
    @Contract("_->param1")
    public <T extends McComponent> T add(T widget) {
        widget.parent = this;
        children.add(widget);
        return widget;
    }

    @CanIgnoreReturnValue
    public boolean remove(McComponent widget) {
        widget.parent = null;
        return children.remove(widget);
    }

    public void clearWidgets() {
        children.clear();
    }

    public Collection<McComponent> children() {
        return Collections.unmodifiableCollection(children);
    }
    
    protected Insets getBorder() {
        return border;
    }

    protected void setBorder(Insets border) {
        this.border = border;
    }

    protected int getBorderColor() {
        return borderColor;
    }

    protected void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public McComponent getFocused() {
        return focused;
    }

    public void setFocused(McComponent focused) {
        this.focused = focused;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isMouseOver(mouseX, mouseY)) {
            return false;
        }

        var translatedX = mouseX - getX() - border.left;
        var translatedY = mouseY - getY() - border.top;
        for (var child : children) {
            if (child.isMouseOver(translatedX, translatedY)) {
                child.mouseClicked(translatedX, translatedY, button);
                break;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.isHolding()) {
            var translatedX = mouseX - getX() - border.left;
            var translatedY = mouseY - getY() - border.top;
            for (var child : children) {
                if (child.isMouseOver(translatedX, translatedY)) {
                    child.mouseReleased(translatedX, translatedY, button);
                    break;
                }
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        var translatedX = mouseX - getX() - border.left;
        var translatedY = mouseY - getY() - border.top;
        var translatedDragX = dragX - getX() - border.left;
        var translatedDragY = dragY - getY() - border.top;
        for (var child : children) {
            if (child.isMouseOver(translatedX, translatedY)) {
                child.mouseDragged(translatedX, translatedY, button, translatedDragX, translatedDragY);
                break;
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (!isMouseOver(mouseX, mouseY)) {
            return false;
        }

        var translatedX = mouseX - getX() - border.left;
        var translatedY = mouseY - getY() - border.top;
        for (var child : children) {
            if (child.isMouseOver(translatedX, translatedY)) {
                child.mouseScrolled(translatedX, translatedY, delta);
                break;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (!isMouseOver(mouseX, mouseY)) {
            return;
        }

        var translatedX = mouseX - getX() - border.left;
        var translatedY = mouseY - getY() - border.top;
        for (var child : children) {
            if (child.isMouseOver(translatedX, translatedY)) {
                child.mouseMoved(translatedX, translatedY);
                break;
            }
        }
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (isFocused()) {
            final var focused = this.focused;
            if (focused != null) {
                focused.keyPressed(keyCode, scanCode, modifiers);
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (isFocused()) {
            final var focused = this.focused;
            if (focused != null) {
                focused.keyReleased(keyCode, scanCode, modifiers);
            }
        }
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (isFocused()) {
            final var focused = this.focused;
            if (focused != null) {
                focused.charTyped(codePoint, modifiers);
            }
        }
        return false;
    }
}
