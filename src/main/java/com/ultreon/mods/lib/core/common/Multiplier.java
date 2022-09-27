package com.ultreon.mods.lib.core.common;

import com.ultreon.mods.lib.core.common.interfaces.Formattable;
import com.ultreon.mods.lib.core.util.Meth;
import net.minecraft.ChatFormatting;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("unused")
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record Multiplier(double value) implements Formattable {

    @Override
    public String toFormattedString() {
        if (Meth.getDecimalPlaces(value) == 0) {
            return ChatFormatting.GOLD.toString() + Math.round(value) + ChatFormatting.GRAY + "x";
        }

        return ChatFormatting.GOLD.toString() + value + ChatFormatting.GRAY + "x";
    }

    public Percentage percentage() {
        return new Percentage(value);
    }
}
