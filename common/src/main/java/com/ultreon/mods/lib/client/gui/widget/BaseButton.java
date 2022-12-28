package com.ultreon.mods.lib.client.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.client.gui.Clickable;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class BaseButton extends BaseWidget implements Clickable {
    private CommandCallback callback;
    private TooltipHandler tooltipHandler;

    public BaseButton(int x, int y, int width, int height, Component message, CommandCallback callback) {
        this(x, y, width, height, message, callback, (button, pose, mouseX, mouseY) -> {});
    }

    public BaseButton(int x, int y, int width, int height, Component message, CommandCallback callback, TooltipHandler tooltipHandler) {
        super(x, y, width, height, message);
        this.setTextColor(0xffffff);
        this.callback = callback;
        this.tooltipHandler = tooltipHandler;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
        this.tooltipHandler.narrateTooltip(component -> narrationElementOutput.add(NarratedElementType.HINT, component));
    }

    @Override
    public void onLeftClick(int clicks) {
        callback.click(this);
    }

    public void setCallback(CommandCallback callback) {
        this.callback = callback;
    }

    public void setTooltipHandler(TooltipHandler tooltipHandler) {
        this.tooltipHandler = tooltipHandler;
    }

    @FunctionalInterface
    public interface CommandCallback {
        void click(BaseButton button);
    }

    @FunctionalInterface
    public interface TooltipHandler {
        void onTooltip(Button button, PoseStack pose, int mouseX, int mouseY);

        default void narrateTooltip(Consumer<Component> contents) {
        }
    }
}
