package com.ultreon.mods.lib.actionmenu;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;

import java.awt.*;

public class ActionMenuTitle extends AbstractWidget implements IActionMenuIndexable {
    private int menuIndex;

    public ActionMenuTitle(ActionMenuScreen screen, int x, int y, int width, int height) {
        super(x, y, width, height, screen.getTitle());
    }

    @Override
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        int width = font.width(this.getMessage().getString()) + 2;

        Color color = new Color(0, 0, 0, 127 / (menuIndex + 1));
        int col = color.getRGB();

        fill(matrixStack, getX() + (this.width / 2 - width / 2) - 1, getY() + (this.height / 2 - 5) - 1 - 2, getX() + (this.width / 2 + width / 2), getY() + (this.height / 2 - 5) + 12, col);

        int j = new Color(255, 255, 255, 255 / (menuIndex + 1)).getRGB(); // White : Light Grey
        drawCenteredString(matrixStack, font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, j);
    }

    @Override
    public void setMenuIndex(int index) {
        this.menuIndex = index;
    }

    @Override
    public void updateWidgetNarration(NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }
}
