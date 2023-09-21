package com.ultreon.mods.lib.client.gui.v2;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.util.ScissorStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;
import org.joml.Vector2i;

import java.awt.*;
import java.util.List;
import java.util.function.BooleanSupplier;

public class McWindow extends McContainer {
    private static final ResourceLocation SHADOW = UltreonLib.res("textures/gui/desktop/window_shadow.png");
    private final List<Runnable> onClosed = Lists.newArrayList();
    private final List<BooleanSupplier> onClosing = Lists.newArrayList();
    boolean topMost;
    boolean bottomMost;
    boolean undecorated;
    McWindowManager wm;
    private boolean holdingTitle;
    private Vector2d holdingTitleFrom;
    private Vector2d holdingTitleSince;
    private boolean maximized;
    private boolean minimized;
    private Vector2i previousSize;
    private Vector2i previousPos;

    public McWindow(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);

        this.previousSize = new Vector2i(width, height);
        this.previousPos = new Vector2i(x, y);

        setBorderColor(0xff555555);

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
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        var message = getMessage();

        if (width <= 0 || height <= 0)
            throw new IllegalStateException("Size must be positive, got: %d * %d".formatted(width, height));

        if (maximized) {
            setX(-getBorder().left);
            setY(-1);
            setWidth(wm.getWidth());
            setHeight(wm.getHeight() - getBorder().top + 1);
        } else {
            fixPosition();
        }

        var titleWidth = Math.min(this.getWidth() - 24, 150);
        var titleHeight = this.getHeight() - getBorder().top - 1;

