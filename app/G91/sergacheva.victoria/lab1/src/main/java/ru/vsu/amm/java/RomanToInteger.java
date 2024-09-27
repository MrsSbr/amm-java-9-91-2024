package ru.vsu.amm.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RomanToInteger {

    public static final Map<Character, Integer> ROMAN_VALUES = new HashMap<>();
    static {
        ROMAN_VALUES.put('I', 1);
        ROMAN_VALUES.put('V', 5);
        ROMAN_VALUES.put('X', 10);
        ROMAN_VALUES.put('L', 50);
        ROMAN_VALUES.put('C', 100);
        ROMAN_VALUES.put('D', 500);
        ROMAN_VALUES.put('M', 1000);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String romanInput = scanner.nextLine();
        System.out.println(romanToInteger(romanInput));
    }

    public static int romanToInteger(String s) {

        int total = 0;
        int prevValue = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            char currentChar = s.charAt(i);
            try {
                int currentValue = ROMAN_VALUES.get(currentChar);

                if (currentValue < prevValue) {
                    total -= currentValue;
                } else {
                    total += currentValue;
                }

                prevValue = currentValue;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        return total;
    }
}
