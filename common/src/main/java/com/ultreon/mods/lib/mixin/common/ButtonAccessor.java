package com.ultreon.mods.lib.mixin.common;

import net.minecraft.client.gui.components.Button;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Button.class)
public interface ButtonAccessor {
    @Accessor("onPress")
    void setOnPress(Button.OnPress onPress);
    @Accessor("onPress")
    Button.OnPress getOnPress();
}
