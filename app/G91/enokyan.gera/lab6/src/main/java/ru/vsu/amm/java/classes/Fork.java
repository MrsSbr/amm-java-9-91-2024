package ru.vsu.amm.java.classes;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    private final Lock lock = new ReentrantLock();

    public boolean tryPickUp() {
        return lock.tryLock();
    }

    public void putDown() throws IllegalMonitorStateException {
        lock.unlock();
    }
}
