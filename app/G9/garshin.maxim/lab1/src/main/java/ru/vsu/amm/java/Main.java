package ru.vsu.amm.java;
import java.util.HashSet;
import java.util.Set;

/*
Дан целочисленный массив nums, вернуть true, если какое-либо значение встречается в массиве не менее двух раз,
и вернуть false, если каждый элемент различен
*/

public class Main {
    public static void checkArrayLimitations(int[] numbers) {
        if (numbers.length < 1 || numbers.length > 100000) {
            throw new IllegalArgumentException("The length of the array must be in the range from 1 to 100000.");
        }

        for (int num : numbers) {
            if (num < -1000000000 || num > 1000000000) {
                throw new IllegalArgumentException("The array elements must be in the range from -10^9 to 10^9.");
            }
        }
    }

    public static boolean hasDuplicateElements(int[] numbers) {
        Set<Integer> uniqueElements = new HashSet<>();
        for (int number : numbers) {
            if (!uniqueElements.add(number)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 12, 21000000, 2};

        try {
            checkArrayLimitations(nums);

            System.out.print("Input: [");
            for (int i = 0; i < nums.length; ++i) {
                System.out.print(nums[i] + (i == nums.length-1 ? "]" : ", "));
            }

            System.out.println("\nOutput: " + hasDuplicateElements(nums));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}