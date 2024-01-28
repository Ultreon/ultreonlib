package com.ultreon.mods.lib.client.gui.widget;

import com.ultreon.mods.lib.client.gui.GuiRenderer;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class Progressbar extends UIWidget<Progressbar> {
    private static final ResourceLocation BAR_BACKGROUND = new ResourceLocation("hud/experience_bar_background");
    private static final ResourceLocation BAR_PROGRESS = new ResourceLocation("hud/experience_bar_progress");
    private int maximum;
    private int value;

    /**
     * @param maximum the maximum value
     */
    public Progressbar(int maximum) {
        this(0, maximum);
    }

    /**
     * @param value   the current value
     * @param maximum the maximum value
     */
    public Progressbar(int value, int maximum) {
        super(Component.empty());

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

    public void setPercentage(double percentage) {
        setRatio(percentage / 100.0);
    }

    public double getRatio() {
        return (double) value / (double) maximum;
    }

    public void setRatio(double ratio) {
        this.value = (int) (ratio * maximum);
    }

    public boolean isFull() {
        return value >= maximum;
    }

    public boolean isEmpty() {
        return value <= 0;
    }

    @Override
    public void renderWidget(@NotNull GuiRenderer renderer, int i, int j, float f) {
        int x = getX() - 91;
        int y = getY() - 3;

        renderer.blitSprite(BAR_BACKGROUND, x, y, 182, 5);
        renderer.blitSprite(BAR_PROGRESS, 182, 5, 0, 0, x, y, (int) (182 * getRatio()), 5);
    }

    @Override
    public void revalidate() {
        this.setPositionNow(this.positionGetter.get());
    }

    @Override
    public void setWidth(int width) {
        // Width is a constant for progress bars.
    }

    @Override
    public void setHeight(int height) {
        // Height is a constant for progress bars.
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        // No narration for progress bars.
    }
}