        // Shadow
        RenderSystem.setShaderTexture(0, SHADOW);
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 0.5f);
        {
            // Corners
            gfx.blit(SHADOW, getX() - 9, getY() - 9, 9, 9, 0, 0, 9, 9, 256, 256);
            gfx.blit(SHADOW, getX() - 9, getY() + getHeight() + getBorder().top + getBorder().bottom, 9, 9, 0, 11, 9, 9, 256, 256);
            gfx.blit(SHADOW, getX() + getWidth() + getBorder().left + getBorder().right, getY() - 9, 9, 9, 11, 0, 9, 9, 256, 256);
            gfx.blit(SHADOW, getX() + getWidth() + getBorder().left + getBorder().right, getY() + getHeight() + getBorder().top + getBorder().bottom, 9, 9, 11, 11, 9, 9, 256, 256);

            // Vertical Parts
            gfx.blit(SHADOW, getX() - 9, getY(), 9, getHeight() + getBorder().top + getBorder().bottom, 0, 10, 9, 1, 256, 256);
            gfx.blit(SHADOW, getX() + getWidth() + getBorder().left + getBorder().right, getY(), 9, getHeight() + getBorder().top + getBorder().bottom, 10, 10, 9, 1, 256, 256);

            // Horizontal Parts
            gfx.blit(SHADOW, getX(), getY() - 9, getWidth() + getBorder().left + getBorder().right, 9, 10, 0, 1, 9, 256, 256);
            gfx.blit(SHADOW, getX(), getY() + getHeight() + getBorder().top + getBorder().bottom, getWidth() + getBorder().left + getBorder().right, 9, 10, 10, 1, 9, 256, 256);
        }
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.disableBlend();

        // Title frame.
        gfx.fill(getX(), getY(), getX() + this.getWidth() + getBorder().left + getBorder().right, getY() + this.getHeight() + getBorder().top + getBorder().bottom, getBorderColor());
        gfx.fill(getX() + getBorder().left, getY() + getBorder().top, getX() + this.getWidth() + getBorder().left, getY() + this.getHeight() + getBorder().top, 0xff333333);

        if (!isUndecorated()) {
            if (titleWidth > 0 && titleHeight > 0) {
                // Scissored title text/
                ScissorStack.pushScissor(getX() + 1, getY() + 1, titleWidth, titleHeight);
                {
                    if (this.font.width(message) > titleWidth)
                        gfx.drawString(this.font, this.font.substrByWidth(message, titleWidth - 4).getString(), getX() + 3, getY() + 3, 0xffdddddd, false);
                    else
                        gfx.drawString(this.font, message, getX() + 3, getY() + 3, 0xffdddddd, false);
                }
                ScissorStack.popScissor();
            }

            RenderSystem.enableBlend();
            int tcr = getX() + getWidth() + 1; // Title Controls Right
            if (isMouseOver(mouseX, mouseY, tcr - 23, getY() + 1, 23, 12)) {
                gfx.fill(tcr - 23, getY() + 1, tcr, getY() + 13, 0x33ffffff);
                drawCenteredStringWithoutShadow(gfx, font, "×", tcr - 11, getY() + 3, 0xffffffff);
            } else drawCenteredStringWithoutShadow(gfx, font, "×", tcr - 11, getY() + 3, 0xffffffff);

            if (isMouseOver(mouseX, mouseY, tcr - 46, getY() + 1, 23, 12)) {
                gfx.fill(tcr - 46, getY() + 1, tcr - 23, getY() + 13, 0x33ffffff);
                drawCenteredStringWithoutShadow(gfx, font, "□", tcr - 34, getY() + 3, 0xffffffff);
            } else drawCenteredStringWithoutShadow(gfx, font, "□", tcr - 34, getY() + 3, 0xffffffff);

            if (isMouseOver(mouseX, mouseY, tcr - 69, getY() + 1, 23, 12)) {
                gfx.fill(tcr - 69, getY() + 1, tcr - 46, getY() + 13, 0x33ffffff);
                drawCenteredStringWithoutShadow(gfx, font, "-", tcr - 57, getY() + 3, 0xffffffff);
            } else drawCenteredStringWithoutShadow(gfx, font, "-", tcr - 57, getY() + 3, 0xffffffff);
            RenderSystem.disableBlend();
        }

        // Do content rendering.
        super.render(gfx, mouseX, mouseY, partialTicks);
    }

    void handleSetActive() {
        this.setBorderColor(wm.getWindowActiveColor());
    }

    void handleSetInactive() {
        this.setBorderColor(wm.getWindowInactiveColor());
    }

    private boolean isMouseOver(int mouseX, int mouseY, int x, int y, int width, int height) {
        return this.active && this.visible && mouseX >= (double)x && mouseY >= (double)y && mouseX < (double)(x + width) && mouseY < (double)(y + height);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return this.active && this.visible && mouseX >= (double)this.getX() && mouseY >= (double)this.getY() && mouseX < (double)(this.getX() + this.getWidth() + getBorder().left + getBorder().right) && mouseY < (double)(this.getY() + this.getHeight() + getBorder().top + getBorder().bottom);
    }

    private void fixPosition() {
        int wmWidth = wm.getWidth();
        int wmHeight = wm.getHeight();

        // Fix position when the window is outside of the WM.
        Insets border = getBorder();
        Insets wmBorder = wm.getBorder();

        if (getX() + getWidth() > wmWidth - border.left - border.right - wmBorder.left - wmBorder.right)
            setX(wmWidth - getWidth() - border.left - border.right - wmBorder.left - wmBorder.right);
        if (getY() > wmHeight - border.top - wmBorder.top - wmBorder.bottom)
            setY(wmHeight - border.top - wmBorder.top - wmBorder.bottom);

        // Fix position when the position is negative.
        if (getX() < wmBorder.left) setX(wmBorder.left);
        if (getY() < wmBorder.top) setY(wmBorder.top);
    }

    public final void destroy() {
        this.onClosed.forEach(Runnable::run);
        if (this.wm != null) {
            this.wm.destroyWindow(this); // Destroy window in WM.
        }
    }

    public void close() {
        var doClose = true;

        for (var booleanSupplier : this.onClosing) {
            doClose &= booleanSupplier.getAsBoolean();
        }

        if (doClose) {
            destroy();
        }
    }

    public void maximize() {
        previousPos = new Vector2i(getX(), getY());
        previousSize = new Vector2i(width, height);
        this.maximized = true;
    }

    public void minimize() {
        this.minimized = true;
    }

    public void restore() {
        this.maximized = false;
        this.minimized = false;

        this.setX(previousPos.x);
        this.setY(previousPos.y);
        this.setWidth(previousSize.x);
        this.setHeight(previousSize.y);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.holdingTitle = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOverTitle(mouseX, mouseY)) {
            int tcr = getX() + getWidth() + 1; // Title Controls Right
            if (isMouseOver((int) mouseX, (int) mouseY, tcr - 24, getY() + 1, 24, 12)) {
                close();
            }

            if (isMouseOver((int) mouseX, (int) mouseY, tcr - 48, getY() + 1, 24, 12)) {
                if (maximized) restore();
                else maximize();
            }

            if (isMouseOver((int) mouseX, (int) mouseY, tcr - 72, getY() + 1, 24, 12)) {
                if (minimized) restore();
                else minimize();
            }
            if (button == 0) {
                this.holdingTitleFrom = new Vector2d(mouseX, mouseY);
                this.holdingTitleSince = new Vector2d(getX(), getY());
                this.holdingTitle = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.holdingTitle) {
            var fromX = (int) this.holdingTitleFrom.x;
            var fromY = (int) this.holdingTitleFrom.y;
            var sinceX = (int) this.holdingTitleSince.x;
            var sinceY = (int) this.holdingTitleSince.y;

            var deltaX = (int)mouseX - fromX;
            var deltaY = (int)mouseY - fromY;

            setX(sinceX + deltaX);
            setY(sinceY + deltaY);
        }

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    private boolean isMouseOverTitle(double mouseX, double mouseY) {
        return mouseX > getX() + 2 && mouseX < getX() + width - 2 && mouseY > getY() + 2 && mouseY <= getY() + getBorder().top;
    }

    @Override
    protected final Insets getBorder() {
        if (undecorated) {
            return new Insets(0, 0, 0, 0);
        }
        return super.getBorder();
    }

    @Override
    protected final void setBorder(Insets border) {
        if (undecorated) {
            return;
        }
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

    public boolean isTopMost() {
        return topMost;
    }

    public void setTopMost(boolean topMost) {
        this.wm.setTopMost(this, topMost);
    }

    public boolean isBottomMost() {
        return bottomMost;
    }

    public void setBottomMost(boolean bottomMost) {
        this.wm.setBottomMost(this, bottomMost);
    }

    public boolean isUndecorated() {
        return undecorated;
    }

    public boolean isActiveWindow() {
        return this.wm.isActiveWindow(this);
    }

    public void setUndecorated(boolean undecorated) {
        if (undecorated) {
            this.setX(this.getX() - getBorder().left);
            this.setY(this.getY() - getBorder().top);
        } else {
            this.setX(this.getX() + getBorder().left);
            this.setY(this.getY() + getBorder().top);
        }
        this.undecorated = undecorated;
    }

    public boolean isMinimized() {
        return minimized;
    }

    public boolean isMaximized() {
        return maximized;
    }
}
