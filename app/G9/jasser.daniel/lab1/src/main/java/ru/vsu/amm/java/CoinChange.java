package ru.vsu.amm.java;

import java.util.Arrays;
import java.util.Scanner;


public class CoinChange {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the number of coins (no more than 12): ");
        int amountVariations = in.nextInt();

        amountVariations = Help.checkRange(1, 12, amountVariations);

        int[] coins = new int[amountVariations];

        for (int i = 0; i < amountVariations; i++) {
            System.out.println("Enter the coin value: ");
            coins[i] = in.nextInt();
            coins[i] = Help.checkRange(1, Integer.MAX_VALUE, coins[i]);
        }

        Arrays.sort(coins);

        System.out.println("Enter the amount you want to change: ");
        int amount = in.nextInt();
        amount = Help.checkRange(1, 10000, amount);

        int answer = tryToChange(coins, amount, 0, amountVariations - 1);
        System.out.println(answer);
    }

    static int tryToChange(int[] coins, int amount, Integer countOfCoins, int numberOfCoins) {

            boolean endOfChange = false;

            while (!endOfChange) {
                if (amount >= 0) {
                    amount = amount - coins[numberOfCoins];
                    countOfCoins++;
                    if (amount == 0)
                        endOfChange = true;
                } else {
                    amount += coins[numberOfCoins];
                    countOfCoins--;
                    endOfChange = true;
                }
            }

            if (numberOfCoins == 0 && amount > 0)
                return -1;

            return (amount == 0) ?
                countOfCoins : tryToChange(coins, amount, countOfCoins, numberOfCoins - 1);
    }
}