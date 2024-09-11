package ru.vsu.amm.java;

import java.util.Scanner;

public class Main {
    public static int solve(int[] nums, int start, int end) {
        int length = end - start + 1;
        int[] solve = new int[length];
        solve[0] = nums[start];
        solve[1] = Math.max(nums[start], nums[start + 1]);
        for (int i = 2; i < length; i++) {
            solve[i] = Math.max(solve[i - 1], solve[i - 2] + nums[i + start]);
        }
        return solve[length - 1];
    }

    public static int robberSum(int[] nums) {
        int length = nums.length;
        if (length < 3) return Math.max(nums[0], nums[length - 1]);
        return Math.max(solve(nums, 0, length - 2), solve(nums, 1, length - 1));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите количество домов: ");
        int count = in.nextInt();
        int[] nums = new int[count];
        for (int i = 0; i < count; i++) {
            System.out.printf("Введите дом №%d: ", i + 1);
            nums[i] = in.nextInt();
        }
        System.out.println("Ответ: " + robberSum(nums));
    }
}