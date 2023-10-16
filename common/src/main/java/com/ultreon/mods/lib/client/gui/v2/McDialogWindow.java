package com.ultreon.mods.lib.client.gui.v2;

import com.ultreon.libs.commons.v0.size.IntSize;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2d;
import org.joml.Vector2i;

public class McDialogWindow extends McWindow {
    McWindow root;
    McWindow parent;
    private boolean holdingTitle;
    private @Nullable Vector2d holdingTitleFrom;
    private @Nullable Vector2d holdingTitleSince;

    public McDialogWindow(@NotNull McApplication application , int width, int height, String title) {
        super(application, 0, 0, width, height, title);
    }

    public McDialogWindow(@NotNull McApplication application, int width, int height, Component title) {
        super(application, 0, 0, width, height, title);
    }

    @Override
    public void minimize() {

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean b = super.mouseClicked(mouseX, mouseY, button);
        if (!b && this.isMouseOverTitle(mouseX, mouseY) && button == 0) {
            this.holdingTitleFrom = new Vector2d(mouseX, mouseY);
            this.holdingTitleSince = new Vector2d(root.getX(), root.getY());
            this.holdingTitle = true;
            return true;
        }
        return b;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.holdingTitle = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.holdingTitle && this.holdingTitleFrom != null && this.holdingTitleSince != null) {
            var fromX = (int) this.holdingTitleFrom.x;
            var fromY = (int) this.holdingTitleFrom.y;
            var sinceX = (int) this.holdingTitleSince.x;
            var sinceY = (int) this.holdingTitleSince.y;

            var deltaX = (int) mouseX - fromX;
            var deltaY = (int) mouseY - fromY;

            root.setX(sinceX + deltaX);
            root.setY(sinceY + deltaY);
            return true;
        }

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    @Nullable
    protected Vector2i getForcePosition() {
        return new Vector2i(this.parent.getX() + (this.parent.getWidth() - this.width) / 2, this.parent.getY() + (this.parent.getHeight() - this.height) / 2);
    }

    public McWindow getParent() {
        return this.parent;
    }
}
