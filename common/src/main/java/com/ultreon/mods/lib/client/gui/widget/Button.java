package com.ultreon.mods.lib.client.gui.widget;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.Clickable;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.client.theme.ThemeComponent;
import com.ultreon.mods.lib.client.theme.ThemeGuiComponent;
import com.ultreon.mods.lib.client.theme.WidgetPlacement;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unchecked")
public abstract class Button<T extends Button<T>> extends UIWidget<T> implements Clickable {
    protected static final ThemeGuiComponent BUTTON_THEME_COMPONENT;
    protected Callback<T> callback;
    private TooltipFactory<T> tooltipFactory;

    static {
        BUTTON_THEME_COMPONENT = WidgetPlacement.CONTENT.register(UltreonLib.res("button"), ThemeComponent.create(GlobalTheme::getContentButtonStyle));
    }

    protected Button(Component message, Callback<T> callback) {
        this(message, callback, (button) -> null);
    }

    protected Button(Component message, Callback<T> callback, TooltipFactory<T> tooltipFactory) {
        super(message);
        this.callback = callback;
        this.tooltipFactory = tooltipFactory;

        updateTooltip();
    }

    public void updateTooltip() {
        setTooltip(tooltipFactory.create((T) this));
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean onLeftClick(int clicks) {
        callback.call((T) this);
        return true;
    }

    public T callback(Callback<T> onClick) {
        this.callback = onClick;
        return (T) this;
    }

    public T tooltip(TooltipFactory<T> onTooltip) {
        this.setTooltipFactory(onTooltip);
        return (T) this;
    }

    public T message(Component text) {
        this.setMessage(text);
        return (T) this;
    }

    public void setCallback(Callback<T> callback) {
        this.callback = callback;
    }

    public void setTooltipFactory(TooltipFactory<T> tooltipFactory) {
        this.tooltipFactory = tooltipFactory;
    }

    @Override
    public int getTextColor() {
        if (isUsingCustomTextColor()) {
            return super.getTextColor();
        }
        return (active ? getStyle().getTextColor() : getStyle().getInactiveTextColor()).getRgb();
    }

}
