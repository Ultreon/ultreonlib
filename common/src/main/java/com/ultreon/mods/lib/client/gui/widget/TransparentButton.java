package com.ultreon.mods.lib.client.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TransparentButton extends BaseButton {
    public TransparentButton(int x, int y, int width, int height, Component title, CommandCallback action) {
        super(x, y, width, height, title, action);
    }

    public TransparentButton(int x, int y, int width, int height, Component title, CommandCallback action, TooltipFactory factory) {
        super(x, y, width, height, title, action, factory);
    }

    @Override
    public void renderWidget(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;

        int col = new Color(0, 0, 0, 127).getRGB();

        fill(poseStack, getX(), getY(), getX() + width, getY() + height, col);

        int hov = new Color(255, 255, 0, 255).getRGB();
        int nrm = new Color(255, 255, 255, 255).getRGB();
        int dis = new Color(160, 160, 160, 255).getRGB();

        int j;
        if (this.active) {
            if (isHovered) {
                j = hov;
            } else {
                j = nrm;
            }
        } else {
            j = dis;
        }

        if (isHovered && active) {
            drawCenteredString(poseStack, font, this.getMessage(), (this.getX() + this.width / 2) + 1, (this.getY() + (this.height - 8) / 2) + 1, j);
        } else {
            drawCenteredString(poseStack, font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, j);
        }
    }
}
