package com.ultreon.mods.lib.core.common;

import com.ultreon.mods.lib.core.common.interfaces.Formattable;
import net.minecraft.ChatFormatting;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("unused")
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FormattableObject implements Formattable {
    @Override
    public String toFormattedString() {
        return ChatFormatting.AQUA + getClass().getPackage().getName().replaceAll("\\.", ChatFormatting.GRAY + "." + ChatFormatting.AQUA) +
                ChatFormatting.GRAY + "." +
                ChatFormatting.DARK_AQUA + getClass().getSimpleName() +
                ChatFormatting.GRAY + "@" +
                ChatFormatting.GREEN + Integer.toHexString(hashCode());
    }
}
