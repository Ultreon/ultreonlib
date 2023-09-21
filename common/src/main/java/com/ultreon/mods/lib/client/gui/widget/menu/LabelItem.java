/*
 * Copyright (c) 2022. - Qboi SMP Development Team
 * Do NOT redistribute, or copy in any way, and do NOT modify in any way.
 * It is not allowed to hack into the code, use cheats against the code and/or compiled form.
 * And it is not allowed to decompile, modify or/and patch parts of code or classes or in full form.
 * Sharing this file isn't allowed either, and is hereby strictly forbidden.
 * Sharing decompiled code on social media or an online platform will cause in a report on that account.
 *
 * ONLY the owner can bypass these rules.
 */

package com.ultreon.mods.lib.client.gui.widget.menu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class LabelItem extends MenuItem {
    public LabelItem(ContextMenu menu, Component message) {
        super(13, menu, message);
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        gfx.drawString(this.font, getMessage(), getX(), (int) (getY() + 6 - font.lineHeight / 2f), getTextColor());
        if (isHoveredOrFocused()) {
            int x1 = getX() + width;
            int y1 = getY() + height;
            gfx.fill(getX(), getY(), x1, getY(), getTextColor());   // top
            gfx.fill(getX(), y1, x1, y1, getTextColor()); // bottom
            gfx.fill(getX(), getY(), getX(), y1, getTextColor());   // left
            gfx.fill(x1, getY(), x1, y1, getTextColor()); // right
        }
    }

    @Override
    public void updateWidgetNarration(@NotNull NarrationElementOutput narration) {
        narration.add(NarratedElementType.TITLE, this.createNarrationMessage());
        if (this.active) {
            if (this.isFocused()) {
                narration.add(NarratedElementType.USAGE, Component.translatable("narration.button.usage.focused"));
            } else {
                narration.add(NarratedElementType.USAGE, Component.translatable("narration.button.usage.hovered"));
            }
        }
    }
}
