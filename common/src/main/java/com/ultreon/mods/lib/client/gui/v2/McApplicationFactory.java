package com.ultreon.mods.lib.client.gui.v2;

@FunctionalInterface
public interface McApplicationFactory<T extends McApplication> {
    T create();
}
