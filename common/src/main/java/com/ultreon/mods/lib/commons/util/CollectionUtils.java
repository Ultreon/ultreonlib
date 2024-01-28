package com.ultreon.mods.lib.commons.util;

import java.util.Collection;
import java.util.Iterator;

public class CollectionUtils {
    public static <T extends Comparable<T>> T max(Collection<T> coll, T def) {
        Iterator<? extends T> i = coll.iterator();
        if (!i.hasNext()) {
            return def;
        }

        T candidate = i.next();

        while (i.hasNext()) {
            T next = i.next();
            if (next.compareTo(candidate) > 0)
                candidate = next;
        }
        return candidate;
    }
}
