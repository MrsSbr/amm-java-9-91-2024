package ru.vsu.amm.java;

import java.util.Arrays;
import java.util.Scanner;

public class Lab1 {
    public static int maxProfit(int[] prices) throws ArrayLengthOutOfBoundsException,
            ValueOutOfBoundsException {
        if (prices.length > 30000) {
            throw new ArrayLengthOutOfBoundsException("Exception has occurred: " +
                    "the array length is more than 30,000!");
        }
        if (Arrays.stream(prices).anyMatch(x -> x < 0 || x > 10000)) {
            throw new ValueOutOfBoundsException("Exception has occurred: " +
                    "the array contains a value outside of the segment [0; 10,000]!");
        }
        int res = 0;
        int startPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (startPrice < prices[i]) {
                res += prices[i] - startPrice;
            }
            startPrice = prices[i];
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("""
                Enter the prices in one line with space as a separator.
                Each number should represent an integer datatype and must be between 0 and 10,000.
                The amount of numbers should be between 1 and 30,000.""");
        System.out.print("> ");
        try (Scanner sc = new Scanner(System.in)) {
            int[] prices = Arrays.stream(sc.nextLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            try {
                System.out.print("The maximum profit for the given prices is "
                        + maxProfit(prices) + ".");
            } catch (ArrayLengthOutOfBoundsException
                     | ValueOutOfBoundsException e) {
                System.out.print(e.getMessage());
            }
        } catch (java.lang.NumberFormatException e) {
            System.out.println("Exception has occurred: Not a number!");
        }
    }
}