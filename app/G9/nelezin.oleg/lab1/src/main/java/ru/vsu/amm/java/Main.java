package ru.vsu.amm.java;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        int[] nums1 = initArray(Constants.FIRST_MESSAGE);
        int[] nums2 = initArray(Constants.SECOND_MESSAGE);

        int[] result = intersectionOfArrays(nums1, nums2);
        System.out.println("Пересечение:");
        for (int num: result) {
            System.out.print(num + " ");
        }
    }

    public static int[] initArray(String message) {
        int[] intArr = new int[0];
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println(message);
            String[] stringArr = scanner.nextLine().split(" ");
            intArr = new int[stringArr.length];
            if (intArr.length > 1000) {
                throw new ConditionException(Constants.CONDITION_ERROR_MESSAGE);
            }

            for (int i = 0; i < intArr.length; ++i) {
                intArr[i] = Integer.parseInt(stringArr[i]);
                if (intArr[i] > 1000 || intArr[i] < 0) {
                    throw new ConditionException(Constants.CONDITION_ERROR_MESSAGE);
                }
            }
        } catch (ConditionException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(Constants.ERROR_MESSAGE);
        }

        return intArr;
    }

    public static int[] intersectionOfArrays(int[] nums1, int[] nums2) {
        Set<Integer> setForNums1 = new HashSet<>();
        for (int num: nums1) {
            setForNums1.add(num);
        }

        Set<Integer> intersection = new HashSet<>();
        for (int num: nums2) {
            if (setForNums1.contains(num)) {
                intersection.add(num);
            }
        }

        int[] result = new int[intersection.size()];
        int i = 0;
        for (int num: intersection) {
            result[i++] = num;
        }
        return result;
    }
}