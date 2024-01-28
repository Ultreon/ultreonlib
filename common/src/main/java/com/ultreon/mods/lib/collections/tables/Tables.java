package com.ultreon.mods.lib.collections.tables;

import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 0.2.0
 * @author <a href="https://github.com/XyperCodee">XyperCode</a>
 */
public final class Tables {
    private Tables() {
        throw new UnsupportedOperationException("Instantiation of utility class");
    }

    public static <R, C, V> Table<R, C, V> hashTableOf(Table<R, C, V> table) {
        return new HashTable<>(table);
    }

    public static <R, C, V> Table<R, C, V> emptyTable() {
        return new AbstractTable<R, C, V>() {
            @Override
            public Set<Cell<R, C, V>> cellSet() {
                return new HashSet<>();
            }
        };
    }

    public static <R, C, V> Table<R, C, V> unmodifiableTable(Table<R, C, V> table) {
        return new UnmodifiableTable<>(table);
    }

    private static class UnmodifiableTable<R, C, V> extends AbstractTable<R, C, V> {
        private final Table<R, C, V> table;

        public UnmodifiableTable(Table<R, C, V> table) {
            this.table = table;
        }

        @Override
        public V put(R row, C column, V value) {
            throw new UnsupportedOperationException("Unmodifiable table");
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("Unmodifiable table");
        }

        @Override
        public @Nullable V remove(R row, C column) {
            throw new UnsupportedOperationException("Unmodifiable table");
        }

        @Override
        public Set<Cell<R, C, V>> cellSet() {
            return this.table.cellSet();
        }
    }
}
