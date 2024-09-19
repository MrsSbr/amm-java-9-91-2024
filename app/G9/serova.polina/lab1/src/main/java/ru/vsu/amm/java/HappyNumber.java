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
        Set<Integer> previousNumbers = new HashSet<>();// с - n
        Boolean isHappy = null;//1
        int tmpNumber = number;

        do {
            if (tmpNumber == 1) {
                isHappy = true;
            } else if (previousNumbers.contains(tmpNumber)) {
                isHappy = false;
            } else {
                previousNumbers.add(tmpNumber);
                tmpNumber = sumOfDigitsSquares(tmpNumber);
            }
        } while (isHappy == null);

        return isHappy;
    }

    public static void main(String[] args) {
        int someNumber = 7;
        System.out.println(isHappy(someNumber));
    }
}