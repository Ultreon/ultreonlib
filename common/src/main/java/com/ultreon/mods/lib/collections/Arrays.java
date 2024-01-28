package com.ultreon.mods.lib.collections;

import java.lang.reflect.Array;

public class Arrays {
    @SuppressWarnings("unchecked")
    public static <T> T[] add(T[] array, T element) {
        Class<?> type;

        if (array != null)
            type = array.getClass().getComponentType();
        else if (element != null)
            type = element.getClass();
        else 
            throw new IllegalArgumentException("Arguments can't both be null");

        T[] newArray = (T[]) copyAndGrow(array, type);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    private static Object copyAndGrow(Object array, Class<?> newType) {
        if (array != null) {
            int len = Array.getLength(array);
            Object newArray = Array.newInstance(array.getClass().getComponentType(), len + 1);
            System.arraycopy(array, 0, newArray, 0, len);
            return newArray;
        }

        return Array.newInstance(newType, 1);
    }
}
