package com.ultreon.mods.lib.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.UltreonLibConfig;
import com.ultreon.mods.lib.client.gui.widget.IToolbarItem;
import com.ultreon.mods.lib.client.gui.widget.ToolbarItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class FullscreenRenderScreen extends BaseScreen {
    private static final int ITEM_PADDING = 2;
    private static final int BOTTOM_MARGIN = 12;
    private final List<IToolbarItem> items = new ArrayList<>();

    protected FullscreenRenderScreen(Component title) {
        super(title);
    }

    @Override
    public abstract void renderBackground(@NotNull PoseStack pose);

    @Override
    public void render(@NotNull PoseStack pose, int mouseX, int mouseY, float partialTicks) {
        renderBackground(pose);

        renderToolbar(pose, mouseX, mouseY, partialTicks);

        drawCenteredString(pose, font, getTitle(), width / 2, 9, 0xFFFFFF);

        super.render(pose, mouseX, mouseY, partialTicks);
    }

    private void renderToolbar(PoseStack pose, int mouseX, int mouseY, float partialTicks) {
        final int paddings = ITEM_PADDING * Math.max(items.size() - 1, 0);
        final int width = items.stream().mapToInt(IToolbarItem::width).sum() + paddings;
        final int height = items.stream().mapToInt(IToolbarItem::height).max().orElse(0);
        final int frameWidth = width - 4;
        final int frameHeight = height - 4;
        final int frameX = this.width / 2 - frameWidth / 2 - 5;
        final int frameY = this.height - frameHeight - BOTTOM_MARGIN - 14;
        renderFrame(pose, frameX, frameY, frameWidth, frameHeight, UltreonLibConfig.THEME.get());
        int x = frameX + 5;
        final int y = frameY + 2 + 7 - 4;
        for (IToolbarItem item : items) {
            int width1 = item.width();
            if (item instanceof ToolbarItem toolbarItem) {
                toolbarItem.setX(x);
                toolbarItem.setY(y);
            }
            item.render(pose, mouseX, mouseY, partialTicks);

            x += width1 + ITEM_PADDING;
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (IToolbarItem baseItem : items) {
            if (baseItem instanceof ToolbarItem item && item.isActive() && item.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button)/* || this.filterList.mouseClicked(mouseX, mouseY, button)*/;
    }

    public <T extends IToolbarItem> T addToolbarItem(T t) {
        items.add(t);
        if (t instanceof ToolbarItem toolbarItem) {
            addWidget(toolbarItem);
        }
        return t;
    }

    @Override
    public Vec2 getCloseButtonPos() {
        return new Vec2(width - 6 - 3 - 5, 6 + 3);
    }
}
