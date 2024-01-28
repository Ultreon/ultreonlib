package com.ultreon.mods.lib.client.gui;

import net.minecraft.resources.ResourceLocation;

public enum FrameType {
    NORMAL(""), @Deprecated BORDER("_border"), EXTEND("_extend"), INVERTED("_inverted"), MENU("_menu");

    private final String suffix;

    FrameType(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public ResourceLocation mapSprite(ResourceLocation themeId) {
        return new ResourceLocation(themeId.getNamespace(), themeId.getPath() + suffix);
    }
}
