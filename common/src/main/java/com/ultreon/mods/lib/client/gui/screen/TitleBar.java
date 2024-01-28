package com.ultreon.mods.lib.client.gui.screen;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.widget.ContainerWidget;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.client.theme.Theme;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

class TitleBar extends ContainerWidget {
    private GlobalTheme globalTheme = UltreonLib.getTheme();

    public TitleBar(Component message) {
        super(message);
    }

    @Override
    public void reloadTheme() {
        super.reloadTheme();

        this.globalTheme = UltreonLib.getTheme();
    }

    @Override
    public void renderWidget(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        super.renderWidget(renderer, mouseX, mouseY, partialTicks);

        renderer.renderTitleFrame(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.globalTheme.getFrameType());
    }

    @Override
    public Theme getTheme() {
        return this.globalTheme.getWindowTheme();
    }
}
