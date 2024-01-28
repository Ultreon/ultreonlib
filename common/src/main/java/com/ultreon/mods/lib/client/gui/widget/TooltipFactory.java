package com.ultreon.mods.lib.client.gui.widget;

import net.minecraft.client.gui.components.Tooltip;

@FunctionalInterface
public interface TooltipFactory<T extends ULibWidget> {
    Tooltip create(T caller);
}
