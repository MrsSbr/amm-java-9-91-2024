package ru.vsu.amm.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Ввод данных
        int[] nums = new int[]{};
        System.out.print("Введите элементы массива nums через пробел\n --> ");
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            String[] stringNums = bf.readLine().split(" ");
            int n = stringNums.length;
            nums = new int[n];
            for (int i = 0; i < n; ++i) {
                nums[i] = Integer.parseInt(stringNums[i]);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Алгоритм
        int k = 1;
        for (int i = 0; i < nums.length - 1; ++i) {
            if (nums[i] != nums[i + 1]) {
                nums[k++] = nums[i + 1];
            }
        }
        // Вывод результата
        System.out.println("k = " + k + ", " + "nums = " + Arrays.toString(nums));
    }
}