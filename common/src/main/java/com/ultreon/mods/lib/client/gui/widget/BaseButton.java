package com.ultreon.mods.lib.client.gui.widget;

import com.ultreon.mods.lib.client.gui.Clickable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class BaseButton extends BaseWidget implements Clickable {
    private CommandCallback callback;
    private TooltipFactory tooltipFactory;

    public BaseButton(int x, int y, int width, int height, Component message, CommandCallback callback) {
        this(x, y, width, height, message, callback, (button) -> null);
    }

    public BaseButton(int x, int y, int width, int height, Component message, CommandCallback callback, TooltipFactory tooltipFactory) {
        super(x, y, width, height, message);
        this.setTextColor(0xffffff);
        this.callback = callback;
        this.tooltipFactory = tooltipFactory;

        updateTooltip();
    }

    public void updateTooltip() {
        setTooltip(tooltipFactory.create(this));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }

    @Override
    public void onLeftClick(int clicks) {
        callback.click(this);
    }

    public void setCallback(CommandCallback callback) {
        this.callback = callback;
    }

    public void setTooltipFactory(TooltipFactory tooltipFactory) {
        this.tooltipFactory = tooltipFactory;
    }

    @Override
    public int getTextColor() {
        if (isUsingCustomTextColor()) {
            return super.getTextColor();
        }
        return active ? getTheme().getButtonTextColor() : getTheme().getInactiveTextColor();
    }

    @FunctionalInterface
    public interface CommandCallback {
        void click(BaseButton button);
    }

    @FunctionalInterface
    public interface TooltipFactory {
        Tooltip create(BaseButton button);

        default void narrateTooltip(Consumer<Component> contents) {
        }
    }
}
