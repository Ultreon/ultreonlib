package com.ultreon.mods.lib.client.gui.screen;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.ultreon.mods.lib.client.gui.FrameType;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.widget.toolbar.ToolbarItem;
import com.ultreon.mods.lib.client.gui.widget.toolbar.ToolbarWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class FullscreenRenderScreen extends ULibScreen {
    private static final int ITEM_PADDING = 2;
    private static final int BOTTOM_MARGIN = 12;
    private final List<ToolbarWidget> items = new ArrayList<>();

    protected FullscreenRenderScreen(Component title) {
        super(title);
    }

    @Override
    public void render(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(renderer, mouseX, mouseY, partialTicks);
        this.renderToolbar(renderer, mouseX, mouseY, partialTicks);

        renderer.textCenter(this.getTitle(), width / 2, 9, 0xFFFFFF);

        super.render(renderer, mouseX, mouseY, partialTicks);
    }

    private void renderToolbar(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        final int paddings = ITEM_PADDING * Math.max(items.size() - 1, 0);
        final int width = items.stream().mapToInt(ToolbarWidget::width).sum() + paddings;
        final int height = items.stream().mapToInt(ToolbarWidget::height).max().orElse(0);
        final int frameWidth = width - 4;
        final int frameHeight = height - 4;
        final int frameX = this.width / 2 - frameWidth / 2 - 5;
        final int frameY = this.height - frameHeight - BOTTOM_MARGIN - 14;
        renderer.renderContentFrame(frameX, frameY, frameWidth + 14, frameHeight + 14, FrameType.NORMAL);
        int x = frameX + 5;
        final int y = frameY + 2 + 7 - 4;
        for (ToolbarWidget item : items) {
            int width1 = item.width();
            if (item instanceof ToolbarItem<?> toolbarItem) {
                toolbarItem.setX(x);
                toolbarItem.setY(y);
            }
            item.render(renderer, mouseX, mouseY, partialTicks);

            x += width1 + ITEM_PADDING;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (ToolbarWidget baseItem : items) {
            if (baseItem instanceof ToolbarItem<?> item && item.isActive() && item.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button)/* || this.filterList.mouseClicked(mouseX, mouseY, button)*/;
    }

    @CanIgnoreReturnValue
    public <T extends ToolbarWidget> T addToolbarItem(T t) {
        items.add(t);
        if (t instanceof ToolbarItem<?> toolbarItem) {
            this.add(toolbarItem);
        }
        return t;
    }

    @Override
    public Vec2 getCloseButtonPos() {
        return new Vec2(width - 6 - 3 - 5, 6 + 2);
    }
}
