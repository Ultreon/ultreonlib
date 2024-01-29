package com.ultreon.mods.lib.client.gui.widget;

import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.screen.TabPage;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SimpleTabPage implements TabPage {
    @Override
    public void initWidgets() {

    }

    @Override
    public Component getMessage() {
        return null;
    }

    @Override
    public void setMessage(Component message) {

    }

    @Override
    public void render(GuiRenderer guiRenderer, int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public @NotNull List<? extends ULibWidget> children() {
        return null;
    }

    @Override
    public boolean isDragging() {
        return false;
    }

    @Override
    public void setDragging(boolean isDragging) {

    }

    @Nullable
    @Override
    public GuiEventListener getFocused() {
        return null;
    }

    @Override
    public void setFocused(@Nullable GuiEventListener focused) {

    }

    @Override
    public <T extends ULibWidget> T add(T widget) {
        return null;
    }
}
