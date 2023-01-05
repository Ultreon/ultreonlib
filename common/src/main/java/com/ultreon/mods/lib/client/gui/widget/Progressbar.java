package com.ultreon.mods.lib.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class Progressbar extends BaseWidget {
    private static final ResourceLocation BARS = new ResourceLocation("textures/gui/bars.png");
    private int maximum;
    private int value = 0;
    private ColorType colorType;
    private Segments segments;

    public Progressbar(int x, int y, int maximum) {
        super(x, y, 182, 10, Component.empty());

        this.maximum = maximum;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public ColorType getColorType() {
        return colorType;
    }

    public void setColorType(ColorType colorType) {
        this.colorType = colorType;
    }

    public Segments getSegments() {
        return segments;
    }

    public void setSegments(Segments segments) {
        this.segments = segments;
    }

    public double getPercentage() {
        return 100 * getRatio();
    }

    public double getRatio() {
        return (double) value / (double) maximum;
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        int colV = colorType.getTextureV();
        int segV = segments.getTextureV();

        RenderSystem.setShaderTexture(0, BARS);
        blit(poseStack, getX(), getY(), 0, colV, 182, 5);
        blit(poseStack, getX(), getY(), 0, segV, 182, 5);

        RenderSystem.setShaderTexture(0, BARS);
        blit(poseStack, getX(), getY(), 0, colV + 5, (int) (182 * getRatio()), 5);
        blit(poseStack, getX(), getY(), 0, segV + 5, (int) (182 * getRatio()), 5);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public enum ColorType {
        PURPLE(0),
        AQUA(10),
        RED(20),
        GREEN(30),
        YELLOW(40),
        BLUE(50),
        WHITE(60),
        ;

        private final int textureV;

        ColorType(int textureV) {

            this.textureV = textureV;
        }

        public int getTextureV() {
            return textureV;
        }
    }

    public enum Segments {
        SIX(80),
        TEN(90),
        TWELVE(100),
        TWENTY(110),
        ;

        private final int textureV;

        Segments(int textureV) {

            this.textureV = textureV;
        }

        public int getTextureV() {
            return textureV;
        }
    }
}
