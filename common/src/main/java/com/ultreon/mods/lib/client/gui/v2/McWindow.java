package com.ultreon.mods.lib.client.gui.v2;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.util.ScissorStack;
import net.minecraft.network.chat.Component;
import org.apache.commons.compress.utils.Lists;
import org.joml.Vector2d;

import java.awt.*;
import java.util.List;
import java.util.function.BooleanSupplier;

public class McWindow extends McContainer {
    private final List<Runnable> onClosed = Lists.newArrayList();
    private final List<BooleanSupplier> onClosing = Lists.newArrayList();
    McWindowManager wm;
    private boolean holdingTitle;
    private Vector2d holdingTitleFrom;
    private Vector2d holdingTitleSince;

    public McWindow(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);

        setBorder(new Insets(13, 1, 1, 1));
    }

    public void addOnClosedListener(Runnable listener) {
        this.onClosed.add(listener);
    }

    public void removeOnClosedListener(Runnable listener) {
        this.onClosed.remove(listener);
    }

    public void addOnClosingListener(BooleanSupplier listener) {
        this.onClosing.add(listener);
    }

    public void removeOnClosingListener(BooleanSupplier listener) {
        this.onClosing.remove(listener);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        var message = getMessage();

        if (width <= 0 || height <= 0)
            throw new IllegalStateException("Size must be positive, got: %d * %d".formatted(width, height));

        if (this.holdingTitle) {
            var fromX = (int) this.holdingTitleFrom.x;
            var fromY = (int) this.holdingTitleFrom.y;
            var sinceX = (int) this.holdingTitleSince.x;
            var sinceY = (int) this.holdingTitleSince.y;

            var deltaX = mouseX - fromX;
            var deltaY = mouseY - fromY;

            setX(sinceX + deltaX);
            setY(sinceY + deltaY);
        }

        var titleWidth = Math.min(this.width - 24, 150);
        var titleHeight = this.height - getBorder().top - 1;

        // Title frame.
        fill(poseStack, getX(), getY(), getX() + this.width + getBorder().left + getBorder().right, getY() + this.height + getBorder().top + getBorder().bottom, 0xff555555);
        fill(poseStack, getX() + getBorder().left, getY() + getBorder().top, getX() + this.width + getBorder().left, getY() + this.height + getBorder().top, 0xff333333);

        if (titleWidth > 0 && titleHeight > 0) {
            // Scissored title text/
            ScissorStack.pushScissor(getX() + 1, getY() + 1, titleWidth, titleHeight);
            {
                if (this.font.width(message) > titleWidth)
                    this.font.draw(poseStack, this.font.substrByWidth(message, titleWidth - 4).getString(), getX() + 3, getY() + 3, 0xffdddddd);
                else
                    this.font.draw(poseStack, message, getX() + 3, getY() + 3, 0xffdddddd);
            }
            ScissorStack.popScissor();
        }

        // Do content rendering.
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    public final void destroy() {
        this.onClosed.forEach(Runnable::run);
        if (this.wm != null) {
            this.wm.destroyWindow(this); // Destroy window in WM.
        }
    }

    public void close() {
        var doClose = false;

        for (var booleanSupplier : this.onClosing) {
            doClose |= booleanSupplier.getAsBoolean();
        }

        if (doClose) {
            destroy();
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.holdingTitle = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOverTitle(mouseX, mouseY)) {
            this.wm.moveToForeground(this);
            if (button == 0) {
                this.holdingTitleFrom = new Vector2d(mouseX, mouseY);
                this.holdingTitleSince = new Vector2d(getX(), getY());
                this.holdingTitle = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY);
    }

    private boolean isMouseOverTitle(double mouseX, double mouseY) {
        return mouseX > getX() + 2 && mouseX < getX() + width - 2 && mouseY > getY() + 2 && mouseY <= getY() + getBorder().top;
    }

    @Override
    protected final Insets getBorder() {
        return super.getBorder();
    }

    @Override
    protected final void setBorder(Insets border) {
        super.setBorder(border);
    }

    @Override
    protected final int getBorderColor() {
        return super.getBorderColor();
    }

    @Override
    protected final void setBorderColor(int borderColor) {
        super.setBorderColor(borderColor);
    }
}
