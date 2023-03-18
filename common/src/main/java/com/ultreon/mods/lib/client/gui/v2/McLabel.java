package com.ultreon.mods.lib.client.gui.v2;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.util.ScissorStack;
import net.minecraft.network.chat.Component;

public class McLabel extends McComponent {
    public McLabel(int x, int y, int width, int height, Component altText) {
        super(x, y, width, height, altText);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        ScissorStack.pushScissorTranslated(poseStack, getX(), getY(), width, height);
        {
            drawCenteredStringWithoutShadow(poseStack, font, getMessage(), getX() + width / 2, getY() + height / 2, 0xffffffff);
        }
        ScissorStack.popScissor();
    }

    @FunctionalInterface
    public interface ClickCallback {
        void onClick(McLabel image, int clicks);
    }
}
