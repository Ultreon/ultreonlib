package com.ultreon.mods.lib.core.common;

import com.ultreon.mods.lib.core.common.interfaces.Formattable;
import net.minecraft.ChatFormatting;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record Angle(double degrees) implements Formattable {
    public String toFormattedString() {
        return ChatFormatting.BLUE.toString() + this.degrees + '\u00b0';
    }
}
