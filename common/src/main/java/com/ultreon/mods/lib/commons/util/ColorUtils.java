package com.ultreon.mods.lib.commons.util;

import com.ultreon.mods.lib.commons.Color;
import com.ultreon.mods.lib.commons.UtilityClass;

public final class ColorUtils extends UtilityClass {
    public static Color[] extractMultiHex(String... hexes) {
        Color[] colors = new Color[hexes.length];

        for (int i = 0, hexesLength = hexes.length; i < hexesLength; i++)
            colors[i] = Color.hex(hexes[i]);

        return colors;
    }

    /**
     * Parse a color string into a color array.<br>
     *
     * @param hexList the color string, hex colors separated by a comma.
     * @return an array create colors parsed from the color string.
     */
    public static Color[] parseHexList(String hexList) {
        String[] strings = hexList.split(",");
        Color[] colors = new Color[strings.length];
        for (int i = 0; i < strings.length; i++) {
            String s = strings[i];
            colors[i] = Color.hex(s.startsWith("#") ? s : "#" + s);
        }

        return colors;
    }
}
