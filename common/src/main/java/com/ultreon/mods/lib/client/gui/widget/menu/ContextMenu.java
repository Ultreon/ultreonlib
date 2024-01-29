package com.ultreon.mods.lib.client.gui.widget.menu;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.FrameType;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.screen.ULibScreen;
import com.ultreon.mods.lib.client.gui.widget.ContainerWidget;
import com.ultreon.mods.lib.client.gui.widget.ULibWidget;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author XyperCode
 */
public class ContextMenu extends ContainerWidget {
    // Constants
    private static final int BORDER_WIDTH = 5;

    // Entries
    private final List<MenuItem> entries = new ArrayList<>();

    // Events.
    private OnClose onClose = menu -> {
    };
    private GlobalTheme globalTheme;

    /**
     * @param title context menu title.
     */
    public ContextMenu(@Nullable Component title) {
        this(title, UltreonLib.getTheme());
    }

    public ContextMenu(@Nullable Component title, GlobalTheme globalTheme) {
        super(title);

        this.globalTheme = globalTheme;
    }

    /**
     * Updates narration.
     *
     * @param output output for narration elements.
     */
    @Override
    public void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        output.add(NarratedElementType.TITLE, this.createNarrationMessage());
    }

    @Override
    public void renderWidget(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        renderer.pushPose();
        renderer.translate(0, 0, 100);

        Component message = getMessage();
        boolean hasTitle;
        //noinspection ConstantConditions
        if (message != null) {
            hasTitle = !message.getString().isBlank();
        } else {
            hasTitle = false;
        }

        Font font = Minecraft.getInstance().font;
        renderer.renderMenuFrame(getX(), getY(), width - 14, height - 4 - (hasTitle ? 0 : font.lineHeight + 1), FrameType.MENU);

        //noinspection ConstantConditions
        if (message != null) {
            renderer.textLeft(message, getX() + 7, getY() + 5, globalTheme.getMenuTheme().getHeaderColor().getRgb(), false);
        }

        this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;

        renderer.pushPose();
        int y = this.getY() + 5 + (hasTitle ? font.lineHeight + 1 : 0);
        int x = this.getX() + 5;
        Stream<MenuItem> entryStream = entries.stream();
        IntStream minWidths = entryStream.mapToInt(MenuItem::getMinWidth);
        int maxMinWidth = minWidths.max().orElse(0);

        for (MenuItem menuItem : entries) {
            width = Math.max(width, menuItem.getMinWidth());
        }

        for (MenuItem menuItem : entries) {
            menuItem.setX(x);
            menuItem.setY(y);
            menuItem.setWidth(Mth.clamp(maxMinWidth, menuItem.getMinWidth(), menuItem.getMaxWidth()));
            menuItem.renderWidget(renderer, mouseX, mouseY, partialTicks);

            y += menuItem.getHeight() + 2;
        }

        renderer.popPose();
        renderer.popPose();
    }

    /**
     * Adds a menu item entry.
     *
     * @param menuItem menu item to add.
     * @param <T>      item type.
     * @return the same as menu item parameter.
     */
    public <T extends MenuItem> T addItem(T menuItem) {
        entries.add(menuItem);
        menuItem.setX(getX() + 5);
        menuItem.setY(getY() + 5);
        invalidateSize();
        return menuItem;
    }

    void invalidateSize() {
        width = BORDER_WIDTH * 2 + entries.stream().mapToInt(MenuItem::getMinWidth).max().orElse(1);
        height = BORDER_WIDTH * 2 + entries.stream().mapToInt(MenuItem::getHeight).sum() + 2 * Math.max(entries.size() - 1, 0);
    }

    /**
     * Get all menu entries currently in the context menu.
     *
     * @return all menu entries (unmodifiable).
     */
    @Override
    public @NotNull List<? extends ULibWidget> children() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Set the on close event handler.
     *
     * @param onClose close handler.
     */
    public void setOnClose(OnClose onClose) {
        this.onClose = onClose;
    }

    /**
     * DON'T CALL IF YOU DON'T KNOW WHAT YOU ARE DOING.
     * This method is called for internal usage, and should not be called to close the context menu.
     * Use the {@link ULibScreen#closeContextMenu()} method to close the menu instead.
     */
    public final void onClose() {
        onClose.call(this);
    }

    /**
     * On Close event handler interface.
     * Could be used as lambda.
     */
    @FunctionalInterface
    public interface OnClose {
        /**
         * Handler itself.
         *
         * @param menu context menu for handling the closing with.
         */
        void call(ContextMenu menu);
    }
}
