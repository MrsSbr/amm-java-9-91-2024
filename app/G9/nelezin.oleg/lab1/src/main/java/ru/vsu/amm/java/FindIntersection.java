package ru.vsu.amm.java;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FindIntersection {

    public static void main(String[] args) {
        int[] nums1 = initArray(Constants.FIRST_MESSAGE);
        int[] nums2 = initArray(Constants.SECOND_MESSAGE);

        int[] result = intersectionOfArrays(nums1, nums2);

        if (result == null || result.length == 0) {
            System.out.println("Нет пересечения");
        } else {
            System.out.println("Пересечение: ");
            for (int i = 0; i < result.length; ++i) {
                if (i != result.length - 1) {
                    System.out.print(result[i] + " ");
                } else {
                    System.out.print(result[i]);
                }
            }
        }
    }

    static int[] initArray(String message) {
        int[] intArr = null;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println(message);
            String[] stringArr = scanner.nextLine().split("\\s+");
            intArr = new int[stringArr.length];
            if (intArr.length > 1000) {
                throw new IllegalArgumentException(Constants.CONDITION_ERROR_MESSAGE);
            }

            for (int i = 0; i < intArr.length; ++i) {
                intArr[i] = Integer.parseInt(stringArr[i]);
                if (intArr[i] > 1000 || intArr[i] < 0) {
                    throw new IllegalArgumentException(Constants.CONDITION_ERROR_MESSAGE);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return intArr;
    }

    static int[] intersectionOfArrays(int[] nums1, int[] nums2) {
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

        return intersection
                .stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}