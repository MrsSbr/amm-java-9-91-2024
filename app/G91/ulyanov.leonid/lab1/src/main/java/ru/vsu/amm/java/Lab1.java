package ru.vsu.amm.java;

import java.util.Arrays;
import java.util.Scanner;

public class Lab1 {
    public static int maxProfit(int[] prices) throws NumberFormatException,
            IndexOutOfBoundsException {
        if (prices.length > 30000) {
            throw new IndexOutOfBoundsException("Exception has occurred: " +
                    "the array length is more than 30,000!");
        }
        if (Arrays.stream(prices).anyMatch(x -> x < 0 || x > 10000)) {
            throw new NumberFormatException("Exception has occurred: " +
                    "the array contains a value outside of the segment [0; 10,000]!");
        }
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }

    public static void main(String[] args) {
        System.out.println("""
                Enter the prices in one line with space as a separator.
                Each number should represent an integer datatype and must be between 0 and 10,000.
                The amount of numbers should be between 1 and 30,000.""");
        System.out.print("> ");
        try (Scanner sc = new Scanner(System.in)) {
            int[] prices = Arrays.stream(sc.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            System.out.print("The maximum profit for the given prices is "
                    + maxProfit(prices) + ".");
        } catch (IndexOutOfBoundsException
                 | NumberFormatException e) {
            System.out.print(e.getMessage());
        }
    }
}