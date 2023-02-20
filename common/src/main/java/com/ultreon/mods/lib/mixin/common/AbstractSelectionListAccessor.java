package com.ultreon.mods.lib.mixin.common;

import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractSelectionList.class)
public interface AbstractSelectionListAccessor {
    @Accessor
    void setHeight(int height);
    @Accessor
    void setWidth(int width);
    @Accessor("x0")
    void setX0(int x0);
    @Accessor("y0")
    void setY0(int y0);
    @Accessor("x1")
    void setX1(int x1);
    @Accessor("y1")
    void setY1(int y1);
}
