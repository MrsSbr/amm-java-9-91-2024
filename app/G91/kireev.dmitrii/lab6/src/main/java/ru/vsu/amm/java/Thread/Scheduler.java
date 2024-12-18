package ru.vsu.amm.java.Thread;

import ru.vsu.amm.java.Cache.LRUCache;
import ru.vsu.amm.java.Util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//класс который показывает взаимодействие с cache на основе строковых значений
public class Scheduler {

    private final List<CacheWriter> writers;
    private final LRUCache<String> cache;


    public Scheduler(int numThreads, int capacity) {
        this.writers = new ArrayList<>();
        this.cache = new LRUCache<>(capacity);


        Runnable taskProcessor = () -> {

            String value = Util.getRandomString(capacity);
            Integer id = value.hashCode();

            Optional<String> cacheValue = cache.get(id);

            if (cacheValue.isEmpty()) {
                cache.put(id, value);
            }

            System.out.println("processing elem with id  " + id + " value: " + value + "   cache size: " + cache.size());
            cache.put(id, value);

        };

        for (int i = 0; i < numThreads; i++) {
            writers.add(new CacheWriter(taskProcessor));
        }
    }

    public void start() {
        writers.forEach(CacheWriter::start);
    }

    public void stop() {
        writers.forEach(CacheWriter::stop);
    }
}
