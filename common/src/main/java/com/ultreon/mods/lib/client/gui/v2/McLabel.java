package com.ultreon.mods.lib.client.gui.v2;

import com.ultreon.mods.lib.util.ScissorStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class McLabel extends McComponent {
    public McLabel(int x, int y, int width, int height, String text) {
        this(x, y, width, height, Component.literal(text));
    }

    public McLabel(int x, int y, int width, int height, Component text) {
        super(x, y, width, height, text);
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        ScissorStack.pushScissorTranslated(gfx, this.getX(), this.getY(), this.width, this.height);
        {
            gfx.drawString(this.font, getMessage(), this.getX(), this.getY(), 0xffffffff, false);
        }
        ScissorStack.popScissor();
    }

    @FunctionalInterface
    public interface ClickCallback {
        void onClick(McLabel label, int clicks);
    }
}
