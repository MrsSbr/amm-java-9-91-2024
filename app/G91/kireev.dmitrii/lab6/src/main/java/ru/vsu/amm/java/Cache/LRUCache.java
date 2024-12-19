package ru.vsu.amm.java.Cache;

import ru.vsu.amm.java.Model.CacheEntity;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class LRUCache<V> {

    private final int capacity;
    private final Map<Integer, CacheEntity<V>> cache;
    private final ReentrantLock lock;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 1, true);
        this.lock = new ReentrantLock();
    }

    public void put( V value) {

        lock.lock();
        try {
            Long timestamp = System.nanoTime();
            if (cache.size() >= capacity) {
                Iterator<Map.Entry<Integer, CacheEntity<V>>> iterator = cache.entrySet().iterator();
                iterator.next();
                iterator.remove();
            }
            cache.put(value.hashCode(), new CacheEntity<>(value, timestamp));
        } finally {
            lock.unlock();
        }
    }

    public Optional<V> get(V value) {
        lock.lock();
        try {
            Long timestamp = System.nanoTime();
            if (!cache.containsKey(value.hashCode())) {
                return Optional.empty();
            }
            //при попытке доступа к элементу linkedHashMap он сдвигается в конец её списка, поэтому не удалится при put
            cache.replace(value.hashCode(),new CacheEntity<>(value,timestamp));
            return Optional.of(cache.get(value.hashCode()).value());
        } finally {
            lock.unlock();
        }

    }

    public int size() {
        lock.lock();
        try {
            return cache.size();
        } finally {
            lock.unlock();
        }
    }

}
