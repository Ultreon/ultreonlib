package com.ultreon.mods.lib.commons.collection.map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

@SuppressWarnings("SuspiciousMethodCalls")
public class OrderedMap<K, V> extends AbstractMap<K, V> {
    private final List<K> keys = new ArrayList<>();
    private final List<V> values = new ArrayList<>();
    private final Object lock = new Object();

    @Override
    public int size() {
        synchronized (lock) {
            return keys.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (lock) {
            return keys.isEmpty();
        }
    }

    public boolean contains(Entry<K, V> o) {
        return entrySet().contains(o);
    }

    @Override
    public boolean containsKey(Object key) {
        synchronized (lock) {
            return keys.contains(key);
        }
    }

    @Override
    public boolean containsValue(Object value) {
        synchronized (lock) {
            return values.contains(value);
        }
    }

    public boolean containsAll(@NotNull Collection<Entry<K, V>> c) {
        synchronized (lock) {
            return entrySet().containsAll(c);
        }
    }

    @Override
    public V get(Object key) {
        synchronized (lock) {
            int index = keys.indexOf(key);
            if (index == -1) {
                return null;
            }
            return values.get(index);
        }
    }

    @Override
    public V remove(Object key) {
        synchronized (lock) {
            int index = keys.indexOf(key);
            if (index == -1) {
                return null;
            }
            keys.remove(index);
            return values.remove(index);
        }
    }

    public Entry<K, V> remove(int index) {
        synchronized (lock) {
            K k = keys.get(index);
            V v = values.get(index);

            keys.remove(index);
            values.remove(index);
            return new AbstractMap.SimpleEntry<>(k, v);
        }
    }

    public Entry<K, V> removeFirst() {
        synchronized (lock) {
            if (keys.isEmpty()) {
                return null;
            }
            return remove(0);
        }
    }

    public Entry<K, V> removeLast() {
        synchronized (lock) {
            if (keys.isEmpty()) {
                return null;
            }
            return remove(keys.size() - 1);
        }
    }

    public boolean removeAll(@NotNull Collection<? extends Entry<K, V>> c) {
        boolean changed = false;
        synchronized (lock) {
            for (Entry<K, V> entry : c) {
                if (remove(entry.getKey()) != null) {
                    changed = true;
                }
            }
        }
        return changed;
    }

    public boolean addAll(@NotNull Collection<? extends Entry<K, V>> c) {
        synchronized (lock) {
            boolean changed = false;
            for (Entry<K, V> entry : c) {
                if (put(entry.getKey(), entry.getValue()) != null) {
                    changed = true;
                }
            }
            return changed;
        }
    }

    public boolean addAll(int index, @NotNull Collection<? extends Entry<K, V>> c) {
        synchronized (lock) {
            boolean changed = false;
            for (Entry<K, V> entry : c) {
                if (put(index, entry.getKey(), entry.getValue()) != null) {
                    changed = true;
                }
                index++;
            }
            return changed;
        }
    }

    public boolean retainAll(@NotNull Collection<?> c) {
        boolean changed = false;
        synchronized (lock) {
            for (Iterator<Entry<K, V>> it = entrySet().iterator(); it.hasNext(); ) {
                Entry<K, V> entry = it.next();
                if (!c.contains(entry)) {
                    it.remove();
                    changed = true;
                }
            }
        }
        return changed;
    }

    public int indexOf(Entry<K, V> entry) {
        synchronized (lock) {
            return keys.indexOf(entry.getKey());
        }
    }

    public int indexOfKey(K key) {
        synchronized (lock) {
            return keys.indexOf(key);
        }
    }

    public int indexOfValue(V value) {
        synchronized (lock) {
            return values.indexOf(value);
        }
    }

    public int lastIndexOf(Entry<K, V> o) {
        synchronized (lock) {
            return keys.lastIndexOf(o.getKey());
        }
    }

    public int lastIndexOfKey(K key) {
        synchronized (lock) {
            return keys.lastIndexOf(key);
        }
    }

    public int lastIndexOfValue(V value) {
        synchronized (lock) {
            return values.lastIndexOf(value);
        }
    }

    @NotNull
    public ListIterator<Entry<K, V>> listIterator() {
        return entryList().listIterator();
    }

    @NotNull
    public Iterator<Entry<K, V>> iterator() {
        return entrySet().iterator();
    }

    public Entry<?, ?>[] toArray() {
        return entrySet().toArray(new Entry[]{});
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        synchronized (lock) {
            return Set.copyOf(keys);
        }
    }

    @NotNull
    @Override
    public Collection<V> values() {
        synchronized (lock) {
            return List.copyOf(values);
        }
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        synchronized (lock) {
            Set<Entry<K, V>> set = new HashSet<>();
            for (int i = 0; i < keys.size(); i++) {
                set.add(new AbstractMap.SimpleEntry<>(keys.get(i), values.get(i)));
            }
            return Set.copyOf(set);
        }
    }

    @NotNull
    public List<Entry<K, V>> entryList() {
        synchronized (lock) {
            List<Entry<K, V>> entries = new ArrayList<>();
            for (int i = 0; i < keys.size(); i++) {
                entries.add(new AbstractMap.SimpleEntry<>(keys.get(i), values.get(i)));
            }
            return entries;
        }
    }

    @NotNull
    public ListIterator<Entry<K, V>> listIterator(int index) {
        return entryList().listIterator(index);
    }

    @NotNull
    public List<Entry<K, V>> subList(int fromIndex, int toIndex) {
        return entryList().subList(fromIndex, toIndex);
    }

    @Override
    public void clear() {
        synchronized (lock) {
            keys.clear();
            values.clear();
        }
    }

    public Entry<K, V> set(int index, K key, V value) {
        synchronized (lock) {
            if (index >= keys.size()) throw new IndexOutOfBoundsException(index + " >= " + keys.size());
            if (index < 0) throw new IndexOutOfBoundsException(index + " < 0");

            int oldKeyIndex = indexOfKey(key);
            if (oldKeyIndex != -1) remove(oldKeyIndex);

            if (oldKeyIndex == index) {
                K oldKey = keys.get(oldKeyIndex);
                V oldValue = values.get(oldKeyIndex);

                keys.add(index, key);
                values.add(index, value);
                return new AbstractMap.SimpleEntry<>(oldKey, oldValue);
            }

            K oldKey = keys.set(index, key);
            V oldValue = values.set(index, value);

            return new AbstractMap.SimpleEntry<>(oldKey, oldValue);
        }
    }

    public Entry<K, V> set(int index, Entry<K, V> element) {
        return set(index, element.getKey(), element.getValue());
    }

    @NotNull
    public Entry<K, V> get(int index) {
        synchronized (lock) {
            if (index >= keys.size()) throw new IndexOutOfBoundsException(index + " >= " + keys.size());
            if (index < 0) throw new IndexOutOfBoundsException(index + " < 0");

            return new AbstractMap.SimpleEntry<>(keys.get(index), values.get(index));
        }
    }

    public K getKey(int index) {
        synchronized (lock) {
            return keys.get(index);
        }
    }

    public V getValue(int index) {
        synchronized (lock) {
            return values.get(index);
        }
    }

    public void setKey(int index, K key) {
        synchronized (lock) {
            keys.set(index, key);
        }
    }

    public void setValue(int index, V value) {
        synchronized (lock) {
            values.set(index, value);
        }
    }

    public K getKeyBy(V value) {
        synchronized (lock) {
            return getKey(indexOfValue(value));
        }
    }

    public V getValueBy(K key) {
        synchronized (lock) {
            return getValue(indexOfKey(key));
        }
    }

    private Entry<K, V> getFirst() {
        synchronized (lock) {
            if (keys.isEmpty()) {
                return null;
            }
            return new AbstractMap.SimpleEntry<>(keys.get(0), values.get(0));
        }
    }

    public Entry<K, V> getLast() {
        synchronized (lock) {
            if (keys.isEmpty()) {
                return null;
            }
            return new AbstractMap.SimpleEntry<>(keys.get(keys.size() - 1), values.get(values.size() - 1));
        }
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        synchronized (lock) {
            if (containsKey(key)) {
                remove(key);
            }
            int index = keys.indexOf(key);
            if (index == -1) {
                keys.add(key);
                values.add(value);
                return null;
            }
            V oldValue = values.get(index);
            values.set(index, value);
            return oldValue;
        }
    }

    public boolean put(Entry<K, V> kvEntry) {
        return put(kvEntry.getKey(), kvEntry.getValue()) != null;
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        synchronized (lock) {
            for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public Entry<K, V> put(int index, K key, V value) {
        synchronized (lock) {
            if (index > keys.size()) throw new IndexOutOfBoundsException(index + " > " + keys.size());
            if (index < 0) throw new IndexOutOfBoundsException(index + " < 0");

            Entry<K, V> oldEntry = get(index);
            if (containsKey(key)) {
                remove(key);
            }
            keys.add(index, key);
            values.add(index, value);
            return oldEntry;
        }
    }

    public Entry<K, V> putFirst(K key, V value) {
        synchronized (lock) {
            Entry<K, V> oldEntry = getFirst();
            if (containsKey(key)) {
                remove(key);
            }
            keys.add(0, key);
            values.add(0, value);
            return oldEntry;
        }
    }

    public Entry<K, V> putLast(K key, V value) {
        synchronized (lock) {
            Entry<K, V> oldEntry = getLast();
            if (containsKey(key)) {
                remove(key);
            }
            keys.add(key);
            values.add(value);
            return oldEntry;
        }
    }

    public void removeIf(Predicate<Entry<K, V>> predicate) {
        synchronized (lock) {
            for (Entry<K, V> entry : entrySet()) {
                if (predicate.test(entry)) {
                    remove(entry.getKey());
                }
            }
        }
    }
}
