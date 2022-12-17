package com.ultreon.mods.lib.actionmenu;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class SubmenuItem extends ActionMenuItem {
    private final IMenuHandler handler;

    public SubmenuItem(ActionMenu parent, String modId, String name, IMenuHandler handler) {
        super(parent, modId, name);
        this.handler = handler;
    }

    @Override
    public void onActivate() {

    }

    public IMenuHandler getHandler() {
        return handler;
    }

    @Override
    public boolean isEnabled() {
        return handler.isEnabled();
    }

    @Override
    public @NotNull Component getText() {
        return handler.getText();
    }
}
