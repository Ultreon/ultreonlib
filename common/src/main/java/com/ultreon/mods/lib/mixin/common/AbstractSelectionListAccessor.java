package com.ultreon.mods.lib.mixin.common;

import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractWidget.class)
public interface AbstractSelectionListAccessor {
    @Accessor
    void setHeight(int height);
    @Accessor
    void setWidth(int width);
}
