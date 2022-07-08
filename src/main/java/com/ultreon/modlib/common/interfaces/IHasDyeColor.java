package com.ultreon.modlib.common.interfaces;

import com.ultreon.modlib.api.annotations.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.DyeColor;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface IHasDyeColor {
    DyeColor getDyeColor();
}
