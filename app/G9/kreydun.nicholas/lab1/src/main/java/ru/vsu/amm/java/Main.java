package ru.vsu.amm.java;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static int jump(List<Integer> nums) {
        int n = nums.size();
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= i +nums.get(i) && j < n; j++) {
                dp[j] = Math.min(dp[j], dp[i] + 1);
            }
        }

        return dp[n - 1];
    }

    public static List<Integer> arrInit() {
        Scanner scanner = new Scanner(System.in);
        List<Integer> arr = new ArrayList<>();
        System.out.println("Введите числа (для завершения введите '.'):");
        boolean flag = true;
        while (flag) {//исправил
            if (scanner.hasNextInt()) {
                arr.add(scanner.nextInt());
            } else {
                String input = scanner.next();
                if (input.equalsIgnoreCase(".")) {
                    flag = false;
                } else {
                    throw new IllegalArgumentException("Неверный ввод: " + input);
                }
            }
        }

        return arr;
    }


    public static void main(String[] args) {
        try {
            List<Integer> nums = arrInit();
            int minJumps = jump(nums);
            System.out.println("Минимальное количество прыжков: " + minJumps);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}