package com.ultreon.mods.lib.client.gui.v2;

import net.minecraft.resources.ResourceLocation;

public interface IIcon {
    ResourceLocation resource();
    int width();
    int height();
    int vHeight();
    int uWidth();
    int v();
    int u();
    int texWidth();
    int texHeight();
}
