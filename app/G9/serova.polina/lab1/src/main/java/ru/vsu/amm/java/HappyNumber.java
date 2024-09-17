package ru.vsu.amm.java;

import java.util.HashSet;
import java.util.Set;


public final class HappyNumber {

    private static int sumOfDigitsSquares(int number) {
        int sum = 0;

        while (number > 0) {
            int digit = number % 10;
            sum += digit * digit;
            number /= 10;
        }

        return sum;
    }

    public static boolean isHappy(int number) {
        Set<Integer> previousNumbers = new HashSet<>();
        Boolean isHappy = null;

        do {
            if (number == 1) {
                isHappy = true;
            } else if (previousNumbers.contains(number)) {
                isHappy = false;
            } else {
                previousNumbers.add(number);
                number = sumOfDigitsSquares(number);
            }
        } while (isHappy == null);

        return isHappy;
    }

    public static void main(String[] args) {
        int someNumber = 2147483647;
        System.out.println(isHappy(someNumber));
    }
}