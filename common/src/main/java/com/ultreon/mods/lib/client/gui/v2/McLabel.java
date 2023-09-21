package com.ultreon.mods.lib.client.gui.v2;

import com.ultreon.mods.lib.util.ScissorStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class McLabel extends McComponent {
    public McLabel(int x, int y, int width, int height, Component altText) {
        super(x, y, width, height, altText);
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        ScissorStack.pushScissorTranslated(gfx, getX(), getY(), width, height);
        {
            drawCenteredStringWithoutShadow(gfx, font, getMessage(), getX() + width / 2, getY() + height / 2, 0xffffffff);
        }
        ScissorStack.popScissor();
    }

    @FunctionalInterface
    public interface ClickCallback {
        void onClick(McLabel image, int clicks);
    }
}
