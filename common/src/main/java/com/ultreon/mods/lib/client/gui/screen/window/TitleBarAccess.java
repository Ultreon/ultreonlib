package com.ultreon.mods.lib.client.gui.screen.window;

import com.ultreon.mods.lib.client.gui.screen.TabPage;
import com.ultreon.mods.lib.client.gui.screen.ULibScreen;
import com.ultreon.mods.lib.client.gui.widget.WidgetsContainer;
import net.minecraft.network.chat.Component;

public interface TitleBarAccess extends WidgetsContainer {
    void setTitle(Component title);

    TitleBarAccess title(Component title);

    Component getTitle();

    void addTab(TabPage page);

    void removeTab(TabPage page);

    TabPage getCurrentTab();

    int getTabCount();

    void nextTab();

    void prevTab();

    ULibScreen getScreen();
}
