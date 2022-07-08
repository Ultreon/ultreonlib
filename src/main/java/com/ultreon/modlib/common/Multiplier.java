package com.ultreon.modlib.common;

import com.ultreon.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.ultreon.modlib.common.interfaces.Formattable;
import com.ultreon.modlib.utils.helpers.MathHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("unused")
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record Multiplier(double value) implements Formattable {

    @Override
    public String toFormattedString() {
        if (MathHelper.getDecimalPlaces(value) == 0) {
            return ChatFormatting.GOLD.toString() + Math.round(value) + ChatFormatting.GRAY + "x";
        }

        return ChatFormatting.GOLD.toString() + value + ChatFormatting.GRAY + "x";
    }

    public Percentage percentage() {
        return new Percentage(value);
    }
}
