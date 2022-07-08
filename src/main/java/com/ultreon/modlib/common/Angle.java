package com.ultreon.modlib.common;

import com.ultreon.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.ultreon.modlib.common.interfaces.Formattable;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record Angle(double degrees) implements Formattable {
    public String toFormattedString() {
        return ChatFormatting.BLUE.toString() + this.degrees + ((char) 0xb0);
    }
}
