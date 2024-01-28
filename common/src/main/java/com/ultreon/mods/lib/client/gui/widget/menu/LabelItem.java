package com.ultreon.mods.lib.client.gui.widget.menu;

import com.ultreon.mods.lib.client.gui.GuiRenderer;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class LabelItem extends MenuItem {
    public LabelItem(ContextMenu menu, Component message) {
        super(13, menu, message);
    }

    @Override
    public void renderWidget(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        renderer.textLeft(getMessage(), getX(), (int) (getY() + 6 - font.lineHeight / 2f), getTextColor());
        if (isHoveredOrFocused()) {
            int x1 = getX() + width;
            int y1 = getY() + height;
            renderer.fill(getX(), getY(), x1, getY(), getTextColor());   // top
            renderer.fill(getX(), y1, x1, y1, getTextColor()); // bottom
            renderer.fill(getX(), getY(), getX(), y1, getTextColor());   // left
            renderer.fill(x1, getY(), x1, y1, getTextColor()); // right
        }
    }

    @Override
    public void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        output.add(NarratedElementType.TITLE, this.createNarrationMessage());
        if (this.active) {
            if (this.isFocused()) {
                output.add(NarratedElementType.USAGE, Component.translatable("narration.button.usage.focused"));
            } else {
                output.add(NarratedElementType.USAGE, Component.translatable("narration.button.usage.hovered"));
            }
        }
    }
}
