package ru.vsu.amm.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RomanToInteger {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String romanInput = scanner.nextLine();
        System.out.println(romanToInteger(romanInput));
    }

    public static int romanToInteger(String s) {
        Map<Character, Integer> romanValues = new HashMap<>();
        romanValues.put('I', 1);
        romanValues.put('V', 5);
        romanValues.put('X', 10);
        romanValues.put('L', 50);
        romanValues.put('C', 100);
        romanValues.put('D', 500);
        romanValues.put('M', 1000);

        int total = 0;
        int prevValue = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            char currentChar = s.charAt(i);
            try {
                int currentValue = romanValues.get(currentChar);

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
