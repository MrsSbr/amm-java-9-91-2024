package ru.vsu.amm.java;

import java.util.Scanner;

public class CheckArrayIncreasing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите элементы массива через пробел: ");
        boolean good=true;

        try {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            int[] nums2 = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
                nums2[i] = Integer.parseInt(parts[i]);
                if (nums2[i] < 1 || nums2[i] > 1000) {
                    System.out.print("Неверное значение числа.");
                    good=false;

                    //throw new IllegalArgumentException("Неверное значение числа");
                }
            }
            if (nums2.length < 2 || nums2.length > 1000) {
                System.out.print("Неверный размер массива.");
                good=false;

                //throw new IllegalArgumentException("Неверный размер массива");
            }
            scanner.close();
            if(good) {
                boolean result1 = canBeIncreasing(nums2);

                System.out.println("nums2: " + result1);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static boolean canBeIncreasing(int[] nums) {
        int removed = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] >= nums[i]) {
                removed++;
                if (removed > 1) {
                    return false;
                }
            }
        }
        return true;
    }
}