package com.ultreon.mods.lib.actionmenu;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Supplier;

public class ServerActionMenuItem extends ActionMenuItem {
    private final int permissionLevel;

    public ServerActionMenuItem(ActionMenu parent, String modId, String name) {
        this(parent, modId, name, 4);
    }

    public ServerActionMenuItem(ActionMenu parent, String modId, String name, int permissionLevel) {
        super(parent, modId, name, Component.translatable("misc.unknown"), () -> true, () -> {
        });
        this.permissionLevel = permissionLevel;
    }

    public ServerActionMenuItem(ActionMenu parent, String modId, String name, Component text, Runnable onActivate) {
        this(parent, modId, name, text, onActivate, 4);
    }

    public ServerActionMenuItem(ActionMenu parent, String modId, String name, Component text, Runnable onActivate, int permissionLevel) {
        super(parent, modId, name, text, onActivate);
        this.permissionLevel = permissionLevel;
    }

    public ServerActionMenuItem(ActionMenu parent, String modId, String name, Component text, Supplier<Boolean> enabled, Runnable onActivate) {
        this(parent, modId, name, text, enabled, onActivate, 4);
    }

    public ServerActionMenuItem(ActionMenu parent, String modId, String name, Component text, Supplier<Boolean> enabled, Runnable onActivate, int permissionLevel) {
        super(parent, modId, name, text, enabled, onActivate);
        this.permissionLevel = permissionLevel;
    }

    @Override
    public final void onActivate() {
        throw new UnsupportedOperationException("Can't activate client side while being a server side instance.");
    }

    public void onActivate(ServerPlayer player) {
        onActivate.run();
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }
}
