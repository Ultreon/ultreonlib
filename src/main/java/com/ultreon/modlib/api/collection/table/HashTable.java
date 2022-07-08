package com.ultreon.modlib.api.collection.table;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class HashTable<R, C, V> extends AbstractTable<R, C, V> {
    private final Map<R, Map<C, V>> rowMap = new HashMap<>();
    private final Map<C, Map<R, V>> columnMap = new HashMap<>();

    public HashTable() {

    }

    public HashTable(Table<R, C, V> table) {
        for (R row : table.rowSet()) {
            for (C column : table.columnSet(row)) {
                put(row, column, table.get(row, column));
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
        return columnSet().size();
    }

    @Override
    public int rowSize() {
        return rowSet().size();
    }

    @Override
    public V get(R row, C column) {
        return rowMap.getOrDefault(row, Collections.emptyMap()).get(column);
    }

    @Override
    public V getOrDefault(R row, C column, V defaultValue) {
        return rowMap.getOrDefault(row, Collections.emptyMap()).getOrDefault(column, defaultValue);
    }

    @Nullable
    @Override
    public V put(R row, C column, V value) {
        V v = get(row, column); // get the old value
        rowMap.computeIfAbsent(row, k -> new HashMap<>()).put(column, value);
        columnMap.computeIfAbsent(column, k -> new HashMap<>()).put(row, value);
        return v;
    }

    @Override
    public @NotNull Map<C, V> row(R row) {
        return Collections.unmodifiableMap(rowMap.get(row));
    }

    @Override
    public @NotNull Map<R, V> column(C column) {
        return Collections.unmodifiableMap(columnMap.get(column));
    }

    @Override
    public Set<R> rowSet() {
        return Collections.unmodifiableSet(rowMap.keySet());
    }

    @Override
    public Set<C> columnSet() {
        return Collections.unmodifiableSet(columnMap.keySet());
    }

    @Override
    public Set<R> rowSet(C column) {
        return Collections.unmodifiableSet(columnMap.getOrDefault(column, Collections.emptyMap()).keySet());
    }

    @Override
    public Set<C> columnSet(R row) {
        return Collections.unmodifiableSet(rowMap.getOrDefault(row, Collections.emptyMap()).keySet());
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (R row : rowMap.keySet()) {
            for (C column : rowMap.get(row).keySet()) {
                values.add(rowMap.get(row).get(column));
            }
        }
        return Collections.unmodifiableList(values);
    }

    @Override
    public Map<Index<R, C>, V> toMap() {
        Map<Index<R, C>, V> map = new HashMap<>();
        for (R row : rowMap.keySet()) {
            for (C column : rowMap.get(row).keySet()) {
                map.put(index(row, column), rowMap.get(row).get(column));
            }
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public @NotNull Map<R, Map<C, V>> toRowMap() {
        Map<R, Map<C, V>> map = new HashMap<>();
        for (R row : rowMap.keySet()) {
            map.put(row, Collections.unmodifiableMap(rowMap.get(row)));
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public @NotNull Map<C, Map<R, V>> toColumnMap() {
        Map<C, Map<R, V>> map = new HashMap<>();
        for (C column : columnMap.keySet()) {
            map.put(column, Collections.unmodifiableMap(columnMap.get(column)));
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public @Nullable V remove(R row, C column) {
        V value = rowMap.getOrDefault(row, Collections.emptyMap()).remove(column);
        if (value != null) {
            columnMap.getOrDefault(column, Collections.emptyMap()).remove(row);
        }
        return value;
    }

    @Override
    public void clear() {
        rowMap.clear();
        columnMap.clear();
    }

    @Override
    public boolean isEmpty() {
        return rowMap.isEmpty() && columnMap.isEmpty();
    }

    @Override
    public boolean contains(R row, C column) {
        return rowMap.containsKey(row) && columnMap.containsKey(column);
    }

    @Override
    public String toString() {
        return toMap().toString();
    }

    @Override
    public Set<Cell<R, C, V>> cellSet() {
        Set<Cell<R, C, V>> cells = new HashSet<>();
        for (R row : rowMap.keySet()) {
            for (C column : rowMap.get(row).keySet()) {
                cells.add(cell(row, column, rowMap.get(row).get(column)));
            }
        }
        return Set.copyOf(cells);
    }

    @Override
    public Set<Index<R, C>> indexSet() {
        Set<SimpleIndex<R, C>> indices = new HashSet<>();
        for (R row : rowMap.keySet()) {
            for (C column : rowMap.get(row).keySet()) {
                indices.add(new SimpleIndex<>(row, column));
            }
        }
        return Set.copyOf(indices);
    }

    @Override
    public boolean containsAll(Table<R, C, V> table) {
        return table.cellSet().stream().allMatch(this::contains);
    }
}
