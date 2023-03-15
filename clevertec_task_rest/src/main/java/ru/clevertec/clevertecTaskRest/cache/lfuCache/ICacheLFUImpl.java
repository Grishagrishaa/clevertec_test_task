package ru.clevertec.clevertecTaskRest.cache.lfuCache;

import ru.clevertec.clevertecTaskRest.cache.api.ICache;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class ICacheLFUImpl<K, V> implements ICache<K, V> {
    private final int size;
    private final Map<K, V> valueMap;
    private final Map<K, Long> countMap;
    private final Map<Long, LinkedHashSet<K>> frequency;
    private long unused = -1;
    private final ReentrantReadWriteLock lock;

    public ICacheLFUImpl(int size) {
        this.size = size;
        this.valueMap = new ConcurrentHashMap<>();
        this.countMap = new ConcurrentHashMap<>();
        this.frequency = new ConcurrentHashMap<>();
        this.frequency.put(1L, new LinkedHashSet<>());
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public V put(K key, V value) {
        this.lock.writeLock().lock();
        try {
            if (size == 0) return value;

            if (valueMap.containsKey(key)) return valueMap.put(key, value);

            if (valueMap.size() >= size) {
                K keyToEvict = frequency.get(unused).iterator().next();
                frequency.get(unused).remove(keyToEvict);
                countMap.remove(keyToEvict);
                valueMap.remove(keyToEvict);
            }

            unused = 1;
            valueMap.put(key, value);
            countMap.put(key, 1L);
            frequency.get(unused).add(key);
            return value;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public Optional<V> get(K key) {
        this.lock.readLock().lock();
        try {
            if (!valueMap.containsKey(key)) return Optional.empty();

            long count = countMap.get(key);

            countMap.put(key, count + 1);
            frequency.get(count).remove(key);

            if (count == unused && frequency.get(count).size() == 0) unused++;
            if (!frequency.containsKey(count + 1)) frequency.put(count + 1, new LinkedHashSet<>());

            frequency.get(count + 1).add(key);
            return Optional.of(valueMap.get(key));
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public boolean remove(K key) {
        this.lock.writeLock().lock();
        try {
            if (!valueMap.containsKey(key)) return false;

            long count = countMap.get(key);
            frequency.get(count).remove(key);

            if (count == unused && frequency.get(unused).size() == 0) {
                frequency.remove(count);
                unused = frequency.keySet().stream()
                        .mapToLong(Long::longValue)
                        .min()
                        .getAsLong();
            }

            countMap.remove(key);
            valueMap.remove(key);
            return true;
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}