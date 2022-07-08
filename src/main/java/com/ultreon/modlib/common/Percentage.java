package com.ultreon.modlib.common;

import com.ultreon.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.ultreon.modlib.common.interfaces.Formattable;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Percentage implements Formattable {
    private double percentage;

    public Percentage(double value) {
        this.percentage = value * 100;
    }

    public Percentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toFormattedString() {
        return ChatFormatting.BLUE.toString() + Math.round(percentage) + "%";
    }

    public double getPercentage() {
        return percentage;
    }

    public double getValue() {
        return percentage / 100;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setValue(double value) {
        this.percentage = value * 100;
    }

    public Multiplier toMultiplier() {
        return new Multiplier(this.percentage / 100);
    }
}
