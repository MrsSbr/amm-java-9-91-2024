package ru.vsu.amm.java;

// сложность O(2^n)

public class Main {
    static boolean winnerTask(int[] nums) {
        Integer[][] matrix = new Integer[nums.length][nums.length];
        return winnerRec(nums, 0, nums.length - 1, matrix) >= 0;
    }

    public static int winnerRec(int[] nums, int i, int j, Integer[][] matrix) {
        int result;

        if (i == j) {
            result = nums[i];
        } else if (matrix[i][j] != null) {
            result = matrix[i][j];
        } else {
            int a = nums[i] - winnerRec(nums, i + 1, j, matrix);
            int b = nums[j] - winnerRec(nums, i, j - 1, matrix);

            matrix[i][j] = Math.max(a, b);
            result = matrix[i][j];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 5, 2};
        System.out.println(winnerTask(nums1)); // false

        int[] nums2 = {1, 5, 233, 7};
        System.out.println(winnerTask(nums2)); // true
    }
}