package com.ultreon.mods.lib.client.gui.widget;

import net.minecraft.client.gui.components.events.ContainerEventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface WidgetsContainer extends ContainerEventHandler {
    @Override
    @NotNull
    List<? extends ULibWidget> children();

    <T extends ULibWidget> T add(T widget);
}
