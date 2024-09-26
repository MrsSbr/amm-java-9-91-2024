package ru.vsu.amm.java;

import java.util.Scanner;

public class FullSquare {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        boolean res = squareOfNumber(num);
        System.out.println(res);
    }

    static boolean squareOfNumber (int number) {
        for (int i = 1; i <= number / 2; i++) {
            if (i * i == number) {
                return true;
            }
        }
        return false;
    }
}
