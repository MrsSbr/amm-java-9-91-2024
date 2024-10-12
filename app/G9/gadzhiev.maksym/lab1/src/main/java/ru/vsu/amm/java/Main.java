package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[] candidates = {2, 3, 4, 5};
        int target = 10;

        System.out.println(taskCombinationSum(candidates, target));
    }

    public static List<List<Integer>> taskCombinationSum(int[] candidates, int target) {
        List<List<Integer>> answer = new ArrayList<>();
        findingArray(candidates, target, 0, answer, new ArrayList<>());
        return answer;
    }

    public static void findingArray(int[] candidates, int target, int start,
                             List<List<Integer>> list, List<Integer> tmp) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            list.add(new ArrayList<>(tmp));
        } else {
            for (int i = start; i < candidates.length; i++) {
                tmp.add(candidates[i]);
                findingArray(candidates, target - candidates[i], i, list, tmp);
                tmp.remove(tmp.size() - 1);
            }
        }
    }
}