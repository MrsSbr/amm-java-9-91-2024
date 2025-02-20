package ru.vsu.amm.java;

import java.util.HashSet;
import java.util.Set;

public class Candies {
    public static void main(String[] args) {
        int[] candyType = {1, 1, 2, 3};
        System.out.println(maxUniqueCandies(candyType));
    }

    public static int maxUniqueCandies(int[] candyType) {
        int n = candyType.length;

        // проверки
        if (n < 2 || n > 10000 || n % 2 != 0) {
            throw new IllegalArgumentException("Error");
        }

        for (int candy : candyType) {
            if (candy < -100000 || candy > 100000) {
                throw new IllegalArgumentException("Error");
            }
        }

        int maxCandiesToEat = n / 2;

        // используем HashSet для хранения уникальных типов конфет
        Set<Integer> uniqueCandies = new HashSet<>();

        // добавляем каждый тип конфет в множество
        for (int candy : candyType) {
            uniqueCandies.add(candy);
        }

        // это количество уникальных типов конфет
        int uniqueCount = uniqueCandies.size();

        System.out.println("Max count of candy: ");
        // это мин значение между кол-вом уникальных типов и количеством конфет
        return Math.min(uniqueCount, maxCandiesToEat);
    }
}
