package com.ultreon.mods.lib.collections.tables;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Hash table implementation of {@link Table}.
 *
 * @param <R>
 * @param <C>
 * @param <V>
 * @since 0.2.0
 * @author <a href="https://github.com/XyperCodee">XyperCode</a>
 */
public class HashTable<R, C, V> extends AbstractTable<R, C, V> {
    private final Map<R, Map<C, V>> rowMap = new HashMap<>();
    private final Map<C, Map<R, V>> columnMap = new HashMap<>();

    public HashTable() {

    }

    public HashTable(Table<R, C, V> table) {
        for (R row : table.rowSet()) {
            for (C column : table.columnSet(row)) {
                this.put(row, column, table.get(row, column));
            }
        }
    }

    public static <R, C, V> HashTable<R, C, V> ofRowMap(Map<R, Map<C, V>> rowMap) {
        HashTable<R, C, V> table = new HashTable<>();
        for (R row : rowMap.keySet()) {
            for (C column : rowMap.get(row).keySet()) {
                table.put(row, column, rowMap.get(row).get(column));
            }
        }
        return table;
    }

    public static <R, C, V> HashTable<R, C, V> ofColumnMap(Map<C, Map<R, V>> columnMap) {
        HashTable<R, C, V> table = new HashTable<>();
        for (C column : columnMap.keySet()) {
            for (R row : columnMap.get(column).keySet()) {
                table.put(row, column, columnMap.get(column).get(row));
            }
        }
        return table;
    }

    public static <R, C, V> HashTable<R, C, V> of(Table<R, C, V> table) {
        return new HashTable<>(table);
    }

    @Override
    public int columnSize() {
        return this.columnSet().size();
    }

    @Override
    public int rowSize() {
        return this.rowSet().size();
    }

    @Override
    public V get(R row, C column) {
        return this.rowMap.getOrDefault(row, Collections.emptyMap()).get(column);
    }

    @Override
    public V getOrDefault(R row, C column, V defaultValue) {
        return this.rowMap.getOrDefault(row, Collections.emptyMap()).getOrDefault(column, defaultValue);
    }

    @Nullable
    @Override
    public V put(R row, C column, V value) {
        V v = this.get(row, column); // get the old value
        this.rowMap.computeIfAbsent(row, k -> new HashMap<>()).put(column, value);
        this.columnMap.computeIfAbsent(column, k -> new HashMap<>()).put(row, value);
        return v;
    }

    @Override
    public @NotNull Map<C, V> row(R row) {
        return Collections.unmodifiableMap(this.rowMap.get(row));
    }

    @Override
    public @NotNull Map<R, V> column(C column) {
        return Collections.unmodifiableMap(this.columnMap.get(column));
    }

    @Override
    public Set<R> rowSet() {
        return Collections.unmodifiableSet(this.rowMap.keySet());
    }

    @Override
    public Set<C> columnSet() {
        return Collections.unmodifiableSet(this.columnMap.keySet());
    }

    @Override
    public Set<R> rowSet(C column) {
        return Collections.unmodifiableSet(this.columnMap.getOrDefault(column, Collections.emptyMap()).keySet());
    }

    @Override
    public Set<C> columnSet(R row) {
        return Collections.unmodifiableSet(this.rowMap.getOrDefault(row, Collections.emptyMap()).keySet());
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (R row : this.rowMap.keySet()) {
            for (C column : this.rowMap.get(row).keySet()) {
                values.add(this.rowMap.get(row).get(column));
            }
        }
        return Collections.unmodifiableList(values);
    }

    @Override
    public Map<Index<R, C>, V> toMap() {
        Map<Index<R, C>, V> map = new HashMap<>();
        for (R row : this.rowMap.keySet()) {
            for (C column : this.rowMap.get(row).keySet()) {
                map.put(index(row, column), this.rowMap.get(row).get(column));
            }
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public @NotNull Map<R, Map<C, V>> toRowMap() {
        Map<R, Map<C, V>> map = new HashMap<>();
        for (R row : this.rowMap.keySet()) {
            map.put(row, Collections.unmodifiableMap(this.rowMap.get(row)));
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public @NotNull Map<C, Map<R, V>> toColumnMap() {
        Map<C, Map<R, V>> map = new HashMap<>();
        for (C column : this.columnMap.keySet()) {
            map.put(column, Collections.unmodifiableMap(this.columnMap.get(column)));
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public @Nullable V remove(R row, C column) {
        V value = this.rowMap.getOrDefault(row, Collections.emptyMap()).remove(column);
        if (value != null) {
            this.columnMap.getOrDefault(column, Collections.emptyMap()).remove(row);
        }
        return value;
    }

    @Override
    public void clear() {
        this.rowMap.clear();
        this.columnMap.clear();
    }

    @Override
    public boolean isEmpty() {
        return this.rowMap.isEmpty() && this.columnMap.isEmpty();
    }

    @Override
    public boolean contains(R row, C column) {
        return this.rowMap.containsKey(row) && this.columnMap.containsKey(column);
    }

    @Override
    public String toString() {
        return this.toMap().toString();
    }

    @Override
    public Set<Cell<R, C, V>> cellSet() {
        Set<Cell<R, C, V>> cells = new HashSet<>();
        for (R row : this.rowMap.keySet()) {
            for (C column : this.rowMap.get(row).keySet()) {
                cells.add(cell(row, column, this.rowMap.get(row).get(column)));
            }
        }
        return Collections.unmodifiableSet(cells);
    }

    @Override
    public Set<Index<R, C>> indexSet() {
        Set<SimpleIndex<R, C>> indices = new HashSet<>();
        for (R row : this.rowMap.keySet()) {
            for (C column : this.rowMap.get(row).keySet()) {
                indices.add(new SimpleIndex<>(row, column));
            }
        }
        return Collections.unmodifiableSet(indices);
    }

}
