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
    private final int WORD_SIZE = 2;

    public Scheduler(int numThreads, int capacity) {
        this.writers = new ArrayList<>();
        this.cache = new LRUCache<>(capacity);


        Runnable taskProcessor = () -> {

            String value = Util.getRandomString(WORD_SIZE);
            Optional<String> cacheValue = cache.get(value);

            if (cacheValue.isEmpty()) {
                cache.put(value);
                System.out.println("putting elem with value: " + value + "   cache size: " + cache.size());
            } else {
                System.out.println("getting elem with value: " + value + "   cache size: " + cache.size());
            }

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
