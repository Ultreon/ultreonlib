package com.ultreon.mods.lib.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Progressbar extends BaseWidget {
    private static final ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");
    private int maximum;
    private int value;

    /**
     * @param x widget center x
     * @param y widget center y
     */
    public Progressbar(int x, int y,  int maximum) {
        this(x, y, 0, maximum);
    }

    /**
     * @param x widget center x
     * @param y widget center y
     */
    public Progressbar(int x, int y, int value, int maximum) {
        super(x, y, 182, 10, Component.empty());

        this.value = Mth.clamp(value, 0, maximum);
        this.maximum = maximum;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = Mth.clamp(value, 0, maximum);
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
        this.value = Mth.clamp(value, 0, maximum);
    }

    public double getPercentage() {
        return 100 * getRatio();
    }

    public double getRatio() {
        return (double) value / (double) maximum;
    }

    @Override
    public void renderWidget(PoseStack poseStack, int i, int j, float f) {
        int x = getX() - 91;
        int y = getY() - 3;

        RenderSystem.setShaderTexture(0, ICONS);
        blit(poseStack, x, y, 0, 64, 182, 5);

        RenderSystem.setShaderTexture(0, ICONS);
        blit(poseStack, x, y, 0, 69, (int) (182 * getRatio()), 5);
    }

    @Override
    public void setWidth(int width) {

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
