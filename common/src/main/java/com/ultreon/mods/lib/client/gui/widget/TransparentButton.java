package com.ultreon.mods.lib.client.gui.widget;

import com.ultreon.mods.lib.client.gui.GuiRenderer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class TransparentButton<T extends TransparentButton<T>> extends Button<T> {
    public TransparentButton(Component title, Callback<T> action) {
        super(title, action);
    }

    public TransparentButton(Component title, Callback<T> action, TooltipFactory<T> factory) {
        super(title, action, factory);
    }

    @Override
    public void renderWidget(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        int col = new Color(0, 0, 0, 127).getRGB();

        renderer.fill(getX(), getY(), getX() + width, getY() + height, col);

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
            renderer.textCenter(this.getMessage(), (this.getX() + this.width / 2) + 1, (this.getY() + (this.height - 8) / 2) + 1, j);
        } else {
            renderer.textCenter(this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, j);
        }
    }
}
