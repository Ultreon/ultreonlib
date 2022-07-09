package com.ultreon.modlib.utils.helpers;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public final class MathHelper extends UtilityClass {
    public static int getDecimalPlaces(Float d) {
        String s = d.toString();
        String[] split = s.split("\\.");
        if (split.length == 1) {
            return 0;
        }

        return split[1].length();
    }

    public static int getDecimalPlaces(Double d) {
        String s = d.toString();
        String[] split = s.split("\\.");
        if (split.length == 1) {
            return 0;
        }

        return split[1].length();
    }

    public static int getDecimalPlaces(BigDecimal d) {
        String s = d.toString();
        String[] split = s.split("\\.");
        if (split.length == 1) {
            return 0;
        }

        return split[1].length();
    }
}
