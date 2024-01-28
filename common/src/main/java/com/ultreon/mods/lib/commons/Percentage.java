package com.ultreon.mods.lib.commons;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Percentage utility object.
 *
 * @author Qboi
 */
public class Percentage implements Serializable, Comparable<Percentage> {
    private final double percentage;

    public double percentage() {
        return this.percentage;
    }

    private static final long serialVersionUID = 0L;

    public Percentage(double percentage) {
        this.percentage = percentage;
    }

    public static Percentage toPercentage(double value) {
        return new Percentage(value * 100);
    }

    public double value() {
        return this.percentage / 100;
    }

    @Override
    public int compareTo(@NotNull Percentage o) {
        return Double.compare(this.percentage, o.percentage);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Percentage that = (Percentage) obj;
        return Double.doubleToLongBits(this.percentage) == Double.doubleToLongBits(that.percentage);
    }

    @Override
    public String toString() {
        return "Percentage[" +
                "percentage=" + this.percentage + ']';
    }

}
