package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;

public class Permutations {
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> numsList = new ArrayList<>();
        for (int num : nums) {
            numsList.add(num);
        }
        int length = nums.length;
        int[] indices = new int[length];
        result.add(new ArrayList<>(numsList));

        int i = 0;
        while (i < length) {
            if (indices[i] < i) {
                if (i % 2 == 0) {
                    swap(numsList, 0, i);
                } else {
                    swap(numsList, indices[i], i);
                }
                result.add(new ArrayList<>(numsList));
                indices[i] += 1;
                i = 0;
            } else {
                indices[i] = 0;
                i += 1;
            }
        }
        return result;
    }

    private static void swap(List<Integer> numsList, int a, int b) {
        int temp = numsList.get(a);
        numsList.set(a, numsList.get(b));
        numsList.set(b, temp);
    }
}
