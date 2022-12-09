package com.ultreon.mods.lib.core.common.interfaces;

import com.ultreon.mods.lib.core.client.render.Gfx;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface Renderable {
    void render(Gfx mcg);
}
