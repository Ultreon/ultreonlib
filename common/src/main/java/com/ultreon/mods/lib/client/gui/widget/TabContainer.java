package com.ultreon.mods.lib.client.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class TabContainer extends BaseContainerWidget {
    private final TabNavigator navigator;
    private boolean closeable;

    public TabContainer(TabNavigator navigator, int x, int y, int width, int height, Component title) {
        super(x, y, width, height, title);
        this.navigator = navigator;
    }

    public void add(BaseWidget component) {
        this.children.add(component);
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        defaultButtonNarrationText(narrationElementOutput);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        for (BaseWidget child : children) {
            child.render(poseStack, mouseX, mouseY, partialTick);
        }
    }

    public TabNavigator getNavigator() {
        return navigator;
    }

    public boolean isCloseable() {
        return closeable;
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
