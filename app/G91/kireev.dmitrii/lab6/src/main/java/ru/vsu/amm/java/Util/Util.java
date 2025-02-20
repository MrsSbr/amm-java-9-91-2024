package ru.vsu.amm.java.Util;

import java.util.Random;

public class Util {

    private final static int DEL = 17;
    private final static int CHARS_IN_ALPHABET = 26;
    public static String getRandomString(int size) {
        size %= DEL;
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append((char) ('a' + random.nextInt(CHARS_IN_ALPHABET)));
        }
        return result.toString();
    }
}
