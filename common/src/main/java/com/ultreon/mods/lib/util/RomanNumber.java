package com.ultreon.mods.lib.util;

import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;

import java.util.TreeMap;

@SuppressWarnings("unused")
@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RomanNumber {

    private final static TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(1000000000, "(C)");
        map.put(900000000, "(L)(C)");
        map.put(500000000, "(L)");
        map.put(400000000, "(X)(L)");
        map.put(100000000, "(X)");
        map.put(90000000, "(V)(X)");
        map.put(50000000, "(V)");
        map.put(40000000, "(I)(V)");
        map.put(10000000, "(I)");
        map.put(9000000, "O(I)");
        map.put(5000000, "O");
        map.put(4000000, "SO");
        map.put(1000000, "S");
        map.put(900000, "HS");
        map.put(500000, "H");
        map.put(400000, "QH");
        map.put(100000, "Q");
        map.put(90000, "RQ");
        map.put(50000, "R");
        map.put(40000, "TR");
        map.put(10000, "T");
        map.put(9000, "BT");
        map.put(5000, "B");
        map.put(4000, "MB");
        map.put(1000, "M");
        map.put(900, "DM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

    public static String toRoman(int number) {
        int l = map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number - l);
    }
}