package ru.vsu.amm.java.Model;

public record CacheEntity<V>(V value, Long timeStamp) {
}
