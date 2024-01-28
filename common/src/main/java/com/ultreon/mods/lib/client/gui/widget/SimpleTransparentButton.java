package com.ultreon.mods.lib.client.gui.widget;

import net.minecraft.network.chat.Component;

public class SimpleTransparentButton extends TransparentButton<SimpleTransparentButton> {
    public SimpleTransparentButton(Component title, Callback<SimpleTransparentButton> action) {
        super(title, action);
    }

    public SimpleTransparentButton(Component title, Callback<SimpleTransparentButton> action, TooltipFactory<SimpleTransparentButton> factory) {
        super(title, action, factory);
    }
}
