package com.ultreon.mods.lib.client.gui.screen;

import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.widget.WidgetsContainer;
import net.minecraft.network.chat.Component;

public interface TabPage extends WidgetsContainer {
    void initWidgets();

    Component getMessage();

    void setMessage(Component message);

    void render(GuiRenderer guiRenderer, int mouseX, int mouseY, float partialTicks);
}
