package ru.vsu.amm.java;

import java.util.Arrays;
import java.util.Scanner;


public class CoinChange {

    public static void main(String[] args) {
        int answer = coinChange();
        if (answer == -1) {
            System.out.println("This amount of money can't be represented by any combination of coins.");
        } else {
            System.out.println("This amount of money can be represented by a combination of " + answer + " coins.");
        }
    }

    static int coinChange(){
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the number of coins (no more than 12): ");
        int amountVariations = Help.initValue(1, 12);

        int[] coins = new int[amountVariations];

        for (int i = 0; i < amountVariations; i++) {
            System.out.println("Enter the coin value: ");
            coins[i] = Help.initValue(1, Integer.MAX_VALUE);
        }

        System.out.println("Enter the amount you want to change: ");
        int amount = Help.initValue(1, 10000);

        Arrays.sort(coins); // Использует timsort сортировку (сложность в лучщем случае n, в среднем и худшем nlogn)
        return tryToChange(coins, amount, 0, amountVariations - 1);
    }

    static int tryToChange(int[] coins, int amount, Integer countOfCoins, int numberOfCoins) {

            boolean endOfChange = false;

            while (!endOfChange) {
                if (amount >= 0) {
                    amount = amount - coins[numberOfCoins];
                    countOfCoins++;
                    if (amount == 0) {
                        endOfChange = true;
                    }
                } else {
                    amount += coins[numberOfCoins];
                    countOfCoins--;
                    endOfChange = true;
                }
            }

            if (numberOfCoins == 0 && amount > 0) {
                return -1;
            }

            return (amount == 0) ?
                countOfCoins : tryToChange(coins, amount, countOfCoins, numberOfCoins - 1);
    }
}