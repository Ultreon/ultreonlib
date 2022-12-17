package com.ultreon.mods.lib.core.common.interfaces;

import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface ModifyRenderType {
    @Environment(EnvType.CLIENT)
    RenderType getRenderType();
}
