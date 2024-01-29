package com.ultreon.mods.lib.actionmenu;

import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.widget.UIWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ActionMenuTitle extends UIWidget<ActionMenuTitle> implements ActionMenuIndexable {
    private int menuIndex;

    public ActionMenuTitle(ActionMenuScreen screen) {
        super(screen.getTitle());
    }

    @Override
    public void renderWidget(@NotNull GuiRenderer gfx, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        int width = font.width(this.getMessage().getString()) + 2;

        Color color = new Color(0, 0, 0, 127 / (menuIndex + 1));
        int col = color.getRGB();

        gfx.fill(getX() + (this.width / 2 - width / 2) - 1, getY() + (this.height / 2 - 5) - 1 - 2, getX() + (this.width / 2 + width / 2), getY() + (this.height / 2 - 5) + 12, col);

        int j = new Color(255, 255, 255, 255 / (menuIndex + 1)).getRGB(); // White : Light Grey
        gfx.textCenter(this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, j);
    }

    @Override
    public void setMenuIndex(int index) {
        this.menuIndex = index;
    }

    @Override
    public void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }
}
