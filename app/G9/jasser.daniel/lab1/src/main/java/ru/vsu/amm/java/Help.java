package ru.vsu.amm.java;

import java.util.Scanner;

public class Help {
    private static Scanner in = new Scanner(System.in);

    static int checkRange(int left, int right, int variable) {
        while (variable < left || variable > right) {
            System.out.format("The number must be in the range from %s to %s: \n", left, right);
            variable = in.nextInt();
        }
        return variable;
    }
}
