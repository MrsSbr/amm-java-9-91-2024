package ru.vsu.amm.java;

import java.util.Arrays;
import java.util.Objects;

public class LargestNumber {

    public static void buildNumber(String[] numbers) {
        Arrays.sort(numbers, (String x, String y) -> (y + x).compareTo(x + y));
    }

    public static String[] intArrayToString(int[] nums) {
        String[] numbers = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            numbers[i] = Objects.toString(nums[i]);
        }
        return numbers;
    }
    public static void main(String[] args) {
        int[] nums = {1, 3, 43, 10415, 56, 86, 4, 2, 9, 34, 0, 52352, 796, 324235, 648, 907, 43};
        String[] numbers = intArrayToString(nums);
        buildNumber(numbers);
        StringBuilder answer = new StringBuilder();
        for (var x : numbers) {
            answer.append(x);
        }
        System.out.println(answer);
    }
}