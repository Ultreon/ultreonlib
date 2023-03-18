package com.ultreon.mods.lib.client.gui.v2;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.client.gui.widget.BaseWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class McComponent extends BaseWidget {
    @Nullable
    McContainer parent = null;
    private McContextMenu contextMenu;
    private boolean holding;

    public McComponent(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) this.holding = true;

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) this.holding = false;

        return super.mouseReleased(mouseX, mouseY, button);
    }

    public boolean isHolding() {
        return holding;
    }

    @Override
    public void onRightClick() {
        super.onRightClick();
//        if (contextMenu != null) {
//
//        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        defaultButtonNarrationText(narrationElementOutput);
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
