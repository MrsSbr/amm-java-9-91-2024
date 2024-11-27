package ru.vsu.amm.java;

import java.util.Scanner;

public class Help {
    private static Scanner in = new Scanner(System.in);

    static int initValue(int left, int right) {
        System.out.format("*input the number in the range from %s to %s \n", left, right);
        int variable = in.nextInt();
        while (variable < left || variable > right) {
            System.out.format("*the number must be in the range from %s to %s \n", left, right);
            variable = in.nextInt();
        }
        return variable;
    }
}
