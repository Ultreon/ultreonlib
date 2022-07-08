package com.ultreon.modlib.common.interfaces;

import com.ultreon.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.ultreon.modlib.client.render.Renderer;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface Renderable {
    void render(Renderer mcg);
}
