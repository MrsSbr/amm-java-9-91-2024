package ru.vsu.amm.java;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RobberSum {
    public static int maximumAmount(int[] houses, int start, int end) {
        int length = end - start + 1;
        int[] solve = new int[length];
        solve[0] = houses[start];
        solve[1] = Math.max(houses[start], houses[start + 1]);
        for (int i = 2; i < length; i++) {
            solve[i] = Math.max(solve[i - 1], solve[i - 2] + houses[i + start]);
        }
        return solve[length - 1];
    }

    public static int robberSumSolve(int[] houses) {
        int length = houses.length;
        if (length < 3) {
            return Math.max(houses[0], houses[length - 1]);
        }
        return Math.max(maximumAmount(houses, 0, length - 2), maximumAmount(houses, 1, length - 1));
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print(ConstantsOutput.ENTERING_NUMBER_OF_HOUSES_MESSAGE);
            int count = in.nextInt();
            int[] nums = new int[count];
            for (int i = 0; i < count; i++) {
                System.out.printf(ConstantsOutput.ENTERING_HOUSES_MESSAGE, i + 1);
                nums[i] = in.nextInt();
            }
            System.out.println(ConstantsOutput.ANSWER_MESSAGE + robberSumSolve(nums));
        } catch (IllegalStateException | NoSuchElementException e) {
            System.out.println(ConstantsOutput.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}