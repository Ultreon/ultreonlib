package com.ultreon.mods.lib.client.gui.v2;

import com.ultreon.mods.lib.util.ScissorStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class McButton extends McComponent {
    private final List<ClickCallback> onClick = new ArrayList<>();

    public McButton(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        var background = 0xff444444;
        if (isMouseOver(mouseX, mouseY)) background = 0xff666666;
        if (isHolding()) background = 0xff222222;
        gfx.fill(getX(), getY(), getX() + width, getY() + height, background);
        ScissorStack.pushScissorTranslated(gfx, getX() + 1, getY() + 1, width - 2, height - 2);
        {
            drawCenteredStringWithoutShadow(gfx, font, getMessage(), getX() + width / 2, getY() + height / 2 - 4, 0xffffffff);
        }
        ScissorStack.popScissor();
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (var callback : onClick) {
                callback.onClick(this);
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public void addClickHandler(ClickCallback onClick) {
        this.onClick.add(onClick);
    }

    public void removeClickHandler(ClickCallback onClick) {
        this.onClick.remove(onClick);
    }

    @FunctionalInterface
    public interface ClickCallback {
        void onClick(McButton button);
    }
}
