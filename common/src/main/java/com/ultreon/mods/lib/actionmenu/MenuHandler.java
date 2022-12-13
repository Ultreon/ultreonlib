package com.ultreon.mods.lib.actionmenu;

import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public class MenuHandler implements IMenuHandler {
    private final Component text;
    private final Submenu menu;
    private final Supplier<Boolean> enabled;

    public MenuHandler(Component text, Submenu menu) {
        this.text = text;
        this.menu = menu;
        this.enabled = () -> true;
    }

    public MenuHandler(Component text, Submenu menu, Supplier<Boolean> enabled) {
        this.text = text;
        this.menu = menu;
        this.enabled = enabled;
    }

    @Override
    public Submenu getMenu() {
        return menu;
    }

    @Override
    public Component getText() {
        if (text == null) {
            return Component.literal("...");
        }
        return text;
    }

    @Override
    public boolean isEnabled() {
        return enabled.get();
    }
}
