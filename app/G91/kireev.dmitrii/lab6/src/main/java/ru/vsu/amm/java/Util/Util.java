package ru.vsu.amm.java.Util;

import java.util.Random;

public class Util {

    public static String getRandomString(int size) {
        size %= 17;
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append((char) ('a' + random.nextInt(26)));
        }
        return result.toString();
    }
}
